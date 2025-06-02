package edu.tinkoff.tinkoffbackendacademypetproject.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.Duration;
import java.util.Objects;


public class MinIOTestConfig {
    private static volatile MinIOContainer minIOContainer = null;

    private static MinIOContainer getMinIOContainer() {
        var instance = minIOContainer;
        if (Objects.isNull(instance)) {
            synchronized (PostgreSQLContainer.class) {
                instance = minIOContainer;
                if (Objects.isNull(instance)) {
                    minIOContainer = instance = new MinIOContainer("minio/minio")
                            .withUserName("test-user")
                            .withPassword("test-pass")
                            .withStartupTimeout(Duration.ofSeconds(60))
                            .withReuse(true);
                    minIOContainer.start();
                }
            }
        }
        return instance;
    }

    @Component("MinioInitializer")
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            var minIOContainer = getMinIOContainer();

            var s3url = minIOContainer.getS3URL();
            var username = minIOContainer.getUserName();
            var password = minIOContainer.getPassword();
            var port = minIOContainer.getMappedPort(9000);

            TestPropertyValues.of(
                    "storage.url=" + s3url,
                    "storage.port=" + port,
                    "storage.accessKey=" + username,
                    "storage.secretKey=" + password,
                    "storage.secure=false",
                    "storage.bucket=minio-storage",
                    "storage.image-size=10485760"
            ).applyTo(applicationContext.getEnvironment());

        }
    }
}