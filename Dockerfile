FROM openjdk:17-jdk-slim

COPY build/libs/*.jar MovieLog-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "MovieLog-0.0.1-SNAPSHOT.jar"]