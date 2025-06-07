# Use JDK 8 as base image
FROM openjdk:8-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the JAR file
COPY target/VSLearnApi-1.0.0.jar app.jar

# Expose port 8080
EXPOSE 8080

# Set environment variables
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 