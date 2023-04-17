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

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final TableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    public Customer signUp(SignUpForm form) {
        if (!customerRepository.existsByPhone(form.getPhone())) {
            return customerRepository.save(Customer.from(form));
        } else {
            throw new CustomException(ErrorCode.USING_PHONE);
        }
    }

    public Optional<Customer> findByIdAndPhone(Long id, String phone) {
        return customerRepository.findById(id).stream().filter(
                customer -> customer.getPhone().equals(phone)).findFirst();
    }

    public Optional<Customer> findValidCustomer(String phone, String password) {
        return customerRepository.findByPhone(phone).stream().filter(
                customer -> customer.getPassword().equals(password)).findFirst();
    }

    public Reservation visitTable(VisitTableForm form) {
        Customer customer = customerRepository.findByPhone(form.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Store store = storeRepository.findById(form.getStoreId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));
        Table table = tableRepository.findById(form.getTableId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TABLE));
        Reservation reservation = reservationRepository.findById(form.getReservationId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_USER_RESERVATION));


        reservation.setCheckOuted(true);
        customer.getReservations().stream().filter(r -> r.equals(reservation)).collect(Collectors.toList()).forEach(r -> r.setCheckOuted(true));
        store.getTables().stream().filter(t -> t.equals(table)).collect(Collectors.toList()).forEach(t -> t.setBooked(false));
        table.setBooked(false);

        reservationRepository.save(reservation);
        customerRepository.save(customer);
        storeRepository.save(store);
        tableRepository.save(table);

        return reservation;
    }
}
