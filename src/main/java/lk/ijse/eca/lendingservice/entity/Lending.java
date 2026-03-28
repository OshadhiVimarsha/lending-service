package lk.ijse.eca.lendingservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "lendings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lending {

    @Id
    private String id;          // Lending ID

    private String userNIC;      // Reference to User/Reader
    private String bookId;      // Reference to Book

    private LocalDate lendingDate;
    private LocalDate dueDate;
    private boolean returned;   // true if book returned
}