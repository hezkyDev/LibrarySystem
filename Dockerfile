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

# Set environment variables (with default values)
ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
ENV SPRING_DATASOURCE_DRIVER=org.h2.Driver
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=
ENV SERVER_PORT=8080
ENV LOGGING_LEVEL_ROOT=INFO
ENV LOGGING_LEVEL_APP=DEBUG

# Build the application using Maven
RUN ./mvnw clean package -DskipTests

# Expose the application port
EXPOSE ${SERVER_PORT}

# Run the Spring Boot application with environment variables
ENTRYPOINT ["java", "-jar", "target/LibrarySystem-0.0.1-SNAPSHOT.jar"]v