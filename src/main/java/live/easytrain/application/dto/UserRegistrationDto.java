package live.easytrain.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegistrationDto(
        @NotEmpty(message = "Enter first name")
        @Size(min = 2, message = "First name must be at least 2 characters long")
        String firstName,

        @NotEmpty(message = "Enter last name")
        @Size(min = 2, message = "Last name must be at least 2 characters long")
        String lastName,

        @NotEmpty(message = "Enter email address")
        @Pattern(message = "Enter a valid email address",
                regexp = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
                        + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$")
        String email,

        @NotEmpty(message = "Enter phone number")
        String phoneNumber,

        @NotEmpty(message = "Enter password")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,
//        @NotNull(message = "Passwords must match")
        String confirmPassword

) {
}