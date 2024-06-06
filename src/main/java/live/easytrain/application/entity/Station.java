package live.easytrain.application.entity;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stations")
public class Station implements Comparable<Station> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stationPrefix;
    private String stationName;
    private String evaNumber;
    private String stationCode;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Timetable> timetables = new ArrayList<>();

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
    public List<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    @Override
    public int compareTo(@NotNull Station o) {
        return 0;
    }
}