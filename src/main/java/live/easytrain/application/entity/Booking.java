package live.easytrain.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    //@Column(name = "\"from\"")
    @NotNull(message = "Please enter the departure station name.")
    @Size(min = 1, message = "Please enter the departure station name.")
    @Column(name = "from_location")
    private String fromLocation;

    @Column(name = "to_location")
    private String toLocation;

    private LocalDate date;

    private LocalTime arrivalTime;

    private LocalTime departureTime;

    private String trainsDestination;

    @Column(name = "journey_details", length = 1000)
    private String journeyDetails;

    private double journeyPrice;

    private double duration;

    private String trainNumber;

    private String platformNumber;

    private boolean finalized;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Connection> connections;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //    Constructors
    public Booking() {
    }

    public Booking(String fromLocation, String toLocation, LocalDate date, LocalTime arrivalTime, LocalTime departureTime,
                   String trainsDestination, String journeyDetails, double journeyPrice, double duration, String trainNumber,
                   String platformNumber /*, User user*/) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.trainsDestination = trainsDestination;
        this.journeyDetails = journeyDetails;
        this.journeyPrice = journeyPrice;
        this.duration = duration;
        this.trainNumber = trainNumber;
        this.platformNumber = platformNumber;
//        this.user = user;
    }

//    Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Please enter the departure station name.") @Size(min = 1, message = "Please enter the departure station name.") String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(@NotNull(message = "Please enter the departure station name.") @Size(min = 1, message = "Please enter the departure station name.") String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getTrainsDestination() {
        return trainsDestination;
    }

    public void setTrainsDestination(String trainsDestination) {
        this.trainsDestination = trainsDestination;
    }

    public String getJourneyDetails() {
        return journeyDetails;
    }

    public void setJourneyDetails(String journeyDetails) {
        this.journeyDetails = journeyDetails;
    }

    public double getJourneyPrice() {
        return journeyPrice;
    }

    public void setJourneyPrice(double journeyPrice) {
        this.journeyPrice = journeyPrice;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFinalized() {
        return finalized;
    }

    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
    }
}