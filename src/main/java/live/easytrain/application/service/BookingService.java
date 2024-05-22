package live.easytrain.application.service;

import jakarta.transaction.Transactional;
import live.easytrain.application.entity.Booking;
import live.easytrain.application.entity.Connection;
import live.easytrain.application.exceptions.BookingNotFoundException;
import live.easytrain.application.repository.BookingRepo;
import live.easytrain.application.repository.ConnectionRepo;
import live.easytrain.application.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements BookingServiceInterface {

    private BookingRepo bookingRepo;
    private ConnectionRepo connectionRepo;
    private TicketRepo ticketRepo;

    @Autowired
    public BookingService(BookingRepo bookingRepo, ConnectionRepo connectionRepo, TicketRepo ticketRepo) {
        this.bookingRepo = bookingRepo;
        this.connectionRepo = connectionRepo;
        this.ticketRepo = ticketRepo;
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
    public Booking createBooking(Booking booking) {
        return bookingRepo.save(booking);
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
