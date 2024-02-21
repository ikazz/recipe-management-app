package recipes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AppUserController {
    private final AppUserRepository repository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserController(AppUserRepository repository,
                             UserService userService,
                             PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/api/register")
    public ResponseEntity<String> register(@Validated @RequestBody RegistrationRequest request) {
        return userService.registerUser(request) ?
                new ResponseEntity<>("User registered successfully", HttpStatus.OK) :
                new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
    }

}
