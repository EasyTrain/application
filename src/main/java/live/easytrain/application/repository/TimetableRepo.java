package live.easytrain.application.repository;

import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TimetableRepo extends JpaRepository<Timetable, Long> {

    List<Timetable> findByStation(Station station);
}
