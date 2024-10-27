package kysh.corn.store.ses.worker.configs;

import kysh.corn.store.EnableSesStore;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Import({
        EnableSesStore.class
})
@Configuration
@EnableScheduling
public class ImportConfig {
}
