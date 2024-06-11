package live.easytrain.application.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import live.easytrain.application.config.PaymentResponse;
import live.easytrain.application.config.PaymentsEncryptConfig;
import live.easytrain.application.dto.BookingDto;
import live.easytrain.application.entity.*;
import live.easytrain.application.repository.UserRepository;
import live.easytrain.application.service.BookingServiceInterface;
import live.easytrain.application.service.TicketServiceInterface;
import live.easytrain.application.service.TimetableServiceInterface;
import live.easytrain.application.utils.BookingUtils;
import live.easytrain.application.utils.DateTimeParserUtils;
import live.easytrain.application.utils.TicketUtils;
import live.easytrain.application.utils.TimetableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    private UserRepository userRepository;

    private List<Timetable> timetablesDestinations = new ArrayList<>();

    @Autowired
    public BookingController(BookingServiceInterface bookingService, TimetableServiceInterface timetableService,
                             DateTimeParserUtils dateTimeParser, TimetableUtils timetableUtils,
                             PaymentResponse paymentResponse, PaymentsEncryptConfig paymentsEncrypt,
                             TicketServiceInterface ticketService, TicketUtils ticketUtils, UserRepository userRepository,
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
        this.userRepository = userRepository;
    }

    @GetMapping("/booking")
    public String booking(@RequestParam String stationName,
                          @RequestParam(required = false) String goingTo, @RequestParam(required = false) String time,
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


    @PostMapping("/processBooking/{trainNumber}/{price}")
    public String processBooking(@Valid @PathVariable String trainNumber, @PathVariable("price") double price,
                                 @ModelAttribute("booking") BookingDto bookingDto, BindingResult bindingResult,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            return "booking/booking";
        } else {
            Booking selectedTrain = timetableUtils.timetableToBooking(timetablesDestinations, trainNumber, price);

            if (selectedTrain == null) {
                return "redirect:/";
            }


            // To change with user authentication logic
            User user = new User();
            Optional<User> optionalUser = userRepository.findById(1L);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            } else {
                throw new RuntimeException("User not found");
            }

            // User authentication logic
           /* Authentication loggedMember = SecurityContextHolder.getContext().getAuthentication();
            String email = loggedMember.getName();

            User userAuth = userRepository.findByEmail(email);*/

            selectedTrain.setUser(user);
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
                    throw new RuntimeException(e);
                }
            } else {

                model.addAttribute("booking", bookingDto);
                return "booking/booking";
            }
            return "booking/booking_success";
        }
    }
}
