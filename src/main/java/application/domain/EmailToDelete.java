package application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "emails_to_delete")
public class EmailToDelete {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private LocalDateTime dateTime;

    public EmailToDelete(String email) {
        this.email = email;
        dateTime = LocalDateTime.now();
    }
}
