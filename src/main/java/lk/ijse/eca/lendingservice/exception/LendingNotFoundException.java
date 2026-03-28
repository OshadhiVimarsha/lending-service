package lk.ijse.eca.lendingservice.exception;

public class LendingNotFoundException extends RuntimeException {

    public LendingNotFoundException(String lendingId) {
        super("Lending not found with ID: " + lendingId);
    }
}
