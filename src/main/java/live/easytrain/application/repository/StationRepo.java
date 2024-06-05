package live.easytrain.application.repository;

import live.easytrain.application.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepo extends JpaRepository<Station, Long> {
    List<Station> findByStationNameStartingWith(String stationName);

    Station findByStationName(String stationName);

    Station findByEvaNumber(String scheduleId);
}
