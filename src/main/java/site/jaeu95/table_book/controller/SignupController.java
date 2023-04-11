package site.jaeu95.table_book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.jaeu95.table_book.domain.form.SignUpForm;
import site.jaeu95.table_book.service.SignupService;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {
    private final SignupService signupService;

    @PostMapping("/customer")
    public ResponseEntity<String> customerSignup(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(signupService.customerSignUp(form));
    }

    @PostMapping("/manager")
    public ResponseEntity<String> managerSignup(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(signupService.mangerSignup(form));
    }
}
