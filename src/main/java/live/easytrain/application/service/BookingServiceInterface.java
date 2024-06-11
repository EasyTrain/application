package live.easytrain.application.service;

import live.easytrain.application.entity.Booking;

public interface BookingServiceInterface {

    Booking getBookingWithConnections(Long bookingId);

    Booking createBooking(Booking booking);

    String deleteBooking(Long id);

}
