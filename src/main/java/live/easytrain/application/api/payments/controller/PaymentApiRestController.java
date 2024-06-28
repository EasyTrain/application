package live.easytrain.application.api.payments.controller;

import jakarta.servlet.http.HttpServletRequest;
import live.easytrain.application.api.payments.entity.PaymentApiEntity;
import live.easytrain.application.api.payments.exceptions.PaymentBalanceExceptions;
import live.easytrain.application.api.payments.exceptions.PaymentExceptions;
import live.easytrain.application.api.payments.exceptions.PaymentInvalidCardException;
import live.easytrain.application.api.payments.service.PaymentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class PaymentApiRestController {

    private PaymentServiceInterface paymentService;

    @Autowired
    public PaymentApiRestController(PaymentServiceInterface paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/createCard")
    public PaymentApiEntity createCard(@RequestBody PaymentApiEntity payment) {
        System.out.println("Aqui");
        payment.setId(0L);

        return paymentService.createPayment(payment);
    }

    @GetMapping("/pay")
   public String pay(@RequestParam("encryptedData") String encryptedData, @RequestParam("billValue") double billValue) {

        return paymentService.getPaymentId(encryptedData, billValue);
    }


    @ExceptionHandler
    public ResponseEntity<PaymentExceptions> handleInvalidExceptions(PaymentInvalidCardException e) {

        PaymentExceptions exception = new PaymentExceptions();
        exception.setStatus(HttpStatus.NOT_FOUND.value());
        exception.setMessage(e.getMessage());
        exception.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PaymentExceptions> handleBalanceExceptions(PaymentBalanceExceptions e) {

        PaymentExceptions exception = new PaymentExceptions();
        exception.setStatus(HttpStatus.PAYMENT_REQUIRED.value());
        exception.setMessage(e.getMessage());
        exception.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        return new ResponseEntity<>(exception, HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler
    public ResponseEntity<PaymentExceptions> handlerBadReqExceptions(Exception e) {

        PaymentExceptions exception = new PaymentExceptions();
        exception.setStatus(HttpStatus.BAD_REQUEST.value());
        exception.setMessage(e.getMessage());
        exception.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/test")
    public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        System.out.println(siteURL.replace(request.getServletPath(), ""));
        return siteURL.replace(request.getServletPath(), "");
    }
}
