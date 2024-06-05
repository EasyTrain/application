package live.easytrain.application.service;

import jakarta.transaction.Transactional;
import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.exceptions.StationNotFoundException;
import live.easytrain.application.api.binder.ApiDataToEntities;
import live.easytrain.application.repository.StationRepo;
import live.easytrain.application.repository.TimetableRepo;
import live.easytrain.application.utils.DateTimeParserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Service
public class TimetableService implements TimetableServiceInterface {
    private TimetableRepo timetableRepo;
    private ApiDataToEntities apiDataToEntities;
    private StationRepo stationRepo;
    private StationServiceInterface stationServiceInterface;
    private DateTimeParserUtils dateTimeParser;

    @Autowired
    public TimetableService(StationServiceInterface stationServiceInterface, StationRepo stationRepo,
                            TimetableRepo timetableRepo, ApiDataToEntities apiDataToEntities, DateTimeParserUtils dateTimeParser) {
        this.timetableRepo = timetableRepo;
        this.apiDataToEntities = apiDataToEntities;
        this.stationRepo = stationRepo;
        this.stationServiceInterface = stationServiceInterface;
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    @Transactional
    public List<Timetable> saveTimetableData(String stationName, LocalDate date, LocalTime hour, boolean recentChanges) {

        Integer evaNumber = stationServiceInterface.evaNumberByStationName(stationName);
        List<Timetable> timetables = apiDataToEntities.apiDataToTimetable(evaNumber, dateTimeParser.formatLocalDateToString(date),
                dateTimeParser.formatLocalTimeToString(hour), recentChanges);

        timetableRepo.saveAll(timetables);

        return  timetables;
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
    public List<Timetable> fetchTimetableDataFromAPI(String stationName, LocalDate date, LocalTime hour) {
        List<Station> stations = findAllEvaNumberByStationName(stationName);
        if (stations.isEmpty()) {
            throw new StationNotFoundException("Station not found: " + stationName);
        }

        Integer evaNumber = evaNumberByStationName(stationName);

        return apiDataToEntities.apiDataToTimetable(evaNumber,dateTimeParser.formatLocalDateToString(date),
                dateTimeParser.formatLocalTimeToString(hour), false);
    }
    @Override
    public List<Timetable> getAllTimetables() {
        return timetableRepo.findAll();
    }

}







