package edu.tinkoff.tinkoffbackendacademypetproject;

import edu.tinkoff.tinkoffbackendacademypetproject.config.PostgresTestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = PostgresTestConfig.Initializer.class)
public abstract class CommonAbstractTest {
}
