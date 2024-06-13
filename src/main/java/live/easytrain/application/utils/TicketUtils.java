package live.easytrain.application.utils;

import org.springframework.context.annotation.Configuration;


@Configuration
public class TicketUtils {

    public double underageTicket(double normalPrice, boolean underage) {

        if (underage) {
            return normalPrice * 0.5;
        }
        return normalPrice;
    }

    public double firstClassTicket(double normalPrice, int carriageClass) {

        if (carriageClass == 1) {
            return normalPrice * 1.25;
        }
        return normalPrice;
    }

    public double finalPrice(double normalPrice, int carriageClass, boolean underage) {

        double finalPrice = normalPrice;

        if (underage && carriageClass == 1) {
            finalPrice = underageTicket(normalPrice, true) * 1.25;
        } else if (underage && carriageClass == 2) {
            finalPrice = underageTicket(normalPrice, true);
        } else {
            finalPrice = firstClassTicket(normalPrice, carriageClass);
        }

        String str = String.format("%1.2f", finalPrice);
        finalPrice = Double.parseDouble(str);

        return finalPrice;
    }
}
