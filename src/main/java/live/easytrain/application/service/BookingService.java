package live.easytrain.application.service;

import jakarta.transaction.Transactional;
import live.easytrain.application.entity.Booking;
import live.easytrain.application.entity.Connection;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.exceptions.BookingNotFoundException;
import live.easytrain.application.external.binder.ApiDataToEntities;
import live.easytrain.application.repository.BookingRepo;
import live.easytrain.application.repository.ConnectionRepo;
import live.easytrain.application.repository.TicketRepo;
import live.easytrain.application.repository.TimetableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements BookingServiceInterface {

    private BookingRepo bookingRepo;
    private ConnectionRepo connectionRepo;
    private TicketRepo ticketRepo;
    private ApiDataToEntities apiData;
    private StationServiceInterface stationService;

    //Only for tests
    private TimetableRepo timetableRepo;

    @Autowired
    public BookingService(BookingRepo bookingRepo, ConnectionRepo connectionRepo, TicketRepo ticketRepo,
                          ApiDataToEntities apiData, StationServiceInterface stationService,
                          TimetableRepo timetableRepo) {
        this.bookingRepo = bookingRepo;
        this.connectionRepo = connectionRepo;
        this.ticketRepo = ticketRepo;
        this.apiData = apiData;
        this.stationService = stationService;
        this.timetableRepo = timetableRepo;
    }

    // Logic to calculate the price of a booking
    @Override
    public double calculatePrice(Long bookingId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found!"));

        double totalTravelTime = booking.getDuration();
        double basePrice = calculateBasePrice(totalTravelTime);
        double discountedPrice = applyDiscount(basePrice, booking.getTicket().getDiscount());
        return discountedPrice;
    }


    private double calculateBasePrice(double travelTime) {

        // Base price is 1 Euro per minute of travel time
        double basePricePerMinute = 1.0;

        return travelTime * basePricePerMinute;
    }

    private double applyDiscount(double basePrice, double discount) {

        return basePrice * (1 - (discount / 100));
    }

    // Book with connections
    @Override
    public Booking getBookingWithConnections(Long bookingId) {

        return bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found!"));
    }

    // CRUD operations: Create Booking
    @Override
    @Transactional
    public Booking createBooking(Booking modelBooking) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] arrDate = modelBooking.getStartDate().format(formatter).split("-");
        String startDate = arrDate[0].substring(2) + arrDate[1] + arrDate[2];

        System.out.println(startDate + " Date");

        String startTime = modelBooking.getStartTime().toString().substring(0,2);

        System.out.println(startTime + " hour");

        List<Timetable> timetables = apiData.apiDataToTimetable(stationService.evaNumberByStationName(modelBooking.getFromLocation()),
                startDate, startTime, false);

        timetableRepo.saveAll(timetables);

        Booking booking = null;

        return booking;
    }

    // CRUD operations: Update Booking
    @Override
    @Transactional
    public Booking updateBooking(Booking booking) {
        return bookingRepo.save(booking);
    }

    // CRUD operations: Delete Booking
    @Override
    @Transactional
    public String deleteBooking(Long id) {

        Optional<Booking> booking = bookingRepo.findById(id);

        String message = "";

        if (booking.isPresent()) {
            bookingRepo.delete(booking.get());
            message = "Booking with id " + id + " deleted successfully!";
        } else {
            throw new BookingNotFoundException("Booking not found!");
        }

        return message;
    }
}
