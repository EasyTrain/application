package live.easytrain.application.service;


import jakarta.transaction.Transactional;
import live.easytrain.application.config.XmlResponse;

import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.external.binder.ApiDataToEntities;
import live.easytrain.application.repository.StationRepo;
import live.easytrain.application.repository.TimetableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class TimetableStationService implements TimetableServiceInterface {

    private final TimetableRepo timetableRepo;
    private final StationRepo stationRepo;
    private final XmlResponse xmlResponse;
    private final ApiDataToEntities apiDataToEntities;

    @Autowired
    public TimetableStationService(TimetableRepo timetableRepo, StationRepo stationRepo, XmlResponse xmlResponse, ApiDataToEntities apiDataToEntities) {
        this.timetableRepo = timetableRepo;
        this.stationRepo = stationRepo;
        this.xmlResponse = xmlResponse;
        this.apiDataToEntities = apiDataToEntities;
    }

    @Override
    public List<Timetable> getAllTimetables() {
        return timetableRepo.findAll();
    }

    @Override
    public Timetable getTimetableById(Long id) {
        return timetableRepo.findById(id).orElse(null);
    }

    @Override
    public Timetable saveTimetable(Timetable timetable) {
        return timetableRepo.save(timetable);
    }

    @Override
    public void deleteTimetable(Long id) {
        timetableRepo.deleteById(id);
    }
    @Transactional
    public List<Timetable> saveAllTimetables(String evaNumber) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Saving timetables for Eva number: " + evaNumber);

        if (evaNumber == null || evaNumber.isEmpty()) {
            throw new IllegalArgumentException("Eva number cannot be null or empty");
        }

        Station station = stationRepo.findByEvaNumber(evaNumber);
        if (station == null) {
            throw new RuntimeException("Station not found for the given Eva number");
        }

        List<Timetable> timetables = apiDataToEntities.apiDataToTimetable(Integer.parseInt(evaNumber), "", "", false);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Fetched " + timetables.size() + " timetables");

        for (Timetable timetable : timetables) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Timetable: " + timetable.toString());
        }

        List<Timetable> savedTimetables = timetableRepo.saveAll(timetables);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Saved " + savedTimetables.size() + " timetables to database");

        return savedTimetables;
    }

    public List<Timetable> retrieveTimetables(String evaNumber) {
        // Retrieve timetables for the given EVA number using the ApiDataToEntities service
        List<Timetable> timetables = apiDataToEntities.apiDataToTimetable(Integer.parseInt(evaNumber), "", "", false);

        // Perform business logic to filter or transform the retrieved timetables
        List<Timetable> filteredTimetables = filterTimetables(timetables);

        return filteredTimetables;
    }

    private List<Timetable> filterTimetables(List<Timetable> timetables) {
        // Filter timetables to include only those with delay
        return timetables.stream()
                .filter(timetable -> "Delayed".equals(timetable.getDelay()))
                .collect(Collectors.toList());
    }
}