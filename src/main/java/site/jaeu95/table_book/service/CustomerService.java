package site.jaeu95.table_book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.jaeu95.table_book.domain.form.SignUpForm;
import site.jaeu95.table_book.domain.form.VisitTableForm;
import site.jaeu95.table_book.domain.model.Customer;
import site.jaeu95.table_book.domain.model.Reservation;
import site.jaeu95.table_book.domain.model.Store;
import site.jaeu95.table_book.domain.model.Table;
import site.jaeu95.table_book.domain.repository.CustomerRepository;
import site.jaeu95.table_book.domain.repository.ReservationRepository;
import site.jaeu95.table_book.domain.repository.StoreRepository;
import site.jaeu95.table_book.domain.repository.TableRepository;
import site.jaeu95.table_book.exception.CustomException;
import site.jaeu95.table_book.exception.ErrorCode;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 고객의 Service 입니다.
 * 회원가입과 로그인은 별도의 서비스를 만드었습니다.
 */

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final TableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 회원가입 메소드입니다. 핸드폰 경우를 기준으로 존재한는 경우 CustomError 를 발생시킵니다.
     */
    public Customer signUp(SignUpForm form) {
        if (!customerRepository.existsByPhone(form.getPhone())) {
            return customerRepository.save(Customer.from(form));
        } else {
            throw new CustomException(ErrorCode.USING_PHONE);
        }
    }

    /**
     * id 와 핸드폰으로 고객의 정보를 반환하는 메서드입니다.
     */
    public Optional<Customer> findByIdAndPhone(Long id, String phone) {
        return customerRepository.findById(id).stream().filter(
                customer -> customer.getPhone().equals(phone)).findFirst();
    }

    /**
     * 비밀번호와 핸드폰으로 고객의 정보를 반환하는 메서드입니다.
     */
    public Optional<Customer> findValidCustomer(String phone, String password) {
        return customerRepository.findByPhone(phone).stream().filter(
                customer -> customer.getPassword().equals(password)).findFirst();
    }

    /**
     * 점포 방문 처리를 위한 메소드입니다. save 해주지 않아도 자동 업데이트 된다는 것을 읽은 적이 있지만,, 순환참조 해결 후 불안해서 저장하는 식으로 작성했습니다.
     */
    public Reservation visitTable(VisitTableForm form) {
        Customer customer = customerRepository.findByPhone(form.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Store store = storeRepository.findById(form.getStoreId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));
        Table table = tableRepository.findById(form.getTableId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TABLE));
        Reservation reservation = reservationRepository.findById(form.getReservationId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_USER_RESERVATION));


        reservation.setCheckedOuted(true);
        customer.getReservations().stream().filter(r -> r.equals(reservation)).collect(Collectors.toList()).forEach(r -> r.setCheckedOuted(true));
        store.getTables().stream().filter(t -> t.equals(table)).collect(Collectors.toList()).forEach(t -> t.setBooked(false));
        table.setBooked(false);

        reservationRepository.save(reservation);
        customerRepository.save(customer);
        storeRepository.save(store);
        tableRepository.save(table);

        return reservation;
    }
}
