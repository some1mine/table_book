package site.jaeu95.table_book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.jaeu95.table_book.config.JwtAuthenticationProvider;
import site.jaeu95.table_book.domain.common.UserVo;
import site.jaeu95.table_book.domain.dto.CustomerDto;
import site.jaeu95.table_book.domain.dto.ReservationDto;
import site.jaeu95.table_book.domain.dto.ReviewDto;
import site.jaeu95.table_book.domain.dto.StoreDto;
import site.jaeu95.table_book.domain.form.AddReviewForm;
import site.jaeu95.table_book.domain.form.BookTableForm;
import site.jaeu95.table_book.domain.form.VisitTableForm;
import site.jaeu95.table_book.domain.model.Customer;
import site.jaeu95.table_book.exception.CustomException;
import site.jaeu95.table_book.exception.ErrorCode;
import site.jaeu95.table_book.service.CustomerService;
import site.jaeu95.table_book.service.ReviewService;
import site.jaeu95.table_book.service.StoreService;
import site.jaeu95.table_book.service.TableService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 고객으로부터 들어오는 요청을 받기 위한 Controller 입니다.
 * 회원가입, 로그인은 별도의 컨트롤러를 만들었습니다.
 */
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final JwtAuthenticationProvider provider;
    private final CustomerService customerService;
    private final TableService tableService;
    private final StoreService storeService;
    private final ReviewService reviewService;

    /**
     * 고객 자신의 정보를 가져오는 메서드입니다.
     */
    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo userVo = provider.getUserVo(token);
        Customer customer = customerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ResponseEntity.ok(CustomerDto.from(customer));
    }

    /**
     * 모든 점포의 정보를 불러오는 메서드입니다.
     */
    @GetMapping("/getAllStores")
    public ResponseEntity<List<StoreDto>> getAllStores(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo userVo = provider.getUserVo(token);
        customerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ResponseEntity.ok(storeService.getAllStores().stream().map(StoreDto::from).collect(Collectors.toList()));
    }

    /**
     * 테이블을 예약하는 메서드입니다.
     */
    @PostMapping("/bookTable")
    public ResponseEntity<ReservationDto> bookTable(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody BookTableForm form) {
        UserVo userVo = provider.getUserVo(token);
        Customer customer = customerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(ReservationDto.from(tableService.bookTable(customer, form)));
    }

    /**
     * 방문처리를 하는 메서드입니다.
     */
    @PostMapping("/visitTable")
    public ResponseEntity<ReservationDto> visitTable(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody VisitTableForm form) {
        UserVo userVo = provider.getUserVo(token);
        customerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(ReservationDto.from(customerService.visitTable(form)));
    }

    @PostMapping("/addReview")
    public ResponseEntity<ReviewDto> makeReview(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody AddReviewForm form) {
        UserVo userVo = provider.getUserVo(token);
        customerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ResponseEntity.ok(ReviewDto.from(reviewService.addReview(form)));
    }

    @GetMapping("/getMyReviews")
    public ResponseEntity<List<ReviewDto>> getMyReviews(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo userVo = provider.getUserVo(token);
        Customer customer = customerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ResponseEntity.ok(reviewService.getReviewByCustomerId(customer.getId()).stream().map(ReviewDto::from).collect(Collectors.toList()));
    }

    @GetMapping("/getMyReservation")
    public ResponseEntity<List<ReservationDto>> getMyReservation(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo userVo = provider.getUserVo(token);
        Customer customer = customerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ResponseEntity.ok(customerService.getMyReservation(customer).stream().map(ReservationDto::from).collect(Collectors.toList()));
    }

    @GetMapping("/getStoreReviews/{id}")
    public ResponseEntity<List<ReviewDto>> getStoreReviews(@RequestHeader(name = "X-AUTH-TOKEN") String token, @PathVariable Long id) {
        UserVo userVo = provider.getUserVo(token);
        customerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ResponseEntity.ok(reviewService.getReviewByStoreId(id).stream().map(ReviewDto::from).collect(Collectors.toList()));
    }
}
