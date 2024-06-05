package live.easytrain.application.service;

import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
public interface TimetableServiceInterface {
    List<Timetable> saveTimetableData(String stationName, LocalDate date, LocalTime hour, boolean recentChanges);
    List<Station> findAllEvaNumberByStationName(String stationName);
    // Find the EVA number directly from the repository
    Integer evaNumberByStationName(String stationName);

    List<Timetable> fetchTimetableDataFromAPI(String stationName, LocalDate date, LocalTime hour, LocalDate startDate, LocalTime startTime);

    List<Timetable> getAllTimetables();
}
