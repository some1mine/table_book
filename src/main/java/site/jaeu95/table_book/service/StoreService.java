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

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ManagerRepository managerRepository;

    @Transactional
    public Store getStore(Long id) {
        return storeRepository.getById(id);
    }

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

    @Transactional
    public List<Store> getMyStores(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return storeRepository.findAllById(manager.getStores().stream().map(Store::getId).collect(Collectors.toList()));
    }

    @Transactional
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

}
