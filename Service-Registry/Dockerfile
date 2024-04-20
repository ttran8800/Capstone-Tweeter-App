FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} registry.jar

ENTRYPOINT ["java", "-jar", "/registry.jar"]

EXPOSE 8761