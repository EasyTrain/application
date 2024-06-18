package live.easytrain.application.utils;

import live.easytrain.application.entity.JourneyUpdate;
import live.easytrain.application.entity.Timetable;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JourneyUpdateConverter {
    // Method to convert a Timetable object to a JourneyUpdate object
    public static JourneyUpdate timetableToJourneyUpdate(Timetable timetable) {
        JourneyUpdate update = new JourneyUpdate();
        update.setScheduleId(truncateString(timetable.getScheduleId(), 255));
        update.setDelay(timetable.getDelay());
        update.setArrivalTime(timetable.getArrivalTime());
        update.setDepartureTime(timetable.getDepartureTime());
        update.setChangedPathFrom(truncateString(timetable.getStartingPoint(), 255));
        update.setChangedPathTo(truncateString(timetable.getDestination(), 255));
        update.setTrainNumber(truncateString(timetable.getTrainNumber(), 255));
        update.setPlatformNumber(truncateString(timetable.getPlatformNumber(), 255));
        return update;
    }

    private static String truncateString(String value, int maxLength) {
        if (value != null && value.length() > maxLength) {
            return value.substring(0, maxLength);
        }
        return value;
    }
}
