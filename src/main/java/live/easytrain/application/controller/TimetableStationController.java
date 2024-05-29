package live.easytrain.application.controller;

import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.service.StationService;
import live.easytrain.application.service.StationServiceInterface;
import live.easytrain.application.service.TimetableStationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/timetables")
public class TimetableStationController {

    private TimetableStationServiceInterface timetableStationService;

    private StationServiceInterface stationService;

    @Autowired
    public TimetableStationController(TimetableStationServiceInterface timetableStationService, StationServiceInterface stationService) {
        this.timetableStationService = timetableStationService;
        this.stationService = stationService;
    }
    @PostMapping("/save")
    public @ResponseBody String saveTimetablesData(@RequestParam String stationName,
                                                   @RequestParam(required = false) String date,
                                                   @RequestParam(required = false) String hour,
                                                   @RequestParam(required = false, defaultValue = "false") boolean recentChanges) {
        try {
            System.out.println("Hello");
            timetableStationService.saveTimetableData(stationName, date, hour, recentChanges);
            return "Timetables saved successfully.";
        } catch (Exception e) {
            return "Error saving timetables: " + e.getMessage();
        }
    }
    @GetMapping("/add")
    public String showTimetableForm(Model model) {
        model.addAttribute("timetable", new Timetable());
        return "timetable-form";
    }

    @PostMapping("/search")
    public String searchStations(@RequestParam String stationName, Model model) {
        List<Station> stations = stationService.findAllEvaNumberByStationName(stationName);
        model.addAttribute("stations", stations);
        return "station-list";
    }

}
