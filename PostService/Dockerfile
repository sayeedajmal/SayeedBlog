FROM openjdk:17-alpine

WORKDIR /app

COPY target/PostService.jar /app/PostService.jar
EXPOSE 8081

ENTRYPOINT ["java","-jar","PostService.jar"]  