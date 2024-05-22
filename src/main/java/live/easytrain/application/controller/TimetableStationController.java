package live.easytrain.application.controller;


import live.easytrain.application.entity.Timetable;
import live.easytrain.application.service.TimetableStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/timetables")
public class TimetableStationController {

    private final TimetableStationService timetableService;

    @Autowired
    public TimetableStationController(TimetableStationService timetableService) {
        this.timetableService = timetableService;
    }

    @GetMapping("/retrieve") // showing the retrieve form
    public String showRetrieveForm(Model model) {
        return "timetable";
    }

    @PostMapping("/retrieve")
    public String retrieveTimetables(@RequestParam("evaNumber") String evaNumber, Model model) {
        List<Timetable> retrievedTimetables = timetableService.retrieveTimetables(evaNumber);
        model.addAttribute("timetables", retrievedTimetables);
        return "timetable-list";
    }

    @PostMapping("/{evaNumber}")
    public String saveTimetables(@PathVariable String evaNumber, Model model) {
        try {
            List<Timetable> savedTimetables = timetableService.saveAllTimetables(evaNumber);
            model.addAttribute("timetables", savedTimetables);
            return "timetable-list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/list")
    public String getAllTimetables(Model model) {
        List<Timetable> timetables = timetableService.getAllTimetables();
        model.addAttribute("timetables", timetables);
        return "timetable-list";
    }
}
