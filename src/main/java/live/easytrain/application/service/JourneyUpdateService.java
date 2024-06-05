package live.easytrain.application.service;

import live.easytrain.application.entity.JourneyUpdate;
import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.api.binder.ApiDataToEntities;
import live.easytrain.application.repository.JourneyUpdateRepo;
import live.easytrain.application.repository.StationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JourneyUpdateService implements JourneyUpdateServiceInterface{
    private JourneyUpdateRepo journeyUpdateRepo;
    private ApiDataToEntities apiDataToEntities;
    private StationRepo stationRepo;
    private StationServiceInterface stationServiceInterface;
    @Autowired
    public JourneyUpdateService(JourneyUpdateRepo journeyUpdateRepo, ApiDataToEntities apiDataToEntities, StationRepo stationRepo, StationServiceInterface stationServiceInterface) {
        this.journeyUpdateRepo = journeyUpdateRepo;
        this.apiDataToEntities = apiDataToEntities;
        this.stationRepo = stationRepo;
        this.stationServiceInterface = stationServiceInterface;
    }
    @Override
    public Iterable<JourneyUpdate> getAllJourneyUpdates() {
        return journeyUpdateRepo.findAll();
    }
    @Override
    public Optional<JourneyUpdate> getJourneyUpdateByScheduledId(Long id) {
        return journeyUpdateRepo.findById(id);
    }
//    @Override
//    public List<JourneyUpdate> checkDelays(String scheduleId) {
//        List<JourneyUpdate> journeyUpdates = journeyUpdateRepo.findByScheduleId(scheduleId);
//        if (journeyUpdates.isEmpty()) {
//            throw new RuntimeException("No journey updates found for the schedule ID: " + scheduleId);
//        }
//
//        // Retrieve the Station by evaNumber
//        Station station = stationRepo.findByEvaNumber(scheduleId);
//        if (station == null) {
//            throw new RuntimeException("No station found for the evaNumber: " + scheduleId);
//        }
//
//        // Get the stationNumber (evaNumber)
//        Integer stationNumber = Integer.valueOf(station.getEvaNumber());
//
//         // Fetch timetables using the ApiDataToEntities service
//        List<Timetable> timetables = apiDataToEntities.apiDataToTimetable(stationNumber, null, null, true);
//
//        for (JourneyUpdate journeyUpdate : journeyUpdates) {
//            for (Timetable timetable : timetables) {
//                if (journeyUpdate.getScheduleId().equals(timetable.getScheduledId())) {
//                    updateDelayInfo(journeyUpdate, timetable);
//                }
//            }
//        }
//
//        journeyUpdateRepo.saveAll(journeyUpdates);
//        return journeyUpdates;
//    }
//
//    private void updateDelayInfo(JourneyUpdate journeyUpdate, Timetable updatedTimetable) {
//        journeyUpdate.setDelay(updatedTimetable.getDelay());
//        journeyUpdate.setArrivalTime(updatedTimetable.getArrivalTime());
//        journeyUpdate.setDepartureTime(updatedTimetable.getDepartureTime());
//        journeyUpdate.setChangedPathFrom(updatedTimetable.getStartingPoint());
//        journeyUpdate.setChangedPathTo(updatedTimetable.getDestination());
//        journeyUpdate.setTrainNumber(updatedTimetable.getTrainNumber());
//        journeyUpdate.setPlatformNumber(updatedTimetable.getPlatformNumber());
//    }
}