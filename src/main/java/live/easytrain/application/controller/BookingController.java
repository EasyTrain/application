package live.easytrain.application.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import live.easytrain.application.config.PaymentResponse;
import live.easytrain.application.config.PaymentsEncryptConfig;
import live.easytrain.application.dto.BookingDto;
import live.easytrain.application.entity.*;
import live.easytrain.application.service.interfaces.BookingServiceInterface;
import live.easytrain.application.service.TicketServiceInterface;
import live.easytrain.application.service.TimetableServiceInterface;
import live.easytrain.application.service.UserServiceInterface;
import live.easytrain.application.utils.BookingUtils;
import live.easytrain.application.utils.DateTimeParserUtils;
import live.easytrain.application.utils.TicketUtils;
import live.easytrain.application.utils.TimetableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Controller
public class BookingController {

    private BookingServiceInterface bookingService;
    private TimetableServiceInterface timetableService;
    private DateTimeParserUtils dateTimeParser;
    private TimetableUtils timetableUtils;
    private PaymentResponse paymentResponse;
    private PaymentsEncryptConfig paymentsEncrypt;
    private TicketServiceInterface ticketService;
    private TicketUtils ticketUtils;
    private BookingUtils bookingUtils;

    private UserServiceInterface userService;

    private List<Timetable> timetablesDestinations = new ArrayList<>();

    @Autowired
    public BookingController(BookingServiceInterface bookingService, TimetableServiceInterface timetableService,
                             DateTimeParserUtils dateTimeParser, TimetableUtils timetableUtils,
                             PaymentResponse paymentResponse, PaymentsEncryptConfig paymentsEncrypt,
                             TicketServiceInterface ticketService, TicketUtils ticketUtils, UserServiceInterface userService,
                             BookingUtils bookingUtils) {

        this.bookingService = bookingService;
        this.timetableService = timetableService;
        this.dateTimeParser = dateTimeParser;
        this.timetableUtils = timetableUtils;
        this.paymentResponse = paymentResponse;
        this.paymentsEncrypt = paymentsEncrypt;
        this.ticketService = ticketService;
        this.ticketUtils = ticketUtils;
        this.bookingUtils = bookingUtils;
        this.userService = userService;
    }

    @GetMapping("/booking")
    public String booking(@RequestParam String stationName,
                          @RequestParam(required = false) String goingTo, @RequestParam(required = false) String time,
                          @RequestParam(required = false, defaultValue = "false") boolean recentChanges,
                          Model model) {

        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();

        if (loggedUser.getName().equalsIgnoreCase("anonymousUser")) {
            return "redirect:/login";
        }

        try {

            if (dateTimeParser.parseStringToLocalTime(time).isBefore(LocalTime.now())
                    && !dateTimeParser.isNextDay(LocalTime.now())) {
                throw new RuntimeException("It is not possible to book this journey as the train has already left.");
            }

            List<Timetable> timetables = timetableService.fetchTimetableDataFromAPI(stationName,
                    dateTimeParser.dayChanging(LocalTime.now()), dateTimeParser.parseStringToLocalTime(time));
            timetablesDestinations = timetableUtils.journeysToDestination(timetables, goingTo);

        } catch (RuntimeException e) {
            model.addAttribute("dbError", e.getMessage());
        }

        if (!timetablesDestinations.isEmpty()) {
            model.addAttribute("timetables", timetablesDestinations);
            model.addAttribute("prices", timetableUtils.journeyPrice(timetablesDestinations));
            model.addAttribute("booking", new Timetable());
            return "booking/book";
        }

        return "booking/book";
    }

    @GetMapping("/book/{trainNumber}/{price}")
    public String selectTrain(@PathVariable String trainNumber, @PathVariable("price") double price, Model model) {

        Booking selectedTrain = timetableUtils.timetableToBooking(timetablesDestinations, trainNumber, price);

        if (selectedTrain == null) {
            return "booking/book";
        }

        BookingDto bookingDto = new BookingDto();
        bookingDto.setBooking(selectedTrain);
        bookingDto.setTicket(new Ticket());
        bookingDto.setPayment(new Payment());

        model.addAttribute("booking", bookingDto);

        return "booking/booking";
    }

