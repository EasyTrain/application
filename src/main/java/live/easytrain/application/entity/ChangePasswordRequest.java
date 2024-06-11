package live.easytrain.application.entity;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {
    private String currentPassword;

    @NotEmpty
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String newPassword;

    @NotEmpty
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String confirmationPassword;

    public ChangePasswordRequest() {
    }
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
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

    public boolean confirmPasswords() {
        return newPassword.equals(confirmationPassword);
    }


    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmationPassword='" + confirmationPassword + '\'' +
                '}';
    }
}