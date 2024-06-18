package live.easytrain.application.controller;

import live.easytrain.application.entity.Station;
import live.easytrain.application.service.interfaces.StationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class EasyTrainController {

    private StationServiceInterface stationService;
    private static final String STATION_CODE = "ICE";

    @Autowired
    public EasyTrainController(StationServiceInterface stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Station> stations = stationService.findAllStationByStationCode(STATION_CODE);

        model.addAttribute("stations", stations);
        return "index";
    }
}
