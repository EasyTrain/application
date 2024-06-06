package live.easytrain.application.controller;

import jakarta.websocket.server.PathParam;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.service.BookingServiceInterface;
import live.easytrain.application.service.StationServiceInterface;
import live.easytrain.application.service.TimetableServiceInterface;
import live.easytrain.application.utils.DateTimeParserUtils;
import live.easytrain.application.utils.TimetableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Controller
public class BookingController {

    private BookingServiceInterface bookingService;
    private StationServiceInterface stationService;
    private TimetableServiceInterface timetableService;
    private DateTimeParserUtils dateTimeParser;
    private TimetableUtils timetableUtils;

    private List<Timetable> timetablesDestinations;

    @Autowired
    public BookingController(BookingServiceInterface bookingService, StationServiceInterface stationService,
                             TimetableServiceInterface timetableService, DateTimeParserUtils dateTimeParser,
                             TimetableUtils timetableUtils) {
        this.bookingService = bookingService;
        this.stationService = stationService;
        this.timetableService = timetableService;
        this.dateTimeParser = dateTimeParser;
        this.timetableUtils = timetableUtils;
    }

    @GetMapping("/booking")
    public String booking(@RequestParam String stationName, @RequestParam(required = false) String goingTo,
                          @RequestParam(required = false) String time,
                          @RequestParam(required = false, defaultValue = "false") boolean recentChanges,
                          Model model) {

        List<Timetable> timetables = timetableService.fetchTimetableDataFromAPI(stationName, LocalDate.now(),
                dateTimeParser.parseStringToLocalTime(time));

        timetablesDestinations = timetableUtils.journeysToDestination(timetables, goingTo);

        if (!timetablesDestinations.isEmpty()) {
            model.addAttribute("timetables", timetablesDestinations);
            model.addAttribute("prices", timetableUtils.journeyPrice(timetablesDestinations));
            model.addAttribute("booking", new Timetable());
            return "booking/book";
        }

        return "booking/book";
    }

    @RequestMapping("/book")
    public String selectTrain(@ModelAttribute("booking") Timetable timetable, @Param(value = "trainNumber") String trainNumber, Model model) {

        System.out.println(timetable.getCurrentStation() + " : Current Station " + timetable.getTrainNumber() + trainNumber);


        return "booking/book";
    }


    /*@PostMapping("/processBooking")
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
    }*/
}
