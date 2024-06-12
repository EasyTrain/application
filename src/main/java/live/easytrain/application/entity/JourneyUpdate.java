package live.easytrain.application.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "journey_updates")
public class JourneyUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "schedule_id")
    private String scheduleId;

    @Column(name = "delay", length = 500)
    private String delay;

    // sTypeIce.getAr().getCt();
    @Column(name = "arrival_time")
    private String arrivalTime;

    // sTypeIce.getDp().getCt();
    @Column(name = "departure_time")
    private String departureTime;

    //sTypeIce.getDp().getCpth()
    @Column(name = "changed_path_from")
    private String changedPathFrom;

    //sTypeIce.getDp().getCpth()
    @Column(name = "changed_path_to")
    private String changedPathTo;

    @Column(name = "train_number")
    private String trainNumber;

    //sTypeIce.getDp().getCp();
    @Column(name = "platform_number")
    private String platformNumber;

    public JourneyUpdate(){
    }

    public JourneyUpdate(String scheduleId, String delay, String arrivalTime, String departureTime, String changedPathFrom, String changedPathTo, String trainNumber, String platformNumber) {
        this.scheduleId = scheduleId;
        this.delay = delay;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.changedPathFrom = changedPathFrom;
        this.changedPathTo = changedPathTo;
        this.trainNumber = trainNumber;
        this.platformNumber = platformNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
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

    public String getChangedPathFrom() {
        return changedPathFrom;
    }

    public void setChangedPathFrom(String changedPathFrom) {
        this.changedPathFrom = changedPathFrom;
    }

    public String getChangedPathTo() {
        return changedPathTo;
    }

    public void setChangedPathTo(String changedPathTo) {
        this.changedPathTo = changedPathTo;
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
}

