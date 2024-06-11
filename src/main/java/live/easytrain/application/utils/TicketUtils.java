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

        if (underage && carriageClass == 1) {
            return underageTicket(normalPrice, true) * 1.25;
        } else if (underage && carriageClass == 2) {
            return underageTicket(normalPrice, true);
        } else {
            return firstClassTicket(normalPrice, carriageClass);
        }
    }
}
