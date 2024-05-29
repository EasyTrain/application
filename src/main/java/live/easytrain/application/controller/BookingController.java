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

import java.util.ArrayList;
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

            // create a method for this logic
            List<Station> stationObjects = stationService.findAllEvaNumberByStationName(booking.getFromLocation());
            List<String> stationNames = new ArrayList<>();
            if (stationObjects != null) {
                for (Station station : stationObjects) {
                    stationNames.add(station.getStationName());
                }
            }else{
                throw new RuntimeException("Station not found!");
            }

            model.addAttribute("stationNames", stationNames);

            return "booking/booking_options";
        }
    }

    @PostMapping("/processBooking")
    public String processBooking( @ModelAttribute("booking") Booking booking, Model model,
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
