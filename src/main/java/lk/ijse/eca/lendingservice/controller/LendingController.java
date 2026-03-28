package lk.ijse.eca.lendingservice.controller;

import jakarta.validation.Valid;
import lk.ijse.eca.lendingservice.dto.LendingDTO;
import lk.ijse.eca.lendingservice.entity.Lending;
import lk.ijse.eca.lendingservice.service.LendingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lendings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class LendingController {

    private final LendingService lendingService;

    // --------------------- Create Lending ---------------------
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Lending> createLending(
            @Validated(LendingDTO.OnCreate.class) @RequestBody LendingDTO dto) {
        log.info("POST /api/v1/lendings - userNIC: {}, bookId: {}", dto.getUserNIC(), dto.getBookId());
        Lending saved = lendingService.createLending(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // --------------------- Return Lending ---------------------
    @PutMapping("/{id}/return")
    public ResponseEntity<Lending> returnBook(@PathVariable String id) {
        log.info("PUT /api/v1/lendings/{}/return", id);
        Lending returned = lendingService.returnBook(id);
        return ResponseEntity.ok(returned);
    }

    // --------------------- Get Lending by ID ---------------------
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lending> getLending(@PathVariable String id) {
        log.info("GET /api/v1/lendings/{}", id);
        Lending lending = lendingService.getAllLendings()
                .stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Lending not found"));
        return ResponseEntity.ok(lending);
    }

    // --------------------- Get All Lendings ---------------------
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Lending>> getAllLendings() {
        log.info("GET /api/v1/lendings - retrieving all lendings");
        List<Lending> lendings = lendingService.getAllLendings();
        return ResponseEntity.ok(lendings);
    }

    // --------------------- Delete Lending ---------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLending(@PathVariable String id) {
        log.info("DELETE /api/v1/lendings/{}", id);
        lendingService.getAllLendings()
                .stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Lending not found"));

        lendingService.returnBook(id); // optionally mark as returned or delete from DB
        return ResponseEntity.noContent().build();
    }
}