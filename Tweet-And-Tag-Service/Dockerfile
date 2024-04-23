FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} tweet-and-tag.jar

ENTRYPOINT ["java", "-jar", "/tweet-and-tag.jar"]

EXPOSE 9003