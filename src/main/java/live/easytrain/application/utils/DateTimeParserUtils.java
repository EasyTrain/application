package live.easytrain.application.utils;

import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class DateTimeParserUtils {

    public LocalTime parseStringToLocalTime(String timeStr) {

        if (timeStr == null || timeStr.trim().isEmpty()) {
            return null;
        }

        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
    }

    // Returns the date in format yyMMdd to be used for the api request
    public String formatLocalDateToString(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] arrDate = localDate.format(dateFormatter).split("-");
        String formattedDate = arrDate[0].substring(2) + arrDate[1] + arrDate[2];

        return formattedDate.trim();
    }

    // Returns the time in format HH to be used for the api request
    public String formatLocalTimeToString(LocalTime localTime) {
        if (localTime == null) {
            return null;
        }
        return localTime.toString().substring(0, 2);
    }
}
