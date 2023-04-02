
FROM openjdk:17
ADD target/spring-boot-docker-trello.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 7071