package live.easytrain.application.controller;

import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.service.StationServiceInterface;
import live.easytrain.application.service.TimetableServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "timetable-list";
    }

    // Show timetable-form
    @GetMapping("/add")
    public String showTimetableForm(Model model) {
        List<Station> stations = stationService.findAllEvaNumberByStationName("");
        model.addAttribute("stations", stations);
        model.addAttribute("timetable", new Timetable());
        return "index";
    }

    // Save Timetables
    @PostMapping("/save")
    public String saveTimetablesData(@RequestParam String stationName,
                                    // @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                     //  @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime hour,
                                     @RequestParam(required = false) String time,
                                     @RequestParam(required = false, defaultValue = "false") boolean recentChanges
                                     ,RedirectAttributes redirectAttributes) {
//        try {
            LocalDate date = LocalDate.now();
            System.out.println(time + " :Javascript time");

            LocalTime hour = LocalTime.parse("11:00", DateTimeFormatter.ofPattern("HH:mm"));

            // Save timetable data to database
            timetableService.saveTimetableData(stationName, date, hour, recentChanges);
            // Fetch timetable data from the API based on the provided parameters
            List<Timetable> timetables = timetableService.fetchTimetableDataFromAPI(stationName, date, hour, date, hour);
            // Redirect to the timetable list page and pass necessary attributes
            redirectAttributes.addFlashAttribute("timetables", timetables);
            redirectAttributes.addFlashAttribute("success", "Timetables saved successfully");
            return "redirect:/timetables/timetable-list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving timetables: " + e.getMessage());
            return "redirect:/timetables/add";
        }
    }

    @GetMapping("/timetable-list")
    public String showTimetableList(@ModelAttribute("timetables") List<Timetable> timetables,
                                    @ModelAttribute("success") String successMessage,
                                    Model model) {
        model.addAttribute("timetables", timetables);
        model.addAttribute("success", successMessage);
        return "timetable-list";
    }

//    @GetMapping("/delays")
//    public String checkDelays(@RequestParam String s scheduleId, Model model) {
//        try {
//            List<Timetable> timetables = timetableService.checkDelays(scheduleId);
//            model.addAttribute("timetables", timetables);
//            return "timetable-delays";
//        } catch (Exception e) {
//            model.addAttribute("error", "Error checking delays: " + e.getMessage());
//            return "timetable-error";
//        }
//    }
}
