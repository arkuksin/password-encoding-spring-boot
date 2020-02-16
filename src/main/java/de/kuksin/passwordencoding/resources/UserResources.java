package de.kuksin.passwordencoding.resources;

import de.kuksin.passwordencoding.authentication.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@Transactional
public class UserResources {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResources(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/registration")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@RequestBody UserCredentials userCredentials) {
        de.kuksin.passwordencoding.authentication.UserCredentials userCredetianls = de.kuksin.passwordencoding.authentication.UserCredentials.builder()
                .enabled(true)
                .username(userCredentials.getUsername())
                .password(passwordEncoder.encode(userCredentials.getPassword()))
                .roles(Set.of("USER"))
                .build();
        userRepository.save(userCredetianls);
    }
}
