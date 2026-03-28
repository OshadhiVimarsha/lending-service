package lk.ijse.eca.lendingservice.service;

import lk.ijse.eca.lendingservice.dto.LendingDTO;
import lk.ijse.eca.lendingservice.entity.Lending;

import java.util.List;

public interface LendingService {

    // Create a new lending record
    Lending createLending(LendingDTO dto);

    // Get a lending record by ID
    Lending getLendingById(String lendingId);

    // Get all lendings
    List<Lending> getAllLendings();

    // Return a book (mark as returned)
    Lending returnBook(String lendingId);

    // Delete a lending record (optional, or could just mark returned)
    void deleteLending(String lendingId);
}