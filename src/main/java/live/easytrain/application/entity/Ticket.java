package live.easytrain.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "gender", length = 20)
    private String gender;

    @NotNull(message = "Please enter the name of the passenger.")
    @Size(min = 1, message = "Please enter the name of the passenger.")
    @Column(name = "full_name", length = 100)
    private String fullName;

    private String toLocation;

    @Column(name = "connection_stop")
    private String connectionStop;

//    @Min(value = 1, message = "must be greater than or equal to 1")
//    @Max(value = 2, message = "must be less than or equal to 2")
    private Integer carriageClass;
    private double finalPrice;

    private boolean underAge;

    @NotNull(message = "Please enter the email.")
    @Size(min = 1, message = "Please enter the email.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Please enter a valid email.")
    private String email;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    private double discount;


    //    Constructors
    public Ticket() {
    }

    public Ticket(String gender, String fullName, String email, String toLocation, Integer carriageClass, double finalPrice,
                  boolean underAge, double discount, String connectionStop) {
        this.gender = gender;
        this.fullName = fullName;
        this.email = email;
        this.toLocation = toLocation;
        this.carriageClass = carriageClass;
        this.finalPrice = finalPrice;
        this.underAge = underAge;
        this.discount = discount;
        this.connectionStop = connectionStop;
    }

    //    Setters and Getters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getConnectionStop() {
        return connectionStop;
    }

    public void setConnectionStop(String connectionStop) {
        this.connectionStop = connectionStop;
    }

    public Integer getCarriageClass() {
        return carriageClass;
    }

    public void setCarriageClass(Integer carriageClass) {
        this.carriageClass = carriageClass;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public boolean isUnderAge() {
        return underAge;
    }

    public void setUnderAge(boolean underAge) {
        this.underAge = underAge;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}