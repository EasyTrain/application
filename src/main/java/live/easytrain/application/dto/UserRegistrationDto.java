package live.easytrain.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegistrationDto(
        @NotEmpty(message = "Enter a first name")
        @Size(min = 2, message = "")
        String firstName,

        @NotEmpty(message = "Enter a last name")
        @Size(min = 2, message = "")
        String lastName,

        @NotEmpty(message = "Enter a valid email address")
        @Pattern(message = "",
                regexp = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
                        + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$")
        String email,

        @NotEmpty(message = "Enter phone number")
        String phoneNumber,

        @NotEmpty(message = "Password must be at least 8 characters long")
        @Size(min = 8, message = "")
        String password,
//        @NotNull(message = "Passwords must match")
        String confirmPassword

) {
}