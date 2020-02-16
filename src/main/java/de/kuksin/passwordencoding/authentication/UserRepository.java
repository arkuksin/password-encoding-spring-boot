package de.kuksin.passwordencoding.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<userCredentials, String> {

    userCredentials findByUsername(String username);
}
