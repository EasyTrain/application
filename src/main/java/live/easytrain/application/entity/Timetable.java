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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private Station station;

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


}