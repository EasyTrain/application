package live.easytrain.application.service.implentation;

import jakarta.transaction.Transactional;
import live.easytrain.application.entity.Booking;
import live.easytrain.application.exceptions.BookingNotFoundException;
import live.easytrain.application.api.binder.ApiDataToEntities;
import live.easytrain.application.repository.BookingRepo;
import live.easytrain.application.repository.ConnectionRepo;
import live.easytrain.application.repository.TicketRepo;
import live.easytrain.application.repository.TimetableRepo;
import live.easytrain.application.service.StationServiceInterface;
import live.easytrain.application.service.interfaces.BookingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return bookingRepo.save(modelBooking);
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

    @Override
    public List<Booking> getBookingsById(Long id) {

        List<Booking> bookings = bookingRepo.findAllById(id);

        if (bookings.isEmpty()) {
            throw new BookingNotFoundException("Booking not found!");
        }
        return bookings;
    }
}
