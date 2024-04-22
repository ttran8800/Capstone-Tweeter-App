FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} api.jar


ENTRYPOINT ["java", "-jar", "/api.jar"]

EXPOSE 9000