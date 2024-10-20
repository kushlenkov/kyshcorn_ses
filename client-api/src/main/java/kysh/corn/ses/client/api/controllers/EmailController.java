package kysh.corn.ses.client.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class EmailController {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/api/email/send")
    public void sendEmail(
            @RequestParam("destination_email") String destinationEmail,
            @RequestParam String message) {



    }
}