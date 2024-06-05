package live.easytrain.application.controller;

import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.service.StationServiceInterface;
import live.easytrain.application.service.TimetableServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/timetables")
public class TimetableStationController {
    private TimetableServiceInterface timetableService;
    private StationServiceInterface stationService;
    @Autowired
    public TimetableStationController(TimetableServiceInterface timetableService, StationServiceInterface stationService) {
        this.timetableService = timetableService;
        this.stationService = stationService;
    }

    @GetMapping
    public String getTimetables(Model model) {
        List<Timetable> timetables = timetableService.getAllTimetables();
        model.addAttribute("timetables", timetables);
        return "timetable";
    }

    // Show timetable-form
    @GetMapping("/add")
    public String showTimetableForm(Model model) {
        List<Station> stations = stationService.findAllEvaNumberByStationName("");
        model.addAttribute("stations", stations);
        model.addAttribute("timetable", new Timetable());
        return "timetable-lookup";
    }

    // Save Timetables
    @PostMapping("/save")
    public String saveTimetablesData(@RequestParam String stationName,
                                     @RequestParam(required = false) String time,
                                     @RequestParam(required = false, defaultValue = "false") boolean recentChanges,
                                     Model model) {
        try {
            LocalDate date = LocalDate.now();
            LocalTime hour = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            // Save timetable data to database
            timetableService.saveTimetableData(stationName, date, hour, recentChanges);
            // Fetch timetable data from the API based on the provided parameters
            List<Timetable> timetables = timetableService.fetchTimetableDataFromAPI(stationName, date, hour, date, hour);
            // Pass necessary attributes to the model
            model.addAttribute("timetables", timetables);
            model.addAttribute("success", "Timetables saved successfully");
            return "timetable";
        } catch (Exception e) {
            model.addAttribute("error", "Error saving timetables: " + e.getMessage());
            return "index";
        }
    }

    @GetMapping("/timetable-list")
    public String showTimetableList(Model model) {
        // Retrieve the timetables from the model
        List<Timetable> timetables = (List<Timetable>) model.getAttribute("timetables");
        // Retrieve the success message from the model
        String successMessage = (String) model.getAttribute("success");
        // Add the timetables and success message to the model again
        model.addAttribute("timetables", timetables);
        model.addAttribute("success", successMessage);
        return "timetable";
    }
}
