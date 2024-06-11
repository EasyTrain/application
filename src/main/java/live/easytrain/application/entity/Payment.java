package live.easytrain.application.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Payment {

    private String cardHolder;

    private String cardNumber;

    @NotNull(message = "CVC must be a numeric value.")
    @Size(min = 3, max = 3, message = "CVC must be a numeric value.")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = "CVC must be a numeric value")
    private String cvc;

    @NotNull(message = "Invalid date format.")
    @Size(min = 4, max = 4, message = "Invalid date format.")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = "Invalid date format")
    private String expiryDate;

    public Payment(String cardHolder, String cardNumber, String cvc, String expiryDate) {
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.cvc = cvc;
    }

    public Payment() {
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
