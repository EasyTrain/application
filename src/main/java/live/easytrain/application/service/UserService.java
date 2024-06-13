package live.easytrain.application.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import live.easytrain.application.dto.UserRegistrationDto;
import live.easytrain.application.entity.ChangePasswordRequest;
import live.easytrain.application.entity.Email;
import live.easytrain.application.entity.Role;
import live.easytrain.application.entity.User;
import live.easytrain.application.exceptions.UserNotFoundException;
import live.easytrain.application.repository.RoleRepository;
import live.easytrain.application.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private JavaMailSender javaMailSender;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegistrationDto userRegistrationDto, String siteURL)
            throws UnsupportedEncodingException, MessagingException {

        // check if user is already registered
        boolean userExists = userRepository.findByEmail(userRegistrationDto.email()).isPresent();

        if (userExists) {
            throw new IllegalStateException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(userRegistrationDto.password());

        User user = new User(userRegistrationDto.firstName(),
                userRegistrationDto.lastName(),
                userRegistrationDto.email(),
                userRegistrationDto.phoneNumber(),
                encodedPassword
        );

        String randomCode = RandomStringUtils.randomAlphabetic(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        userRepository.save(user);

        Role role = new Role("ROLE_USER", user);
        roleRepository.save(role);

        user.setRoles(Arrays.asList(role));

        sendVerificationEmail(user, siteURL);
    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "easyice2024@gmail.com";
        String senderName = "EasyTrain";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "EasyTrain Team.";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        javaMailSender.send(message);

    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.getEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }
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
            throw new UserNotFoundException("User not found!");
        }
        return message;
    }

    @Override
    public boolean isPasswordConfirmed(User user) {
        return false;
    }

    @Override
    public void submitEmail(String email, String siteURL) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            System.out.println("User is present. Disabling account!");
            String randomCode = RandomStringUtils.randomAlphabetic(64);
            user.get().setVerificationCode(randomCode);
            user.get().setEnabled(false);
            userRepository.save(user.get());

            try {
                sendForgotEmail(user.get(), siteURL);
            } catch (MessagingException | UnsupportedEncodingException e) {
                System.out.println(e.fillInStackTrace());
            }
        }

    }

    @Override
    public User getUserByEmail(String email) {

        User user = new User();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new UserNotFoundException("User not found");
        }

        return user;
    }

    private void sendForgotEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "easyice2024@gmail.com";
        String senderName = "EasyTrain";
        String subject = "Please reset your password";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to reset your password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">RESET PASSWORD</a></h3>"
                + "Thank you,<br>"
                + "EasyTrain Team.";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
        String verifyURL = siteURL + "/reset_password?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        javaMailSender.send(message);
    }

    public boolean resetPassword(Email email, ChangePasswordRequest changePasswordRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(email.getEmailAddress());

        if (optionalUser.isPresent()) {
            if (changePasswordRequest.confirmPasswords()) {
                User user = optionalUser.get();
                String encodedPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());

                user.setPassword(encodedPassword);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}