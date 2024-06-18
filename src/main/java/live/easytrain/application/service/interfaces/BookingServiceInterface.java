package live.easytrain.application.service.interfaces;

import live.easytrain.application.dto.BookingDto;
import live.easytrain.application.entity.Booking;

import java.util.List;

public interface BookingServiceInterface {

    Booking getBookingWithConnections(Long bookingId);

    Booking createBooking(Booking booking);

    String deleteBooking(Long id);

    List<Booking> getBookingsById(Long id);

    void sendTicket(BookingDto bookingDto);
}
