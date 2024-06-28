package live.easytrain.application.api.payments.repo;

import live.easytrain.application.api.payments.entity.PaymentApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentApiEntityRepo extends JpaRepository<PaymentApiEntity, Long> {

    PaymentApiEntity findByEncryptedData(String encryptedData);
}
