package kysh.corn.store.ses.worker.scheduled;

import kysh.corn.store.dao.SendEmailTaskDao;
import kysh.corn.store.entities.SendEmailTaskEntity;
import kysh.corn.store.ses.worker.service.EmailClientApi;
import kysh.corn.store.ses.worker.service.RedisLock;
import kysh.corn.store.ses.worker.service.RedisLockWrapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class SendEmailTaskScheduler {

    SendEmailTaskDao sendEmailTaskDao;

    EmailClientApi emailClientApi;

    RedisLockWrapper redisLockWrapper;

    private static final String SEND_EMAIL_TASK_KEY_FORMAT = "kyshcorn:send:email:task:%s";

    @Scheduled(cron = "*/5 * * * * *")
    public void executeSendEmailTasks() {

        List<Long> sendEmailTasksIds = sendEmailTaskDao.findNotProcessedIds();

        for (Long sendEmailTaskId : sendEmailTasksIds) {
            String sendEmailTaskKey = getSendEmailTaskKey(sendEmailTaskId);

            redisLockWrapper.lockAndExecuteTask(
                    sendEmailTaskKey,
                    Duration.ofSeconds(5),
                    () -> sendEmail(sendEmailTaskId)
            );
        }
    }

    private void sendEmail(Long sendEmailTaskId) {

        Optional<SendEmailTaskEntity> optionalSendEmailTask = sendEmailTaskDao
                .findNotProcessedById(sendEmailTaskId);

        if (optionalSendEmailTask.isEmpty()) {
            log.info("Task %s already processed.".formatted(sendEmailTaskId));
            return;
        }

        SendEmailTaskEntity sendEmailTask = optionalSendEmailTask.get();

        String destinationEmail = sendEmailTask.getDestinationEmail();
        String message = sendEmailTask.getMessage();

        boolean delivered = emailClientApi.sendEmail(destinationEmail, message);

        if (delivered) {

            log.debug("Task %d processed.".formatted(sendEmailTask.getId()));
            sendEmailTaskDao.markAsProcessed(sendEmailTask);

            return;
        }

        log.warn("Task %d returned to process.".formatted(sendEmailTask.getId()));
        sendEmailTaskDao.updateLatestTryAt(sendEmailTask);
    }

    private static String getSendEmailTaskKey(Long taskId) {
        return SEND_EMAIL_TASK_KEY_FORMAT.formatted(taskId);
    }
}