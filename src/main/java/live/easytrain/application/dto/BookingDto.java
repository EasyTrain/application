package live.easytrain.application.dto;

import jakarta.validation.Valid;
import live.easytrain.application.entity.Booking;
import live.easytrain.application.entity.Payment;
import live.easytrain.application.entity.Ticket;

public class BookingDto {

    @Valid
    private Ticket ticket = new Ticket();

    private Booking booking = new Booking();

    @Valid
    private Payment payment = new Payment();

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
