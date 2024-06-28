package live.easytrain.application.api.payments.exceptions;

public class PaymentInvalidCardException extends RuntimeException {

    public PaymentInvalidCardException(String message) {
        super(message);
    }
}
