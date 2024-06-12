package live.easytrain.application.repository;

import live.easytrain.application.entity.JourneyUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyUpdateRepo extends JpaRepository<JourneyUpdate, Long> {
    List<JourneyUpdate> findByScheduleId(String scheduleId);
}
