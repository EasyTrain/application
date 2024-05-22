package live.easytrain.application.service;

import live.easytrain.application.entity.Booking;

public interface BookingServiceInterface {

    double calculatePrice(Long bookingId);

    Booking getBookingWithConnections(Long bookingId);

    Booking createBooking(Booking booking);

    Booking updateBooking(Booking booking);

    String deleteBooking(Long id);

}
