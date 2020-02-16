package de.kuksin.passwordencoding.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserDetailsMapperTest {

    private UserDetailsMapper userDetailsMapper = new UserDetailsMapper();

    @Test
    void toUserDetails() {
        // given
        UserCredentials userCredentials = UserCredentials.builder()
                .enabled(true)
                .password("password")
                .username("user")
                .roles(Set.of("USER", "ADMIN"))
                .build();

        // when
        UserDetails userDetails = userDetailsMapper.toUserDetails(userCredentials);

        // then
        assertThat(userDetails.getUsername()).isEqualTo("user");
        assertThat(userDetails.getPassword()).isEqualTo("password");
        assertThat(userDetails.isEnabled()).isTrue();
    }
}