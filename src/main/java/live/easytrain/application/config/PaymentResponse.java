package live.easytrain.application.config;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class PaymentResponse {

//    private static String _API_PAYMENT_URL = "http://localhost:8082/api-payments/pay?encryptedData=%s&billValue=%f";

    // Application api
    private static String _API_PAYMENT_URL = "http://localhost:8081/easytrain/api/pay?encryptedData=%s&billValue=%f";
    // private final static String ip_address = System.getenv("PUBLIC_IP");

    public String fetchPaymentResponse(String encryptedData, double amount) {

       /* if (ip_address != null) {
            _API_PAYMENT_URL = "http://" + ip_address + ":8082/api-payments/pay?encryptedData=%s&billValue=%f";
            System.out.println(_API_PAYMENT_URL);
        }*/

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
