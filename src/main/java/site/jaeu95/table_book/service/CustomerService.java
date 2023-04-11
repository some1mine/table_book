package site.jaeu95.table_book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.jaeu95.table_book.domain.form.SignUpForm;
import site.jaeu95.table_book.domain.model.Customer;
import site.jaeu95.table_book.domain.repository.CustomerRepository;
import site.jaeu95.table_book.exception.CustomException;
import site.jaeu95.table_book.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer signUp(SignUpForm form) {
        if (!customerRepository.existsByPhone(form.getPhone())) {
            return customerRepository.save(Customer.from(form));
        } else {
            throw new CustomException(ErrorCode.USING_PHONE);
        }
    }
}
