FROM openjdk:17-alpine

WORKDIR /app

# Copy the JAR file into the container
COPY target/BlogGateway.jar /app/BlogGateway.jar

ENV MicroZone=${MicroZone}
# Expose the port the app runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "BlogGateway.jar"]
