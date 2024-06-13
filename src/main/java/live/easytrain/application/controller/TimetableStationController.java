package live.easytrain.application.controller;

import live.easytrain.application.entity.JourneyUpdate;
import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.service.JourneyUpdateServiceInterface;
import live.easytrain.application.service.StationServiceInterface;
import live.easytrain.application.service.TimetableServiceInterface;
import live.easytrain.application.utils.DateTimeParserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/timetables")
public class TimetableStationController {
    private TimetableServiceInterface timetableService;
    private StationServiceInterface stationService;
    private DateTimeParserUtils dateTimeParser;
    private JourneyUpdateServiceInterface journeyUpdateService;
    @Autowired
    public TimetableStationController(JourneyUpdateServiceInterface journeyUpdateService, TimetableServiceInterface timetableService, StationServiceInterface stationService,
                                      DateTimeParserUtils dateTimeParser) {
        this.timetableService = timetableService;
        this.stationService = stationService;
        this.dateTimeParser = dateTimeParser;
        this.journeyUpdateService = journeyUpdateService;
    }
    @GetMapping("/timetable")
    public String getTimetables(Model model) {
        System.out.println("Retrieving all timetables and journey updates");
        List<Timetable> timetables = timetableService.getAllTimetables();
        List<JourneyUpdate> updates = journeyUpdateService.findAll();
        List<JourneyUpdate> combinedUpdates = combineTimetableAndUpdates(timetables, updates);
        // Extract current station from the first timetable (if exists)
        String currentStation = timetables.isEmpty() ? "" : timetables.get(0).getCurrentStation();
        model.addAttribute("currentStation", currentStation);
        model.addAttribute("entries", combinedUpdates);
        model.addAttribute("timetables", timetables);
        model.addAttribute("updates", updates);
        return "timetable";
    }
    // Show timetable-form
    @GetMapping()
    public String showTimetableForm(Model model) {
        List<Station> station = stationService.findAllStationByStationCode("ICE");
        model.addAttribute("stations", station);
        model.addAttribute("timetable", new Timetable());
        return "timetable-lookup";
    }
    // Save Timetables
    @PostMapping("/display-timetable")
    public String saveTimetablesData(@RequestParam String stationName,
                                     @RequestParam(required = false) String time,
                                     @RequestParam(required = false, defaultValue = "false") boolean recentChanges,
                                     Model model) {
        try {
            System.out.println("Saving timetables data for station: " + stationName + ", time: " + time + ", recentChanges: " + recentChanges);
            // Save timetable data to database
            List<Timetable> timetables = timetableService.saveTimetableData(stationName, LocalDate.now(),
                    dateTimeParser.parseStringToLocalTime(time), recentChanges);
            // Save the recent changes and compare with timetables.
            List<JourneyUpdate> updates = journeyUpdateService.saveJourneyUpdates(stationName, recentChanges);
            // Combine the timetables and journey updates
            List<JourneyUpdate> combinedUpdates = combineTimetableAndUpdates(timetables, updates);
            // Print saved timetables to console
            System.out.println("Saved Timetables:");
            for (Timetable timetable : timetables) {
                System.out.println(timetable);
            }
            // Pass necessary attributes to the model
            model.addAttribute("entries", combinedUpdates);
            model.addAttribute("updates", updates);
            model.addAttribute("timetables", timetables);
            model.addAttribute("success", "Timetables saved successfully");
            return "timetable";
        } catch (Exception e) {
            model.addAttribute("error", "Error saving timetables: " + e.getMessage());
            return "timetable-lookup";
        }
    }
    @GetMapping("/timetable-list")
    public String showTimetableList(Model model, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Showing timetable list");
            // Retrieve the timetables from the model
            List<Timetable> timetables = timetableService.getAllTimetables();
            // Retrieve the journey updates from the service
            List<JourneyUpdate> updates = journeyUpdateService.findAll();
            // Combine the timetables and journey updates as necessary
            List<JourneyUpdate> combinedUpdates = new ArrayList<>(updates);
            // Add attributes to the model
            model.addAttribute("entries", combinedUpdates);
            model.addAttribute("updates", updates);
            model.addAttribute("timetables", timetables);
            // Add a success message
            model.addAttribute("successMessage", "Timetables and Journey Updates loaded successfully.");
        } catch (Exception e) {
            // Add an error message in case of an exception
            System.out.println("An error occurred while loading Timetables and Journey Updates: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while loading Timetables and Journey Updates: " + e.getMessage());
        }
        return "timetable";
    }
    // Retrieve journeyUpdates by scheduleId
    @GetMapping("/delays/{scheduleId}")
    public String getDelaysByScheduleId(@PathVariable String scheduleId, Model model) {
        // Retrieve journey update by schedule ID
        try {
            System.out.println("Retrieving delays for scheduleId: " + scheduleId);
            List<JourneyUpdate> journeyUpdates = journeyUpdateService.getJourneyUpdatesByScheduleId(scheduleId);
            model.addAttribute("journeyUpdates", journeyUpdates);
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving delays: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while retrieving delays: " + e.getMessage());
        }
        return "timetable";
    }
    private List<JourneyUpdate> combineTimetableAndUpdates(List<Timetable> timetables, List<JourneyUpdate> updates) {
        Map<String, JourneyUpdate> updateMap = updates.stream()
                .collect(Collectors.toMap(JourneyUpdate::getScheduleId, update -> update));

        List<JourneyUpdate> combinedList = new ArrayList<>();
        for (Timetable timetable : timetables) {
            JourneyUpdate journeyUpdate = updateMap.get(timetable.getScheduleId());
            if (journeyUpdate != null) {
                // Update journeyUpdate with timetable data
                journeyUpdate.setArrivalTime(timetable.getPlannedArrivalTime());
                journeyUpdate.setDepartureTime(timetable.getPlannedDepartureTime());
                journeyUpdate.setChangedPathFrom(timetable.getStartingPoint());
                journeyUpdate.setChangedPathTo(timetable.getDestination());
                combinedList.add(journeyUpdate);
            } else {
                combinedList.add(timetableToJourneyUpdate(timetable));
            }
        }
        return combinedList;
    }
    private JourneyUpdate timetableToJourneyUpdate(Timetable timetable) {
        JourneyUpdate journeyUpdate = new JourneyUpdate();
        journeyUpdate.setScheduleId(timetable.getScheduleId());
        journeyUpdate.setTrainNumber(timetable.getTrainNumber());
        journeyUpdate.setArrivalTime(timetable.getPlannedArrivalTime());
        journeyUpdate.setDepartureTime(timetable.getPlannedDepartureTime());
        journeyUpdate.setChangedPathFrom(timetable.getStartingPoint());
        journeyUpdate.setChangedPathTo(timetable.getDestination());
        journeyUpdate.setPlatformNumber(timetable.getPlatformNumber());
        journeyUpdate.setDelay(timetable.getDelay());
        journeyUpdate.setArrivalTime(timetable.getArrivalTime());
        journeyUpdate.setDepartureTime(timetable.getDepartureTime());
        return journeyUpdate;
    }
}