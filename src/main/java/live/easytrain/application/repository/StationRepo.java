package live.easytrain.application.repository;

import live.easytrain.application.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StationRepo extends JpaRepository<Station, Long> {
    Station findByEvaNumber(String evaNumber);
}
