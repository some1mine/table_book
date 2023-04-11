package site.jaeu95.table_book.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.jaeu95.table_book.domain.form.SignUpForm;
import site.jaeu95.table_book.domain.model.Customer;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Test
    void signUpTest() {
        SignUpForm form = SignUpForm.builder()
                .name("jaeu")
                .email("parkjw573@naver.com")
                .phone("000-0000-0000")
                .password("some1mine").build();
        Customer enrolledCustomer = customerService.signUp(form);
        assertEquals(enrolledCustomer.getId(), 1L);
        assertEquals(enrolledCustomer.getName(), "jaeu");
        assertEquals(enrolledCustomer.getEmail(), "parkjw573@naver.com");
        assertEquals(enrolledCustomer.getPhone(), "000-0000-0000");
        assertEquals(enrolledCustomer.getPassword(), "some1mine");
    }
}