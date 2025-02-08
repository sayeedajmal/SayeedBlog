FROM openjdk:17-alpine

WORKDIR /app

COPY target/AuthorService.jar /app/AuthorService.jar
EXPOSE 8081

ENTRYPOINT ["java","-jar","AuthorService.jar"]