package live.easytrain.application.repository;

import live.easytrain.application.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepo extends JpaRepository<Connection, Long> {
}
