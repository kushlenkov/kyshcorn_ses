package kysh.corn.store.dao;

import kysh.corn.store.entities.SendEmailTaskEntity;
import kysh.corn.store.repositories.SendEmailTaskRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class SendEmailTaskDao {

    SendEmailTaskRepository sendEmailTaskRepository;

    @Transactional
    public SendEmailTaskEntity save(SendEmailTaskEntity entity) {
        return sendEmailTaskRepository.save(entity);
    }

    public List<SendEmailTaskEntity> findAllNotProcessed() {
        return sendEmailTaskRepository.findAllNotProcessed();
    }

    @Transactional
    public void markAsProcessed(SendEmailTaskEntity entity) {
        sendEmailTaskRepository.markAsProcessed(entity.getId());
    }

    public void updateLatestTryAt(SendEmailTaskEntity entity) {
        sendEmailTaskRepository.updateLatestTryAt(entity.getId());
    }
}