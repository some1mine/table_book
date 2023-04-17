package site.jaeu95.table_book.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.jaeu95.table_book.domain.form.SignUpForm;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SignupServiceTest {
    @Autowired
    private SignupService signupService;

    @Test
    void customerSignUpTest() {
        SignUpForm form = SignUpForm.builder()
                .name("jaeu")
                .email("parkjw573@naver.com")
                .phone("000-0000-0000")
                .password("some1mine")
                .build();
        assertEquals(signupService.customerSignUp(form), "일반 이용자 회원가입에 성공하였습니다.");
    }

    @Test
    void managerSignUpTest() {
        SignUpForm form = SignUpForm.builder()
                .name("jaeu")
                .email("parkjw573@naver.com")
                .phone("000-0000-0000")
                .password("some1mine")
                .build();
        assertEquals(signupService.mangerSignup(form), "점장 회원가입 성공하였습니다.");
    }
}