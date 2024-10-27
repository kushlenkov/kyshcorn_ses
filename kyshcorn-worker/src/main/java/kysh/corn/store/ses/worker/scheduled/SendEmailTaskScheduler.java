package kysh.corn.store.ses.worker.scheduled;

import kysh.corn.store.dao.SendEmailTaskDao;
import kysh.corn.store.entities.SendEmailTaskEntity;
import kysh.corn.store.ses.worker.service.EmailClientApi;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class SendEmailTaskScheduler {

    private static final Logger log = LoggerFactory.getLogger(SendEmailTaskScheduler.class);
    SendEmailTaskDao sendEmailTaskDao;

    EmailClientApi emailClientApi;

    @Scheduled(cron = "*/5 * * * * *")
    public void executeSendEmailTasks() {

        sendEmailTaskDao
                .findAllNotProcessed()
                .forEach(sendEmailTask -> {

                    String destinationEmail = sendEmailTask.getDestinationEmail();
                    String message = sendEmailTask.getMessage();

                    boolean delivered = emailClientApi.sendEmail(destinationEmail, message);

                    if (delivered) {

                        log.debug("Task %d already processed.".formatted(sendEmailTask.getId()));
                        sendEmailTaskDao.markAsProcessed(sendEmailTask);

                        return;
                    }

                    log.warn("Task %d returned to process.".formatted(sendEmailTask.getId()));
                    sendEmailTaskDao.updateLatestTryAt(sendEmailTask);
                });
    }
}
