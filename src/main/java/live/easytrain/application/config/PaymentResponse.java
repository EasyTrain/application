package live.easytrain.application.config;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class PaymentResponse {

    private static final String _API_PAYMENT_URL = "http://localhost:8082/api-payments/pay?encryptedData=%s&billValue=%f";

    public String fetchPaymentResponse(String encryptedData, double amount) {

        String paymentStatus = "";
        OkHttpClient client = new OkHttpClient();
        String url = String.format(_API_PAYMENT_URL, encryptedData, amount);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (response.isSuccessful()) {
                paymentStatus = response.body().string();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return paymentStatus;
    }


}
