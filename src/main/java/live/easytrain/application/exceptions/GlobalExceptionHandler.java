package live.easytrain.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<EasyTrainException> userNotFoundException(UserNotFoundException e) {

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();

        return new ResponseEntity<>(
                new EasyTrainException(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis(),
                        stackTrace), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = EmailAlreadyRegisteredException.class)
    public ResponseEntity<EasyTrainException> emailAlreadyRegisteredException(EmailAlreadyRegisteredException e) {

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();

        return new ResponseEntity<>(
                new EasyTrainException(HttpStatus.CONFLICT.value(), e.getMessage(), System.currentTimeMillis(),
                stackTrace), HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(value = EvaNumberNotFoundException.class)
    public ResponseEntity<EasyTrainException> evaNumberNotFoundException(EvaNumberNotFoundException e) {

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();

        return new ResponseEntity<>(
                new EasyTrainException(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis(),
                        stackTrace), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = BookingNotFoundException.class)
    public ResponseEntity<EasyTrainException> bookingNotFoundException(BookingNotFoundException e) {

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();

        return new ResponseEntity<>(
                new EasyTrainException(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis(),
                        stackTrace), HttpStatus.NOT_FOUND
        );
    }
}
