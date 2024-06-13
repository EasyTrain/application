package live.easytrain.application.service;


import jakarta.transaction.Transactional;
import live.easytrain.application.api.binder.ApiDataToEntities;
import live.easytrain.application.entity.JourneyUpdate;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.repository.JourneyUpdateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JourneyUpdateService implements JourneyUpdateServiceInterface {
    private ApiDataToEntities apiDataToEntities;
    private JourneyUpdateRepo journeyUpdateRepo;
    private StationServiceInterface stationService;
    @Autowired
    public JourneyUpdateService(ApiDataToEntities apiDataToEntities, JourneyUpdateRepo journeyUpdateRepo, StationServiceInterface stationService) {
        this.apiDataToEntities = apiDataToEntities;
        this.journeyUpdateRepo = journeyUpdateRepo;
        this.stationService = stationService;
    }
    // Retrieve journey updates by scheduleId
    @Override
    public List<JourneyUpdate> getJourneyUpdatesByScheduleId(String scheduleId) {
        // Call journeyUpdates for scheduleId
        List<JourneyUpdate> allJourneyUpdates = journeyUpdateRepo.findByScheduleId(scheduleId);
        // Filter journeyUpdates by changes in delay
        List<JourneyUpdate> filteredJourneyUpdates = new ArrayList<>();
        JourneyUpdate previousUpdate = null;
        for (JourneyUpdate update : allJourneyUpdates) {
            if (previousUpdate == null || !update.getDelay().equals(previousUpdate.getDelay())) {
                filteredJourneyUpdates.add(update);
            }
            previousUpdate = update;
        }
        // Save and return filtered journeyUpdates in database
        journeyUpdateRepo.saveAll(filteredJourneyUpdates);
        return filteredJourneyUpdates;
    }
    // Save JourneyUpdates
    @Override
    @Transactional
    public List<JourneyUpdate> saveJourneyUpdates(String stationName, boolean recentChanges) {
        // Get the evaNumber for the station
        Integer evaNumber = stationService.evaNumberByStationName(stationName);
        // Use evaNumber to fetch data
        List<Timetable> timetables = apiDataToEntities.apiDataToTimetable(evaNumber, null, null, recentChanges);
        List<JourneyUpdate> journeyUpdates = new ArrayList<>();
        // Convert Timetable objects to JourneyUpdate objects and save
            for (Timetable timetable : timetables) {
            // Check if the timetable entry represents a recent change
            if (isRecentChange(timetable, recentChanges)) {
                JourneyUpdate update = timetableToJourneyUpdate(timetable);
                // journeyUpdateRepo.save(update);
                journeyUpdates.add(update);
            }
            }

       return journeyUpdateRepo.saveAll(journeyUpdates);
    }


// Method to check if a timetable entry represents a recent change
private boolean isRecentChange(Timetable timetable, boolean recentChanges) {
    // Check if recentChanges flag is true and if the timetable delay has changed
    return recentChanges && timetable.getDelay() != null;
    }

    // Method to convert a Timetable object to a JourneyUpdate object
    private JourneyUpdate timetableToJourneyUpdate(Timetable timetable) {
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

    private String truncateString(String value, int maxLength) {
        if (value != null && value.length() > maxLength) {
            return value.substring(0, maxLength);
        }
        return value;
    }
    @Override
    public List<JourneyUpdate> findAll() {
        // Fetch all timetable data
        List<Timetable> allTimetables = apiDataToEntities.apiDataToTimetable(null, null, null, true);
        // Filter out recent changes
        List<Timetable> recentChanges = allTimetables.stream()
                .filter(timetable -> isRecentChange(timetable, true)) // Pass the recentChanges flag
                .collect(Collectors.toList());
        // Convert recent changes to JourneyUpdate objects
        List<JourneyUpdate> recentJourneyUpdates = recentChanges.stream()
                .map(this::timetableToJourneyUpdate)
                .collect(Collectors.toList());
        return recentJourneyUpdates;
    }
}