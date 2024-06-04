package live.easytrain.application.service;

import jakarta.transaction.Transactional;
import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.exceptions.StationNotFoundException;
import live.easytrain.application.external.binder.ApiDataToEntities;
import live.easytrain.application.repository.StationRepo;
import live.easytrain.application.repository.TimetableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class TimetableService implements TimetableServiceInterface {
    private TimetableRepo timetableRepo;
    private ApiDataToEntities apiDataToEntities;
    private StationRepo stationRepo;
    private StationServiceInterface stationServiceInterface;
    @Autowired
    public TimetableService(StationServiceInterface stationServiceInterface, StationRepo stationRepo, TimetableRepo timetableRepo, ApiDataToEntities apiDataToEntities) {
        this.timetableRepo = timetableRepo;
        this.apiDataToEntities = apiDataToEntities;
        this.stationRepo = stationRepo;
        this.stationServiceInterface = stationServiceInterface;
    }

    @Override
    @Transactional
    public void saveTimetableData(String stationName, LocalDate date, LocalTime hour, boolean recentChanges) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] arrDate = date.format(dateFormatter).split("-");
        String formattedDate = arrDate[0].substring(2) + arrDate[1] + arrDate[2];

        System.out.println(formattedDate + " Date");

        String formattedHour = hour.toString().substring(0, 2);

        System.out.println(formattedHour + " hour");

        Integer evaNumber = stationServiceInterface.evaNumberByStationName(stationName);
        List<Timetable> timetables = apiDataToEntities.apiDataToTimetable(evaNumber, formattedDate, formattedHour, recentChanges);

        timetableRepo.saveAll(timetables);
    }

    // Find all stations whose names start with the given station name
    @Override
    public List<Station> findAllEvaNumberByStationName(String stationName) {
        return stationRepo.findByStationNameStartingWith(stationName);
    }

    // Find the EVA number directly from the repository
    @Override
    public Integer evaNumberByStationName(String stationName) {
        Station station = stationRepo.findByStationName(stationName);
        if (station == null) {
            throw new StationNotFoundException("Station not found: " + stationName);
        }
        return Integer.valueOf(station.getEvaNumber());
    }

    // Fetch timetable data from the API based on the provided parameters
    @Override
    public List<Timetable> fetchTimetableDataFromAPI(String stationName, LocalDate date, LocalTime hour,
                                                     LocalDate startDate, LocalTime startTime) {
        List<Station> stations = findAllEvaNumberByStationName(stationName);
        if (stations.isEmpty()) {
            throw new StationNotFoundException("Station not found: " + stationName);
        }

        Integer evaNumber = evaNumberByStationName(stationName);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] arrDate = date.format(dateFormatter).split("-");
        String formattedDate = arrDate[0].substring(2) + arrDate[1] + arrDate[2];

        String formattedHour = hour.toString().substring(0, 2);

        return apiDataToEntities.apiDataToTimetable(evaNumber, formattedDate, formattedHour, false);
    }
    @Override
    public List<Timetable> getAllTimetables() {
        return timetableRepo.findAll();
    }

//    @Override
//    public List<Timetable> checkDelays(String scheduleId) {
//        List<Timetable> timetables = timetableRepo.findByScheduleId(scheduleId);
//        if (timetables.isEmpty()) {
//            throw new RuntimeException("No timetables found for the schedule ID: " + scheduleId);
//        }
//        for (Timetable timetable : timetables) {
//            Timetable updatedTimetable = apiDataToEntities.fetchUpdatedTimetable(Integer.valueOf(timetable.getStation().getEvaNumber()), timetable.getScheduleId());
//            timetable.updateDelayInfo(updatedTimetable);
//        }
//        timetableRepo.saveAll(timetables);
//        return timetables;
//    }
}







