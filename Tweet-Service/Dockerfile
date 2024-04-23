FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} tweet-service.jar

ENTRYPOINT ["java", "-jar", "/tweet-service.jar"]

EXPOSE 9002