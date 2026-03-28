package lk.ijse.eca.lendingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LendingDTO {

    public interface OnCreate {}  // <-- Validation group for creation

    private String id;          // MongoDB-generated ID

    @NotBlank(groups = OnCreate.class, message = "User NIC is required")
    private String userNIC;

    @NotBlank(groups = OnCreate.class, message = "Book ID is required")
    private String bookId;

    @NotNull(groups = OnCreate.class, message = "Lending date is required")
    private LocalDate lendingDate;

    private LocalDate dueDate;

    private boolean returned;
}