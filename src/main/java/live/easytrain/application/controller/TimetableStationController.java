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

    @Autowired
    public TimetableStationController(TimetableServiceInterface timetableService, StationServiceInterface stationService,
                                      DateTimeParserUtils dateTimeParser) {
        this.timetableService = timetableService;
        this.stationService = stationService;
        this.dateTimeParser = dateTimeParser;
    }

    // Show timetable-form
    @GetMapping()
    public String showTimetableForm(Model model) {
        try {
            List<Station> station = stationService.findAllStationByStationCode("ICE");
            model.addAttribute("stations", station);
            model.addAttribute("timetable", new Timetable());
        } catch (Exception e) {
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
            // Save timetables
            List<Timetable> timetables = timetableService.saveTimetableData(stationName, LocalDate.now(),
                    dateTimeParser.parseStringToLocalTime(time), recentChanges);
            if (timetables.isEmpty()) {
                // Handle the scenario where no trains are found
                model.addAttribute("error", "No trains found for the specified station and time.");
                return "timetable-error";
            } else {
                model.addAttribute("timetables", timetables);
                return "timetable";
            }
        } catch (Exception e) {
            // Handle other exceptions if necessary
            e.printStackTrace();
            model.addAttribute("error", "Error saving timetables: " + e.getMessage());
            return "timetable-error"; // Or redirect to an error page
        }
    }
}