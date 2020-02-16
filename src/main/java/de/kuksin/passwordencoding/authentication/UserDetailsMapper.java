package de.kuksin.passwordencoding.authentication;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {

    UserDetails toUserDetails(userCredentials userCredentials) {

        return User
                .withUsername(userCredentials.getUsername())
                .password(userCredentials.getPassword())
                .roles(userCredentials.getRoles().toArray(String[]::new))
                .build();
    }
}
