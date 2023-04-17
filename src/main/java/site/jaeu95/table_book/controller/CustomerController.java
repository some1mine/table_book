package site.jaeu95.table_book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.jaeu95.table_book.config.JwtAuthenticationProvider;
import site.jaeu95.table_book.domain.common.UserVo;
import site.jaeu95.table_book.domain.dto.CustomerDto;
import site.jaeu95.table_book.domain.dto.ReservationDto;
import site.jaeu95.table_book.domain.dto.StoreDto;
import site.jaeu95.table_book.domain.form.BookTableForm;
import site.jaeu95.table_book.domain.form.VisitTableForm;
import site.jaeu95.table_book.domain.model.Customer;
import site.jaeu95.table_book.exception.CustomException;
import site.jaeu95.table_book.exception.ErrorCode;
import site.jaeu95.table_book.service.CustomerService;
import site.jaeu95.table_book.service.StoreService;
import site.jaeu95.table_book.service.TableService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final JwtAuthenticationProvider provider;
    private final CustomerService customerService;
    private final TableService tableService;
    private final StoreService storeService;

    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo userVo = provider.getUserVo(token);
        Customer customer = customerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ResponseEntity.ok(CustomerDto.from(customer));
    }

    @GetMapping("/getAllStores")
    public ResponseEntity<List<StoreDto>> getAllStores(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo userVo = provider.getUserVo(token);
        customerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ResponseEntity.ok(storeService.getAllStores().stream().map(StoreDto::from).collect(Collectors.toList()));
    }

    @PostMapping("/bookTable")
    public ResponseEntity<ReservationDto> bookTable(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody BookTableForm form) {
        UserVo userVo = provider.getUserVo(token);
        Customer customer = customerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(ReservationDto.from(tableService.bookTable(customer, form)));
    }

    @PostMapping("/visitTable")
    public ResponseEntity<ReservationDto> visitTable(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody VisitTableForm form) {
        UserVo userVo = provider.getUserVo(token);
        customerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(ReservationDto.from(customerService.visitTable(form)));
    }
}
