package recipes.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class AppUser {
    @Id
    @GeneratedValue
    private Integer id;

    private String username;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String authority;

}