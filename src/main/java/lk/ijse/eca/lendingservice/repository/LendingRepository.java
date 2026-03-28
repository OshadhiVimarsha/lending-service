package lk.ijse.eca.lendingservice.repository;

import lk.ijse.eca.lendingservice.entity.Lending;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LendingRepository extends MongoRepository<Lending, String> {

    // Find a lending for a user and book that is not returned yet
    Optional<Lending> findByUserNICAndBookIdAndReturnedFalse(String userNIC, String bookId);

    // Optional: find all active lendings for a user
    Optional<Lending> findAllByUserNICAndReturnedFalse(String UserNIC);

    // Optional: find all active lendings for a book
    Optional<Lending> findAllByBookIdAndReturnedFalse(String bookId);
}