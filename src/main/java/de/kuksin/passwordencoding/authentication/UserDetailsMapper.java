package de.kuksin.passwordencoding.authentication;

import de.kuksin.passwordencoding.authentication.UserCredetianls;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {

    UserDetails toUserDetails(UserCredetianls userCredetianls) {

        return User
                .withUsername(userCredetianls.getUsername())
                .password(userCredetianls.getPassword())
                .roles(userCredetianls.getRoles().toArray(String[]::new))
                .build();
    }
}
