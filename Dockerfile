# Use a lightweight OpenJDK image
FROM openjdk:17-slim

# Set the working directory inside the container
WORKDIR /app

# Copy Maven wrapper scripts and configuration files
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Ensure Maven wrapper has execution permissions
RUN chmod +x mvnw

# Copy the Maven project files (pom.xml)
COPY pom.xml .

# Download dependencies without building the project
RUN ./mvnw dependency:go-offline -B

# Copy the source code into the container
COPY src ./src

# Build the application using Maven
RUN ./mvnw clean package -DskipTests

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "target/LibrarySystem-0.0.1-SNAPSHOT.jar"]