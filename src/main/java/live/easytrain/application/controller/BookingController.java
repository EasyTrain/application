package live.easytrain.application.controller;

import jakarta.validation.Valid;
import live.easytrain.application.entity.Booking;
import live.easytrain.application.entity.Station;
import live.easytrain.application.service.BookingServiceInterface;
import live.easytrain.application.service.StationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookingController {

    private BookingServiceInterface bookingService;
    private StationServiceInterface stationService;

    @Autowired
    public BookingController(BookingServiceInterface bookingService, StationServiceInterface stationService) {
        this.bookingService = bookingService;
        this.stationService = stationService;
    }

    @GetMapping("/booking")
    public String booking(Model model) {
        model.addAttribute("booking", new Booking());
        return "booking/booking";
    }

    @GetMapping("/getStationOptions")
    public String getStations(@Valid @ModelAttribute("booking") Booking booking,
                              BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "booking/booking";
        } else {

           String id = booking.getFromLocation();
            List<Station> stations = stationService.findAllEvaNumberByStationName(id);
            model.addAttribute("stations", stations);
            model.addAttribute("booking", booking);

            return "booking/booking_options";
        }
    }

    @PostMapping("/processBooking")
    public String processBooking(@Valid @ModelAttribute("booking") Booking booking,
                                 BindingResult bindingResult) {

//        if (bindingResult.hasErrors()) {
//            return "booking/booking_options";
//        } else {

//        booking.setFromLocation(id);
        bookingService.createBooking(booking);

        return "booking/booking_success";
//        }
    }
}
