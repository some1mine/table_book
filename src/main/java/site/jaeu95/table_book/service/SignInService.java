package site.jaeu95.table_book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.jaeu95.table_book.config.JwtAuthenticationProvider;
import site.jaeu95.table_book.domain.common.UserType;
import site.jaeu95.table_book.domain.form.SignInForm;
import site.jaeu95.table_book.domain.model.Customer;
import site.jaeu95.table_book.domain.model.Manager;
import site.jaeu95.table_book.exception.CustomException;
import site.jaeu95.table_book.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class SignInService {
    private final CustomerService customerService;
    private final ManagerService managerService;
    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInForm form) {
        Customer customer = customerService.findValidCustomer(form.getPhone(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return provider.createToken(customer.getPhone(), customer.getId(), UserType.CUSTOMER);
    }

    public String managerLoginToken(SignInForm form) {
        Manager manager = managerService.findValidManager(form.getPhone(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return provider.createToken(manager.getPhone(), manager.getId(), UserType.MANAGER);
    }
}
