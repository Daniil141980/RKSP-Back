FROM openjdk:17-alpine3.14
VOLUME /tmp
VOLUME /files
EXPOSE 8080
ARG JAR_FILE=/target/tinkoff-project-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} project.jar
ENTRYPOINT ["java", "-jar", "/project.jar", "--spring.profiles.active=prod"]