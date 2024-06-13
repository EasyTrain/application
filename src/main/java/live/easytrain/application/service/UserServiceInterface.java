package live.easytrain.application.service;

import live.easytrain.application.entity.User;

public interface UserServiceInterface {

    User saveUser(User user);

    User updateUser(User user);

    String deleteUser(Long id);

    boolean isPasswordConfirmed(User user);

    void submitEmail(String email, String siteURL);

    User getUserByEmail(String email);
}