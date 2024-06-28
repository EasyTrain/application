package live.easytrain.application.api.payments.service;

import jakarta.transaction.Transactional;
import live.easytrain.application.api.payments.entity.PaymentApiEntity;
import live.easytrain.application.api.payments.exceptions.PaymentBalanceExceptions;
import live.easytrain.application.api.payments.exceptions.PaymentInvalidCardException;
import live.easytrain.application.api.payments.repo.PaymentApiEntityRepo;
import live.easytrain.application.config.PaymentsEncryptConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentServiceInterface{

    private PaymentApiEntityRepo paymentRepo;
    private PaymentsEncryptConfig securityConfig;

    @Autowired
    public PaymentServiceImpl(PaymentApiEntityRepo paymentRepo, PaymentsEncryptConfig securityConfig) {
        this.paymentRepo = paymentRepo;
        this.securityConfig = securityConfig;
    }

    @Override
    @Transactional
    public PaymentApiEntity createPayment(PaymentApiEntity payment) {


        String encrypted = null;

            encrypted = securityConfig.encrypt(payment.getCardNumber() + payment.getExpiryDate()
                    + payment.getCvc(), "Eisenbahn");

        payment.setEncryptedData(encrypted);
        paymentRepo.save(payment);

        return payment;
    }

    @Override
    public String getPaymentId(String encryptedData, double billValue) {

        PaymentApiEntity payment = paymentRepo.findByEncryptedData(encryptedData);

        String dec = securityConfig.decrypt(encryptedData, "Eisenbahn");
            System.out.println(dec + " : data");


        if(payment == null) {
            throw new PaymentInvalidCardException("Unsuccessful: Card is not valid");
//            return  "Unsuccessful: Card is not valid";
        }

        if(payment.getBalance() < billValue){
            throw new PaymentBalanceExceptions("Unsuccessful: Insufficient balance");
//           return  "Unsuccessful: Insufficient balance";
        }

        payment.setBalance(payment.getBalance() - billValue);
        paymentRepo.save(payment);

        return "success";
    }
}
