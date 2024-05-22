package live.easytrain.application.service;

import live.easytrain.application.entity.SecurityUser;
import live.easytrain.application.entity.User;
import live.easytrain.application.repository.UserRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        } else if (user.isEmpty()) {
            throw new DisabledException("User not verified, please verify your email before login.");
        }

        SecurityUser securityUser = new SecurityUser(user.get());

        return new org.springframework.security.core.userdetails.User(
                securityUser.getUser().getEmail(),
                securityUser.getUser().getPassword(),
                securityUser.getAuthorities());
    }
}