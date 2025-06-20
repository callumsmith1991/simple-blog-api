# Use a base image with JDK 24
FROM eclipse-temurin:24-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file into the image
COPY target/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
