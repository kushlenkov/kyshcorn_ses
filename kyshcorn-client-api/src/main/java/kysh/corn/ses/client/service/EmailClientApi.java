package kysh.corn.ses.client.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class EmailClientApi {

    @SneakyThrows
    public void sendEmail(String destinationEmail, String message) {

        //TODO purururu, email send
        Thread.sleep(1000L);

        log.info("Email to %s successfully sent.".formatted(destinationEmail));
    }
}