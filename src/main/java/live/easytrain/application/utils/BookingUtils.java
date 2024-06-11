package live.easytrain.application.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import live.easytrain.application.dto.BookingDto;
import live.easytrain.application.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

@Configuration
public class BookingUtils {

    private JavaMailSender mailSender;
    private DateTimeParserUtils dateTimeParser;

    @Autowired
    public BookingUtils(JavaMailSender mailSender, DateTimeParserUtils dateTimeParser) {
        this.mailSender = mailSender;
        this.dateTimeParser = dateTimeParser;
    }

    public void sendTicket(BookingDto bookingDto) throws MessagingException, UnsupportedEncodingException {

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

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        subject = subject.replace("[[dateAndTicketId]]",
                dateTimeParser.formatLocalDateToString(bookingDto.getBooking().getDate()) + "/ET0" +
                bookingDto.getTicket().getId());

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

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

        helper.setText(content, true);

        mailSender.send(message);
    }

}
