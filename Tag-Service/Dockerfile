FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} tag-service.jar

ENTRYPOINT ["java", "-jar", "/tag-service.jar"]

EXPOSE 9003