package de.kuksin.passwordencoding.persistence;

import de.kuksin.passwordencoding.authentication.userCredentials;
import de.kuksin.passwordencoding.authentication.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByUsername() {
        // given
        String username = "user";

        // when
        userCredentials userCredentials = userRepository.findByUsername(username);

        // then
        assertThat(userCredentials).isNotNull();
    }
}