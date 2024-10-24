package kysh.corn.store.repositories;

import kysh.corn.store.entities.SendEmailTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendEmailTaskRepository extends JpaRepository<SendEmailTaskEntity, Long> {
}