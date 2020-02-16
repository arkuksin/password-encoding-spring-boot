package de.kuksin.passwordencoding.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JdbcUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserDetailsMapper userDetailsMapper;

    public JdbcUserDetailsService(UserRepository userRepository, UserDetailsMapper userDetailsMapper) {
        this.userRepository = userRepository;
        this.userDetailsMapper = userDetailsMapper;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredetianls userCredetianls = userRepository.findByUsername(username);
        return userDetailsMapper.toUserDetails(userCredetianls);
    }
}
