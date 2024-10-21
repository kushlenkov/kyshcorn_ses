package kysh.corn.ses.client.store.repositories;

import kysh.corn.ses.client.store.entities.SendEmailTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendEmailTaskRepository extends JpaRepository<SendEmailTaskEntity, Long> {
}