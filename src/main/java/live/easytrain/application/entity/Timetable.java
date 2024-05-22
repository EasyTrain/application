package live.easytrain.application.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "timetable")
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String startingPoint;
    private String destination;
    private String delay;
    private String estimatedTripTime;
    private String arrivalTime;
    private String departureTime;

    /*
    * Unique id provided by API to identify train scheduled time
    * Is used to update the timetable requesting https://apis.deutschebahn.com/db-api-marketplace/apis/timetables/v1/rchg/{evaNo}
    * */
    private String scheduleId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private Station station;

    public Timetable(String startingPoint, String destination, String delay, String estimatedTripTime, String arrivalTime,
                     String departureTime, String scheduleId) {
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.delay = delay;
        this.estimatedTripTime = estimatedTripTime;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.scheduleId = scheduleId;
    }

    public Timetable() {

    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getEstimatedTripTime() {
        return estimatedTripTime;
    }

    public void setEstimatedTripTime(String estimatedTripTime) {
        this.estimatedTripTime = estimatedTripTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
}