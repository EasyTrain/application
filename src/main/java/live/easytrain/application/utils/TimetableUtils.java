package live.easytrain.application.utils;

import live.easytrain.application.entity.Booking;
import live.easytrain.application.entity.Timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class TimetableUtils {

    private DateTimeParserUtils dateTimeParser;

    @Autowired
    public TimetableUtils(DateTimeParserUtils dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

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

    public Booking timetableToBooking(List<Timetable> timetables, String trainNumber, double price) {

        Timetable selectedTrain = timetables.stream().filter(
                m -> m.getTrainNumber().equalsIgnoreCase(trainNumber)
        ).findFirst().orElse(null);

        if (selectedTrain == null) {
            return null;
        }

        return new Booking(selectedTrain.getCurrentStation(), selectedTrain.getDestination(), LocalDate.now(),
                dateTimeParser.parseStringToLocalTime(selectedTrain.getArrivalTime()),
                dateTimeParser.parseStringToLocalTime(selectedTrain.getDepartureTime()), selectedTrain.getDelay(),
                selectedTrain.getNextStations(), price, 0.0, trainNumber, selectedTrain.getPlatformNumber());
    }

    public List<Timetable> bookingToTimetableDestinations(Booking booking) {
        List<Timetable> timetables = new ArrayList<>();

        timetables.add(new Timetable(booking.getFromLocation(), booking.getToLocation(), booking.getTrainsDestination(),
                "N/A",booking.getArrivalTime().toString(), booking.getDepartureTime().toString(),
                booking.getTrainNumber(), booking.getPlatformNumber(), booking.getArrivalTime().toString(),
                booking.getDepartureTime().toString(), "N/A", booking.getJourneyDetails(),
                "N/A", booking.getFromLocation()));

        return timetables;
    }

}
