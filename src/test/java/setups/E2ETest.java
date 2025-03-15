package setups;

import com.juliomesquita.core.CoreApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test-e2e")
@ExtendWith(CleanUpExtension.class)
@SpringBootTest(classes = CoreApplication.class)
@AutoConfigureMockMvc
@Testcontainers
public @interface E2ETest {
}
