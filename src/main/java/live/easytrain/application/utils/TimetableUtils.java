package live.easytrain.application.utils;

import live.easytrain.application.entity.Timetable;
import org.springframework.context.annotation.Configuration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class TimetableUtils {

    // This method is to search for the trains that includes the chosen destination
    public List<Timetable> journeysToDestination(List<Timetable> timetables, String destination) {
        List<Timetable> journeysToDestination = new ArrayList<>();

        for (Timetable timetable : timetables) {
            if (timetable != null && !timetable.getDestination().equals(destination)) {
                String[] nextStations = timetable.getNextStations().split(" -> ");
                Pattern pattern = Pattern.compile("\\b\\w*" + destination + "\\w*\\b", Pattern.CASE_INSENSITIVE);
                for (String nextStation : nextStations) {
                    Matcher matcher = pattern.matcher(nextStation);
                    if (matcher.find()) {
                        // Delay is used to store train's destination (to be improved)
                        timetable.setDelay(timetable.getDestination());
                        timetable.setDestination(nextStation);
                        journeysToDestination.add(timetable);
                    }
                }
            }
        }

        return journeysToDestination;
    }

    // Returns the price according the found journeys stored in timetablesDestinations
    public List<Double> journeyPrice(List<Timetable> timetablesDestinations) {

        List<Double> journeyPrices = new ArrayList<>();
        double journeyPrice = 4.99;
        DecimalFormat df = new DecimalFormat("#.##");

        for (Timetable timetable : timetablesDestinations) {
            String[] nextStations = timetable.getNextStations().split(" -> ");
            for (int i = 0; i < nextStations.length; i++) {
                if (timetable.getDestination().equals(nextStations[i])) {
                    journeyPrice =Double.parseDouble( df.format(journeyPrice * (i + 1)));
                    journeyPrices.add(journeyPrice);
                }
            }
        }
        return journeyPrices;
    }
}
