package lk.ijse.eca.lendingservice.service.impl;

import lk.ijse.eca.lendingservice.dto.LendingDTO;
import lk.ijse.eca.lendingservice.entity.Lending;
import lk.ijse.eca.lendingservice.exception.DuplicateLendingException;
import lk.ijse.eca.lendingservice.exception.LendingNotFoundException;
import lk.ijse.eca.lendingservice.mapper.LendingMapper;
import lk.ijse.eca.lendingservice.repository.LendingRepository;
import lk.ijse.eca.lendingservice.service.LendingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LendingServiceImpl implements LendingService {

    private final LendingRepository lendingRepository;
    private final LendingMapper lendingMapper;

    // --------------------- Create Lending ---------------------
    @Override
    @Transactional
    public Lending createLending(LendingDTO dto) {
        log.debug("Creating lending - userId: {}, bookId: {}", dto.getUserNIC(), dto.getBookId());

        // Check if same book already lent and not returned
        lendingRepository.findByUserNICAndBookIdAndReturnedFalse(dto.getUserNIC(), dto.getBookId())
                .ifPresent(l -> {
                    log.warn("Duplicate lending detected - userId: {}, bookId: {}", dto.getUserNIC(), dto.getBookId());
                    throw new DuplicateLendingException("This book is already lent to the user.");
                });

        Lending entity = lendingMapper.toEntity(dto);
        Lending saved = lendingRepository.save(entity);
        log.info("Lending created successfully - id: {}", saved.getId());
        return saved;
    }

    // --------------------- Get Lending by ID ---------------------
    @Override
    @Transactional(readOnly = true)
    public Lending getLendingById(String lendingId) {
        log.debug("Fetching lending with ID: {}", lendingId);
        return lendingRepository.findById(lendingId)
                .orElseThrow(() -> {
                    log.warn("Lending not found: {}", lendingId);
                    return new LendingNotFoundException(lendingId);
                });
    }

    // --------------------- Get All Lendings ---------------------
    @Override
    @Transactional(readOnly = true)
    public List<Lending> getAllLendings() {
        log.debug("Fetching all lendings");
        List<Lending> lendings = lendingRepository.findAll();
        log.debug("Fetched {} lending(s)", lendings.size());
        return lendings;
    }

    // --------------------- Return Book ---------------------
    @Override
    @Transactional
    public Lending returnBook(String lendingId) {
        log.debug("Returning book for lending ID: {}", lendingId);
        Lending lending = lendingRepository.findById(lendingId)
                .orElseThrow(() -> {
                    log.warn("Lending not found for return: {}", lendingId);
                    return new LendingNotFoundException(lendingId);
                });

        lending.setReturned(true);
        Lending updated = lendingRepository.save(lending);
        log.info("Book returned successfully - lending ID: {}", lendingId);
        return updated;
    }

    // --------------------- Delete Lending ---------------------
    @Override
    @Transactional
    public void deleteLending(String lendingId) {
        log.debug("Deleting lending with ID: {}", lendingId);
        Lending lending = lendingRepository.findById(lendingId)
                .orElseThrow(() -> {
                    log.warn("Lending not found for deletion: {}", lendingId);
                    return new LendingNotFoundException(lendingId);
                });

        lendingRepository.delete(lending);
        log.info("Lending deleted successfully - id: {}", lendingId);
    }
}