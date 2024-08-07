FROM openjdk:19

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY ./target/users-service-0.0.1-SNAPSHOT.jar /app/app.jar

# Define the command to run your application
CMD ["java", "-jar", "app.jar"]
