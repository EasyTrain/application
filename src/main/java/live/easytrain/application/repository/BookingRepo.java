package live.easytrain.application.repository;

import live.easytrain.application.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    List<Booking> findAllById(Long id);
}
