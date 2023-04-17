package site.jaeu95.table_book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jaeu95.table_book.domain.form.SignUpForm;
import site.jaeu95.table_book.domain.model.Manager;
import site.jaeu95.table_book.domain.repository.ManagerRepository;
import site.jaeu95.table_book.exception.CustomException;
import site.jaeu95.table_book.exception.ErrorCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    public Optional<Manager> findByIdAndPhone(Long id, String phone) {
        return managerRepository.findById(id).stream().filter(manager -> manager.getPhone().equals(phone)).findFirst();
    }

    public Manager signup(SignUpForm form) {
        if (managerRepository.existsByPhone(form.getPhone())) {
            throw new CustomException(ErrorCode.USING_PHONE);
        } else {
            return managerRepository.save(Manager.from(form));
        }
    }

    public Optional<Manager> findValidManager(String phone, String password) {
        return managerRepository.findByPhone(phone).stream().filter(manager -> manager.getPassword().equals(password))
                .findFirst();
    }
    @Transactional
    public Manager makePartnerShip(Long managerId) {
        Optional<Manager> managerOptional = managerRepository.findById(managerId);

        if (managerOptional.isPresent()) {
            Manager manager = managerOptional.get();
            manager.setPartner(true);
            return manager;
        }
        throw new CustomException(ErrorCode.NOT_FOUND_USER);
    }
}
