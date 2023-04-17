package site.jaeu95.table_book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.jaeu95.table_book.config.JwtAuthenticationProvider;
import site.jaeu95.table_book.domain.common.UserVo;
import site.jaeu95.table_book.domain.dto.ManagerDto;
import site.jaeu95.table_book.domain.dto.StoreDto;
import site.jaeu95.table_book.domain.form.AddStoreForm;
import site.jaeu95.table_book.domain.form.AddTableForm;
import site.jaeu95.table_book.domain.model.Manager;
import site.jaeu95.table_book.domain.model.Store;
import site.jaeu95.table_book.exception.CustomException;
import site.jaeu95.table_book.exception.ErrorCode;
import site.jaeu95.table_book.service.ManagerService;
import site.jaeu95.table_book.service.StoreService;
import site.jaeu95.table_book.service.TableService;

@RestController
@RequestMapping("/manager/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final TableService tableService;
    private final ManagerService managerService;
    private final JwtAuthenticationProvider provider;

    @PostMapping
    public ResponseEntity<ManagerDto> addStore(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody AddStoreForm form) {
        UserVo userVo = provider.getUserVo(token);
        Manager manager = managerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(ManagerDto.from(storeService.addStore(manager.getId(), form)));
    }

    @PostMapping("/table")
    public ResponseEntity<StoreDto> addTableInStore(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody AddTableForm form) {
        UserVo userVo = provider.getUserVo(token);
        Manager manager = managerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(StoreDto.from(tableService.addTableInStore(form)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> getStoreInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token, @PathVariable Long id) {
        UserVo userVo = provider.getUserVo(token);
        Manager manager = managerService.findByIdAndPhone(userVo.getId(), userVo.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Store store = storeService.getStore(id);
        if (manager.getId() != store.getManager().getId()) throw new CustomException(ErrorCode.NOT_FOUND_USER);

        return ResponseEntity.ok(StoreDto.from(store));
    }
}
