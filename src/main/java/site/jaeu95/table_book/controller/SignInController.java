package site.jaeu95.table_book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.jaeu95.table_book.domain.form.SignInForm;
import site.jaeu95.table_book.service.SignInService;

/**
 * 로그인 컨트롤러 입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/signIn")
public class SignInController {
    private final SignInService signInService;

    @PostMapping("/customer")
    public ResponseEntity<String> signInCustomer(@RequestBody SignInForm form) {
        return ResponseEntity.ok(signInService.customerLoginToken(form));
    }

    @PostMapping("/manager")
    public ResponseEntity<String> signInManager(@RequestBody SignInForm form) {
        return ResponseEntity.ok(signInService.managerLoginToken(form));
    }
}
