package live.easytrain.application.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import live.easytrain.application.entity.ChangePasswordRequest;
import live.easytrain.application.entity.User;
import live.easytrain.application.exceptions.UserNotFoundException;
import live.easytrain.application.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    private UserRepository userRepository;
    private JavaMailSender javaMailSender;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(User user, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String randomCode = RandomStringUtils.randomAlphabetic(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        userRepository.save(user);

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
                + "Your company name.";

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

    public void changePassword(ChangePasswordRequest changePasswordRequest) {

        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

        System.out.println("User: " + user.get().getPassword());
        System.out.println("ChangePasswordRequest: " + changePasswordRequest.getCurrentPassword());

        if(!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.get().getPassword())) {
            throw new IllegalStateException("Wrong password");
        }

        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords don't match");
        }

        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user.get());
        }

    }
}