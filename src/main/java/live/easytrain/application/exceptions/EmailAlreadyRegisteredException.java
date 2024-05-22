package live.easytrain.application.exceptions;

import org.aspectj.bridge.IMessage;

public class EmailAlreadyRegisteredException extends RuntimeException {

    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
