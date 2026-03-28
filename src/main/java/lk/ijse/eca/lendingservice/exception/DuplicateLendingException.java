package lk.ijse.eca.lendingservice.exception;

public class DuplicateLendingException extends RuntimeException {

    public DuplicateLendingException(String lemdingId) {
        super("Lending already exists with ID: " + lemdingId);
    }
}
