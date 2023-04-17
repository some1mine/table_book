package site.jaeu95.table_book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jaeu95.table_book.domain.form.AddTableForm;
import site.jaeu95.table_book.domain.form.BookTableForm;
import site.jaeu95.table_book.domain.model.*;
import site.jaeu95.table_book.domain.repository.CustomerRepository;
import site.jaeu95.table_book.domain.repository.ReservationRepository;
import site.jaeu95.table_book.domain.repository.StoreRepository;
import site.jaeu95.table_book.domain.repository.TableRepository;
import site.jaeu95.table_book.exception.CustomException;
import site.jaeu95.table_book.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 테이블 정보 추가와 테이블의 예약, 그리고 예약취소 처리를 위한 서비스입니다.
 */
@Service
@RequiredArgsConstructor
public class TableService {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final TableRepository tableRepository;

    /**
     *
     * @param form
     * @return 점포에 테이블을 추가하고 해당 점포 정보를 반환하는 메서드입니다.
     */
    public Store addTableInStore(AddTableForm form) {
        Store store = storeRepository.findById(form.getStoreId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));
        Table table = Table.fromAddForm(form);
        store.getTables().add(table);
        table.setStore(store);

        storeRepository.save(store);
        tableRepository.save(table);

        return store;
    }

    /**
     *
     * @param customer
     * @param form
     * @return 고객의 요청으로 테이블을 예약하기 위한 메서드입니다. 예약정보 Entity 를 반환합니다.
     */
    @Transactional
    public Reservation bookTable(Customer customer, BookTableForm form) {
        Store store = storeRepository.findById(form.getStoreId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));
        Table table = store.getTables().stream()
                .filter(t -> t.getId() == form.getTableId() && t.getStore().getId() == form.getStoreId()).findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TABLE));

        if (table.getBooked()) throw new CustomException(ErrorCode.ALREADY_BOOKED_TABLE);

        table.setBooked(true);

        storeRepository.save(store);
        tableRepository.save(table);

        Reservation reservation = Reservation.builder()
                .customer(customer).store(store)
                .table(table)
                .reservedTime(form.getReservedAt())
                .build();

        reservationRepository.save(reservation);
        customer.getReservations().add(reservation);

        customerRepository.save(customer);

        return reservation;
    }

    /**
     * 매분 0초마다 작동하는 기간이 지난 예약정보를 취소처리하기 위한 메서드입니다.
     */
    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void refreshReservation() {
        List<Reservation> expiredReservations = reservationRepository.findByReservedTimeIsBeforeAndCheckOutedIsFalse(LocalDateTime.now());
        expiredReservations.forEach(x -> x.setExpired(true));
        reservationRepository.saveAll(expiredReservations);

        List<Customer> customers = customerRepository.findAll().stream()
                .filter(customer -> customer.getReservations().stream().anyMatch(expiredReservations::contains)).collect(Collectors.toList());

        customers.forEach(customer -> customer.getReservations().stream().filter(expiredReservations::contains).collect(Collectors.toList())
                        .forEach(reservation -> reservation.setExpired(true)));
        customerRepository.saveAll(customers);

        List<Table> tables = expiredReservations.stream().map(Reservation::getTable).collect(Collectors.toList());
        tables.forEach(table -> table.setBooked(false));
        tableRepository.saveAll(tables);

        List<Store> stores = storeRepository.findAll().stream()
                .filter(store -> store.getTables().stream().anyMatch(tables::contains)).collect(Collectors.toList());

        stores.forEach(store -> store.getTables().stream().filter(tables::contains).forEach(table -> table.setBooked(false)));

        storeRepository.saveAll(stores);

        System.out.println("스케줄러 작동");
    }
}
