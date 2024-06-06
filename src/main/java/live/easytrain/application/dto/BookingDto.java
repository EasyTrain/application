package live.easytrain.application.dto;

import live.easytrain.application.entity.Booking;
import live.easytrain.application.entity.Timetable;

public class BookingDto {

    private Timetable timetable = new Timetable();
    private Booking booking = new Booking();

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
