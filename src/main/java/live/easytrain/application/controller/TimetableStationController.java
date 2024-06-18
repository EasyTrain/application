package live.easytrain.application.controller;

import live.easytrain.application.entity.JourneyUpdate;
import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.service.interfaces.JourneyUpdateServiceInterface;
import live.easytrain.application.service.interfaces.StationServiceInterface;
import live.easytrain.application.service.interfaces.TimetableServiceInterface;
import live.easytrain.application.utils.DateTimeParserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    // Show timetable-form
    @GetMapping()
    public String showTimetableForm(Model model) {
        try {
            List<Station> station = stationService.findAllStationByStationCode("ICE");
            model.addAttribute("stations", station);
            model.addAttribute("timetable", new Timetable());
        } catch (Exception e) {
            System.out.println("An error occurred while showing timetable form: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error showing timetable form: " + e.getMessage());
        }
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
            model.addAttribute("timetables", timetables);
            try {
                // Save the recent changes and compare with timetables.
                List<JourneyUpdate> updates = journeyUpdateService.saveJourneyUpdates(stationName, true);
                model.addAttribute("success", "Timetables saved successfully");
            } catch (RuntimeException e) {
                System.out.println("No journey updates found: " + e.getMessage());
                model.addAttribute("success", "Timetables saved successfully, but no journey updates found");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while saving timetables: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error saving timetables: " + e.getMessage());
            return "timetable-lookup";
        }
        return "timetable";
    }

    @GetMapping("/timetable-list")
    public String showTimetableList(@RequestParam String stationName, Model model) {
        try {
            System.out.println("Showing timetable list for station: " + stationName);
            // Retrieve the timetables from the model
            List<Timetable> timetables = timetableService.getAllTimetables();
            // Retrieve the journey updates from the service
            List<JourneyUpdate> updates = journeyUpdateService.findAll(stationName);

            for(Timetable tb : timetables){
                for(JourneyUpdate ja : updates){
                    if(tb.getScheduleId().equalsIgnoreCase(ja.getScheduleId())){
                        tb.setDelay(ja.getDelay());
                        tb.setArrivalTime(ja.getArrivalTime());
                        tb.setArrivalTime(ja.getDepartureTime());
                        tb.setPlatformNumber(ja.getPlatformNumber());
                        tb.setNextStations(ja.getChangedPathTo());
                    }
                }
            }
            model.addAttribute("timetables", timetables);
            // Add a success message
            model.addAttribute("successMessage", "Timetables and Journey Updates loaded successfully.");
        } catch (Exception e) {
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
}