package live.easytrain.application.service;

import jakarta.transaction.Transactional;
import live.easytrain.application.entity.User;
import live.easytrain.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public String deleteUser(Long id) {

        Optional<User> user = userRepository.findById(id);

        String message = "";

        if (user.isPresent()) {
            userRepository.delete(user.get());
            message = "User with " + id + " deleted successfully!";
        } else {
            throw new RuntimeException("User not found!");
        }
        return message;
    }

    @Override
    public boolean isPasswordConfirmed(User user) {
        return false;
    }
}