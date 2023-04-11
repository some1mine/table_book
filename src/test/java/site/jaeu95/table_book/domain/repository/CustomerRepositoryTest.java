package site.jaeu95.table_book.domain.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Test
    @DisplayName("번호로 사용자 찾기")
    void findExistingPhoneTest() {
        String phone = "000-0000-0000";
        assertFalse(customerRepository.existsByPhone(phone));
    }
}