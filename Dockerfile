# Use an official OpenJDK runtime as a base image
FROM openjdk:11-jdk

# Set the working directory to /app
WORKDIR /app

# Copy the compiled Spring Boot application to the working directory
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Expose port 8080 for the app to listen on
EXPOSE 3306

# Start the Spring Boot application
CMD ["java", "-jar", "/app/demo.jar"]