package live.easytrain.application.repository;

import live.easytrain.application.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepo extends JpaRepository<Timetable, Long> {
}
