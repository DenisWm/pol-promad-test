package com.pol.promad.test;

import com.pol.promad.test.infrastructure.configuration.WebServerConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test-e2e")
@SpringBootTest(classes = WebServerConfig.class)
@ExtendWith(PostgresSQLCleanUpExtension.class)
@AutoConfigureMockMvc
public @interface E2ETest {
}
