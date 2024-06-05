package live.easytrain.application.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "timetable")
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "starting_point")
    private String startingPoint;
    @Column(name = "destination")
    private String destination;
    @Column(name = "delay")
    private String delay;
    @Column(name = "estimated_trip_time")
    private String estimatedTripTime;
    @Column(name = "arrival_time")
    private String arrivalTime;
    @Column(name = "departure_time")
    private String departureTime;
    @Column(name = "train_number")
    private String trainNumber;
    @Column(name = "platform_number")
    private String platformNumber;
    @Column(name = "planned_arrival_time")
    String plannedArrivalTime ;
    @Column(name = "planned_departure_time")
    String plannedDepartureTime;

    @Column(name = "previous_stations", length = 500)
    private String previousStations;

    @Column(name = "next_stations", length = 500)
    private String nextStations;

    @Column(name = "current_station", length = 60)
    private String currentStation;
    /*
    * Unique id provided by API to identify train scheduled time
    * Is used to update the timetable requesting https://apis.deutschebahn.com/db-api-marketplace/apis/timetables/v1/rchg/{evaNo}
    * */
    @Column(name = "schedule_id")
    private String scheduleId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private Station station;

    public Timetable(String startingPoint, String destination, String delay, String estimatedTripTime, String arrivalTime,
                     String departureTime, String trainNumber, String platformNumber, String plannedArrivalTime,
                     String plannedDepartureTime, String previousStations, String nextStations, String scheduleId,
                     String currentStation) {
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.delay = delay;
        this.estimatedTripTime = estimatedTripTime;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.trainNumber = trainNumber;
        this.platformNumber = platformNumber;
        this.plannedArrivalTime = plannedArrivalTime;
        this.plannedDepartureTime = plannedDepartureTime;
        this.previousStations = previousStations;
        this.nextStations = nextStations;
        this.scheduleId = scheduleId;
        this.currentStation = currentStation;
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

    // equals() and hashCode() for proper functionality in collections

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timetable timetable = (Timetable) o;
        return Objects.equals(id, timetable.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public String getPlannedArrivalTime() {
        return plannedArrivalTime;
    }

    public void setPlannedArrivalTime(String plannedArrivalTime) {
        this.plannedArrivalTime = plannedArrivalTime;
    }

    public String getPlannedDepartureTime() {
        return plannedDepartureTime;
    }

    public void setPlannedDepartureTime(String plannedDepartureTime) {
        this.plannedDepartureTime = plannedDepartureTime;
    }

    public String getPreviousStations() {
        return previousStations;
    }

    public void setPreviousStations(String previousStations) {
        this.previousStations = previousStations;
    }

    public String getNextStations() {
        return nextStations;
    }

    public void setNextStations(String nextStations) {
        this.nextStations = nextStations;
    }

    public String getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(String currentStation) {
        this.currentStation = currentStation;
    }
}