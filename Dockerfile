FROM openjdk:17-jdk-alpine
ENV MYSQLUSERNAME=""
ENV MYSQLPASSWORD=""
COPY *.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=prod","-Dspring.datasource.username=$MYSQLUSERNAME","-Dspring.datasource.password=$MYSQLPASSWORD"]