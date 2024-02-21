package recipes.user;

import lombok.Data;
import recipes.validation.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {

    @ValidEmail
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;
}
