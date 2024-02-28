# Use Amazon Corretto 17 with Alpine Linux as the base image
FROM amazoncorretto:17.0.0-alpine

# Copy the JAR file(s) from the target directory into the image
ADD target/*.jar app.jar

# Set the default command to run the Java application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]