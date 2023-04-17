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

@Service
@RequiredArgsConstructor
public class TableService {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final TableRepository tableRepository;

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
