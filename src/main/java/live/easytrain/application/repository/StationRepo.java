package live.easytrain.application.repository;

import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StationRepo extends JpaRepository<Station, Long> {
    List<Station> findByStationNameStartingWith(String stationName);

    List<Station> findByEvaNumber(String evaNumber);

    Station findByStationName(String stationName);


}
