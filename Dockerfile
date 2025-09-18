# Use Java 21
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/Job_Portal_System-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Start the app
ENTRYPOINT ["java", "-jar", "app.jar"]
