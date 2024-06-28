package live.easytrain.application.api.payments.service;


import live.easytrain.application.api.payments.entity.PaymentApiEntity;

public interface PaymentServiceInterface {

    PaymentApiEntity createPayment(PaymentApiEntity payment);

    String getPaymentId(String encryptedData, double billValue);
}
