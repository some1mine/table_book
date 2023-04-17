package site.jaeu95.table_book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jaeu95.table_book.domain.form.AddStoreForm;
import site.jaeu95.table_book.domain.model.Manager;
import site.jaeu95.table_book.domain.model.Store;
import site.jaeu95.table_book.domain.repository.ManagerRepository;
import site.jaeu95.table_book.domain.repository.StoreRepository;
import site.jaeu95.table_book.exception.CustomException;
import site.jaeu95.table_book.exception.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 점포에 관한 로직을 처리하는 서비스입니다.
 */
@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ManagerRepository managerRepository;

    /**
     * id 를 받아 단일 점포의 정보를 반환하는 메서드입니다.
     */
    @Transactional
    public Store getStore(Long id) {
        return storeRepository.getById(id);
    }

    /**
     *
     * @param managerId
     * @param form
     * @return 점포를 추가하고 소유주인 점장을 반환하는 메서드입니다.
     */
    @Transactional
    public Manager addStore(Long managerId, AddStoreForm form) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Store store = Store.of(manager, form);
        manager.getStores().add(store);
        storeRepository.save(store);

        managerRepository.save(manager);

        return manager;
    }

    /**
     * @param managerId
     * @return 점장의 아이디를 받아 점포들을 List 로 반환하는 메서드입니다.
     */
    @Transactional
    public List<Store> getMyStores(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return storeRepository.findAllById(manager.getStores().stream().map(Store::getId).collect(Collectors.toList()));
    }

    /**
     * @return 점포의 정보를 모두 반환하는 고객의 요청을 위한 메서드입니다.
     */
    @Transactional
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

}
