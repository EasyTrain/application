package live.easytrain.application.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stationPrefix;
    private String stationName;
    private String evaNumber;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Timetable> timetables = new ArrayList<>();

    public List<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    //    Constructors
    public Station() {
    }

    //    Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationPrefix() {
        return stationPrefix;
    }

    public void setStationPrefix(String stationPrefix) {
        this.stationPrefix = stationPrefix;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getEvaNumber() {
        return evaNumber;
    }

    public void setEvaNumber(String evaNumber) {
        this.evaNumber = evaNumber;
    }
}