package live.easytrain.application.controller;

import jakarta.validation.Valid;
import live.easytrain.application.entity.Booking;
import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.service.BookingServiceInterface;
import live.easytrain.application.service.StationServiceInterface;
import live.easytrain.application.service.TimetableServiceInterface;
import live.easytrain.application.utils.DateTimeParserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BookingController {

    private BookingServiceInterface bookingService;
    private StationServiceInterface stationService;
    private TimetableServiceInterface timetableService;
    private DateTimeParserUtils dateTimeParser;

    @Autowired
    public BookingController(BookingServiceInterface bookingService, StationServiceInterface stationService,
                             TimetableServiceInterface timetableService, DateTimeParserUtils dateTimeParser) {
        this.bookingService = bookingService;
        this.stationService = stationService;
        this.timetableService = timetableService;
        this.dateTimeParser = dateTimeParser;
    }

    @GetMapping("/booking")
    public String booking(@RequestParam String stationName, @RequestParam(required = false) String time,
                          @RequestParam(required = false, defaultValue = "false") boolean recentChanges,
                          Model model) {

        Map<Integer, List<Timetable>> journeys = new HashMap<>();

        LocalTime hour = dateTimeParser.parseStringToLocalTime(time);

        List<Timetable> timetables = timetableService.fetchTimetableDataFromAPI(stationName, LocalDate.now(), hour, LocalDate.now(), hour);

        model.addAttribute("timetables", timetables);
        return "timetable";
    }

    @GetMapping("/getStationOptions")
    public String getStations(@Valid @ModelAttribute("booking") Booking booking,
                              BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "booking/booking";
        } else {

            // create a method for this logic
            List<Station> stationObjects = stationService.findAllEvaNumberByStationName(booking.getFromLocation());
            List<String> stationNames = new ArrayList<>();
            if (stationObjects != null) {
                for (Station station : stationObjects) {
                    stationNames.add(station.getStationName());
                }
            } else {
                throw new RuntimeException("Station not found!");
            }

            model.addAttribute("stationNames", stationNames);

            return "booking/booking_options";
        }
    }

    @PostMapping("/processBooking")
    public String processBooking(@ModelAttribute("booking") Booking booking, Model model,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "booking/booking_options";
        } else {

            model.addAttribute("data", booking);
            System.out.println(booking.getFromLocation() + " From");
            bookingService.createBooking(booking);

            return "booking/booking_success";
        }
    }
}
