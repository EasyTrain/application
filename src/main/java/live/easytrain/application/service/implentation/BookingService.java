package live.easytrain.application.service.implentation;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import live.easytrain.application.dto.BookingDto;
import live.easytrain.application.entity.Booking;
import live.easytrain.application.exceptions.BookingNotFoundException;
import live.easytrain.application.repository.BookingRepo;
import live.easytrain.application.service.interfaces.BookingServiceInterface;
import live.easytrain.application.utils.EasyTrainMailSenderUtils;
import live.easytrain.application.utils.DateTimeParserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements BookingServiceInterface {

    private BookingRepo bookingRepo;
    private DateTimeParserUtils dateTimeParserUtils;
    private EasyTrainMailSenderUtils easyTrainMailSender;

    @Autowired
    public BookingService(BookingRepo bookingRepo, DateTimeParserUtils dateTimeParserUtils,
                          EasyTrainMailSenderUtils easyTrainMailSender) {
        this.bookingRepo = bookingRepo;
        this.dateTimeParserUtils = dateTimeParserUtils;
        this.easyTrainMailSender = easyTrainMailSender;
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
    public List<Booking> getAllBookingsById(Long id) {

        List<Booking> bookings = bookingRepo.findAllByUserId(id);

//        if (bookings.isEmpty()) {
//            throw new BookingNotFoundException("Booking not found!");
//        }
        return bookings;
    }

    @Override
    public void sendTicket(BookingDto bookingDto) {

        String toAddress = bookingDto.getTicket().getEmail();
        String fromAddress = "easyice2024@gmail.com";
        String senderName = "EasyTrain";
        String subject = "Ticket confirmation [[dateAndTicketId]]";
        String content = "Dear [[name]],<br>"
                + "Find your ticket information for the <strong>[[date]]</strong> journey bellow:<br><br>"
                + "<h1>[[trainNumber]]</h1>"
                + "<br><hr>"
                + "<strong>Departure station | Departure Time | Carriage class | Platform Number | Destination | Price</strong>"
                + "<br><hr>"
                + "<pre>[[depStation]]    | [[depTime]]    | [[carClass]]   | [[platNumber]]  | [[dest]]    | [[price]]</pre>"
                + "<br><br><strong>Direction:</strong> [[dir]]"
                + "<br><strong>Details:</strong> [[details]]"
                + "<br><hr>"
                + "<br>"
                + "Thank you,<br>"
                + "EasyTrain Team.";

        subject = subject.replace("[[dateAndTicketId]]",
                dateTimeParserUtils.formatLocalDateToString(bookingDto.getBooking().getDate()) + "/ET0" +
                        bookingDto.getTicket().getId());

        content = content.replace("[[name]]", bookingDto.getTicket().getFullName());
        content = content.replace("[[date]]", bookingDto.getBooking().getDate().toString());
        content = content.replace("[[trainNumber]]", bookingDto.getBooking().getTrainNumber());
        content = content.replace("[[depStation]]", bookingDto.getBooking().getFromLocation());
        content = content.replace("[[depTime]]", bookingDto.getBooking().getDepartureTime().toString());
        content = content.replace("[[carClass]]", String.valueOf(bookingDto.getTicket().getCarriageClass()));
        content = content.replace("[[platNumber]]", bookingDto.getBooking().getPlatformNumber());
        content = content.replace("[[dest]]", bookingDto.getBooking().getToLocation());
        content = content.replace("[[price]]", String.valueOf(bookingDto.getTicket().getFinalPrice()) + " Euros");
        content = content.replace("[[dir]]", bookingDto.getBooking().getTrainsDestination());
        content = content.replace("[[details]]", bookingDto.getBooking().getJourneyDetails());

        try {
            easyTrainMailSender.sendEmail(fromAddress, toAddress, subject, content, senderName);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Something went wrong, email not sent. Please make sure that this email is valid");
        }
    }

    @Override
    public Booking getBookingById(Long id) {

        Optional<Booking> booking = bookingRepo.findById(id);

        if (booking.isPresent()) {
            return booking.get();
        } else {
            throw new BookingNotFoundException("Booking not found!");
        }
    }
}
