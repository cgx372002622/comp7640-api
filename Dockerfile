FROM openjdk:17-jdk-alpine
COPY *.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=prod","-Dspring.datasource.password=$MYSQL_PASSWORD"]