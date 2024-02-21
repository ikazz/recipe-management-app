package recipes.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerUser(RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return false;
        }

        var user = new AppUser();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        TODO:
        user.setAuthority("ROLE_USER"); // Assign a default authority
        // Do we need any additional work here
        userRepository.save(user);
        return true;
    }
}
