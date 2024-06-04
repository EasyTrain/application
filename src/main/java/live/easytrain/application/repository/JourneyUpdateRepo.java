package live.easytrain.application.repository;

import live.easytrain.application.entity.JourneyUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JourneyUpdateRepo extends JpaRepository<JourneyUpdate, Long> {
}
