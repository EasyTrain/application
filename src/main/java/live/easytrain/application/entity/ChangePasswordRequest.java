package live.easytrain.application.entity;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {
    @NotEmpty(message = "Enter current password")
    @Size(min = 8, message = "")
    private String password;

    @NotEmpty(message = "New password must be at least 8 characters long")
    @Size(min = 8, message = "")
    private String newPassword;

    @NotEmpty(message = "Password must be at least 8 characters long")
    @Size(min = 8, message = "")
    private String confirmationPassword;

    public ChangePasswordRequest() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public boolean confirmPassword(String passwordToCheck) {
        return passwordToCheck.equals(password);
    }

    public boolean confirmPasswords() {
        return newPassword.equals(confirmationPassword);
    }


    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "currentPassword='" + password + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmationPassword='" + confirmationPassword + '\'' +
                '}';
    }
}