    // This method allow to buy a ticket from timetable
    @GetMapping("/book/{id}")
    public String book(@PathVariable Long id, Model model) {

        Timetable timetable = timetableService.getJourneyFromTimetableById(id);
        String trainNumber = timetable.getTrainNumber();

        // The delay field is reused to store the train destination to avoid a dedicated field for the purpose
        timetable.setDelay(timetable.getDestination());
        timetablesDestinations.add(timetable);

        double price = timetableUtils.journeyPrice(timetablesDestinations).getFirst();

        Booking selectedTrain = timetableUtils.timetableToBooking(timetablesDestinations, trainNumber, price);

        BookingDto bookingDto = new BookingDto();
        bookingDto.setBooking(selectedTrain);
        bookingDto.setTicket(new Ticket());
        bookingDto.setPayment(new Payment());

        model.addAttribute("booking", bookingDto);

        return "booking/booking";
    }


    @PostMapping("/processBooking/{trainNumber}/{price}")
    public String processBooking(@PathVariable String trainNumber, @PathVariable("price") double price,
                                 @Valid @ModelAttribute("booking") BookingDto bookingDto, BindingResult bindingResult,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            return "booking/booking";
        } else {
            Booking selectedTrain = timetableUtils.timetableToBooking(timetablesDestinations, trainNumber, price);

            if (selectedTrain == null) {
                return "redirect:/";
            }

            Authentication loggedMember = SecurityContextHolder.getContext().getAuthentication();
            String email = loggedMember.getName();

            User userAuth = userService.getUserByEmail(email);

            selectedTrain.setUser(userAuth);
            bookingService.createBooking(selectedTrain);

            bookingDto.setBooking(selectedTrain);

            Ticket ticket = new Ticket(bookingDto.getTicket().getGender(), bookingDto.getTicket().getFullName(),
                    bookingDto.getTicket().getEmail(), bookingDto.getBooking().getToLocation(),
                    bookingDto.getTicket().getCarriageClass(), ticketUtils.finalPrice(selectedTrain.getJourneyPrice(),
                    bookingDto.getTicket().getCarriageClass(), bookingDto.getTicket().isUnderAge()), bookingDto.getTicket().isUnderAge(),
                    0.0, "");

            // Data encryption - to avoid sending sensitive information as it is
            String encryptedData = paymentsEncrypt.encrypt(
                    bookingDto.getPayment().getCardNumber() + bookingDto.getPayment().getExpiryDate() +
                            bookingDto.getPayment().getCvc(), "Eisenbahn");

            // Payment API request
            String paymentStatus = paymentResponse.fetchPaymentResponse(encryptedData, ticket.getFinalPrice());

            if (paymentStatus.equalsIgnoreCase("success")) {
                selectedTrain.setFinalized(true);
                ticket.setBooking(selectedTrain);
                Ticket savedTicket = ticketService.createTicket(ticket);

                bookingDto.setTicket(savedTicket);

                try {
                    bookingUtils.sendTicket(bookingDto);
                } catch (MessagingException | UnsupportedEncodingException e) {
                    throw new RuntimeException("Something went wrong, email not sent. Please make sure that this email is valid: "
                    + ticket.getEmail());
                }
            } else {

                model.addAttribute("booking", bookingDto);
                return "booking/booking";
            }
            return "booking/booking_success";
        }
    }

    @GetMapping("/payLater/{trainNumber}/{price}")
    public String payLater(@PathVariable String trainNumber, @PathVariable double price, Model model) {

        Booking selectedTrain = timetableUtils.timetableToBooking(timetablesDestinations, trainNumber, price);

        if (selectedTrain == null) {
            return "redirect:/";
        }

        if (selectedTrain.getDepartureTime().isBefore(LocalTime.now()) ||
                selectedTrain.getDepartureTime().equals(LocalTime.now())) {

            throw new RuntimeException("Is not possible get this journey because is already departed.");
        }

        bookingService.createBooking(selectedTrain);

        // improvement
        return "booking/booking";

    }

    @GetMapping("/journeys")
    public String journeys(Model model) {

        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loggedUser.getName();

        User userAuth = userService.getUserByEmail(email);

        List<Booking> journeys = bookingService.getBookingsById(userAuth.getId());


        model.addAttribute("journeys", journeys);

        return "profile/journeys";
    }

}
