package site.jaeu95.table_book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.jaeu95.table_book.config.JwtAuthenticationProvider;
import site.jaeu95.table_book.domain.common.UserVo;
import site.jaeu95.table_book.domain.dto.ManagerDto;
import site.jaeu95.table_book.domain.dto.StoreDto;
import site.jaeu95.table_book.domain.model.Manager;
import site.jaeu95.table_book.exception.CustomException;
import site.jaeu95.table_book.exception.ErrorCode;
import site.jaeu95.table_book.service.ManagerService;
import site.jaeu95.table_book.service.StoreService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 점장과 관련된 요청을 받는 Controller 인데, 단일 매장에 대한 메서드들은 StoreController 에 작성했습니다.
 * 회원가입, 로그인은 별도의 컨트롤러를 만들었습니다.
 */
@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final JwtAuthenticationProvider provider;
    private final ManagerService managerService;
    private final StoreService storeService;

    @GetMapping("/getInfo")
    public ResponseEntity<ManagerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo userVo = provider.getUserVo(token);
        Manager manager = managerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ResponseEntity.ok(ManagerDto.from(manager));
    }

    @GetMapping("/getMyStores")
    public ResponseEntity<List<StoreDto>> getALlStores(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo userVo = provider.getUserVo(token);
        Manager manager = managerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(storeService.getMyStores(manager.getId()).stream().map(StoreDto::from).collect(Collectors.toList()));
    }
}
