package de.kuksin.passwordencoding.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class JdbcUserDetailPasswordService implements UserDetailsPasswordService {

    private final UserRepository userRepository;

    private final UserDetailsMapper userDetailsMapper;

    public JdbcUserDetailPasswordService(UserRepository userRepository, UserDetailsMapper userDetailsMapper) {
        this.userRepository = userRepository;
        this.userDetailsMapper = userDetailsMapper;
    }


    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        UserCredetianls userCredetianls = userRepository.findByUsername(user.getUsername());
        userCredetianls.setPassword(newPassword);
        return userDetailsMapper.toUserDetails(userCredetianls);
    }
}
