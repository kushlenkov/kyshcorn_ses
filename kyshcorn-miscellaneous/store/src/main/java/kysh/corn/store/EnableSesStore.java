package kysh.corn.store;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("kysh.corn.store")
@EntityScan("kysh.corn.store.entities")
@EnableJpaRepositories("kysh.corn.store.repositories")
public class EnableSesStore {
}
