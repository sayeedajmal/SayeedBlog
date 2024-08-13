FROM openjdk:17-alpine

WORKDIR /app

# Copy the JAR file into the container
COPY target/BlogDiscovery.jar /app/BlogDiscovery.jar

# Expose the port the app runs on
EXPOSE 8761

# Run the JAR file
ENTRYPOINT ["java", "-jar", "BlogDiscovery.jar"]
