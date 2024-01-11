# Base image
FROM openjdk:17

# Set working directory
WORKDIR /app

# Copy the JAR file to the container
COPY build/libs/*.jar app.jar

# Expose the port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]