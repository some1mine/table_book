package site.jaeu95.table_book.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.jaeu95.table_book.domain.form.SignUpForm;
import site.jaeu95.table_book.domain.model.Customer;
import site.jaeu95.table_book.domain.model.Manager;

/**
 * 회원 가입을 위한 서비스입니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SignupService {
    private final CustomerService customerService;
    private final ManagerService managerService;

    public String customerSignUp(SignUpForm form) {
        Customer customer = customerService.signUp(form);

        return "일반 이용자 회원가입에 성공하였습니다.";
    }

    public String mangerSignup(SignUpForm form) {
        Manager manager = managerService.signup(form);
        managerService.makePartnerShip(manager.getId());

        return "점장 회원가입 성공하였습니다.";
    }
}
