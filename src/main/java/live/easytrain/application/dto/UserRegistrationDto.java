package live.easytrain.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserRegistrationDto(
        @NotEmpty(message = "Enter a first name")
        String firstName,
        @NotEmpty(message = "Enter a last name")
        String lastName,
        @NotEmpty(message = "Enter an email address")
        String email,

        @NotEmpty(message = "Enter a phone number")
        String phoneNumber,
        @NotEmpty(message = "Enter a password")
        String password,
        @NotNull(message = "Passwords must match")
        String confirmPassword

) {
}