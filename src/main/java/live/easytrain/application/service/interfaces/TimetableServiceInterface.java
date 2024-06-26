package live.easytrain.application.service.interfaces;

import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
public interface TimetableServiceInterface {
    List<Timetable> saveTimetableData(String stationName, LocalDate date, LocalTime hour, boolean recentChanges);
    List<Station> findAllEvaNumberByStationName(String stationName);
    Integer evaNumberByStationName(String stationName);
    List<Timetable> fetchTimetableDataFromAPI(String stationName, LocalDate date, LocalTime hour);
    List<Timetable> getAllTimetables();
    Timetable getJourneyFromTimetableById(Long journeyId);

}
