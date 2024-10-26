package kysh.corn.store.ses.worker.configs;

import kysh.corn.store.EnableSesStore;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        EnableSesStore.class
})
@Configuration
public class ImportConfig {
}
