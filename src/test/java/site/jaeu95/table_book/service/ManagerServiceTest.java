package site.jaeu95.table_book.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.jaeu95.table_book.domain.form.SignUpForm;
import site.jaeu95.table_book.domain.model.Manager;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ManagerServiceTest {
    @Autowired
    private ManagerService managerService;

    @Test
    void signUpTest() {
        SignUpForm form = SignUpForm.builder()
                .name("jaeu")
                .email("parkjw573@naver.com")
                .phone("000-0000-0000")
                .password("some1mine").build();
        Manager enrolledManager = managerService.signup(form);
        assertEquals(enrolledManager.getId(), 1L);
        assertEquals(enrolledManager.getName(), "jaeu");
        assertEquals(enrolledManager.getEmail(), "parkjw573@naver.com");
        assertEquals(enrolledManager.getPhone(), "000-0000-0000");
        assertEquals(enrolledManager.getPassword(), "some1mine");
    }

}