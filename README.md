Library System

This is a simple library management system built with Java and Spring Boot. The application allows users to manage books and borrowers, as well as handle borrowing and returning of books. The project is designed to follow best practices for clean code, robust testing, and modern software architecture principles, including adherence to the 12-Factor App methodology.

Features

	•	Register new borrowers and books.
	•	View the list of all books available in the library.
	•	Borrow and return books using the API.
	•	Comprehensive error handling and validation.
	•	Unit tests for core services.
	•	Containerized using Docker for consistent deployment.
	•	CI/CD pipeline set up using GitHub Actions.

Tech Stack

	•	Java 17 with Spring Boot
	•	Maven for dependency management
	•	H2 In-Memory Database for development and testing
	•	Docker for containerization
	•	GitHub Actions for CI/CD
	•	SLF4J for logging

Prerequisites

	•	Java 17 installed on your machine.
	•	Maven installed (optional if using Maven wrapper).
	•	Docker installed for running the containerized application.

Project Setup

	1.	Clone the repository:

git clone https://github.com/hezkyDev/LibrarySystem.git
cd LibrarySystem


	2.	Build the project using Maven:

./mvnw clean package


	3.	Run the application locally:

java -jar target/LibrarySystem-0.0.1-SNAPSHOT.jar


	4.	Access the API at http://localhost:8080.

API Endpoints

Register a New Borrower

	•	Endpoint: POST /api/borrowers
	•	Request Body:

{
  "name": "John Doe",
  "email": "john@example.com"
}


	•	Example:

curl -X POST http://localhost:8080/api/borrowers -H "Content-Type: application/json" -d '{"name": "John Doe", "email": "john@example.com"}'



Register a New Book

	•	Endpoint: POST /api/books
	•	Request Body:

{
  "isbn": "978-3-16-148410-0",
  "title": "The Great Book",
  "author": "Jane Doe"
}


	•	Example:

curl -X POST http://localhost:8080/api/books -H "Content-Type: application/json" -d '{"isbn": "978-3-16-148410-0", "title": "The Great Book", "author": "Jane Doe"}'



Get All Books

	•	Endpoint: GET /api/books
	•	Example:

curl -X GET http://localhost:8080/api/books



Borrow a Book

	•	Endpoint: POST /api/books/borrow
	•	Example:

curl -X POST "http://localhost:8080/api/books/borrow?borrowerId=1&bookId=1"



Return a Book

	•	Endpoint: POST /api/books/return
	•	Example:

curl -X POST "http://localhost:8080/api/books/return?borrowerId=1&bookId=1"



Running the Application with Docker

The application is fully containerized using Docker. You can easily build and run the Docker image as follows:
	1.	Build the Docker image:

docker build -t library-system:latest .


	2.	Run the Docker container:

docker run -p 8080:8080 library-system:latest


	3.	Access the API at http://localhost:8080.

Environment Variables

The application configuration can be customized using the following environment variables:
	•	SPRING_DATASOURCE_URL: Database URL (default: jdbc:h2:mem:testdb)
	•	SERVER_PORT: Server port (default: 8080)
	•	LOGGING_LEVEL_ROOT: Root logging level (default: INFO)
	•	LOGGING_LEVEL_APP: Application logging level (default: DEBUG)

Example:

docker run -e SPRING_DATASOURCE_URL=jdbc:h2:mem:customdb -e SERVER_PORT=9090 -p 9090:9090 library-system:latest

Testing

The project includes unit tests for core services. You can run the tests using Maven:

./mvnw test

Expected output:

[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS

CI/CD Pipeline

The project is integrated with GitHub Actions for CI/CD. The pipeline:
	1.	Checks out the code.
	2.	Sets up Java 17.
	3.	Builds the application with Maven.
	4.	Runs unit tests.
	5.	Builds and pushes the Docker image to GitHub Container Registry.
	6.	Tests the Docker container.

You can view the pipeline results in the Actions tab of the GitHub repository.

Assumptions

Several assumptions were made during development:
	•	Books are uniquely identified by their system-generated ID, not solely by ISBN.
	•	Each borrower is uniquely identified by their email address.
	•	The application uses H2 in-memory database for simplicity.
	•	No authentication is implemented; it is assumed the API will be used in a trusted environment.

Future Enhancements

	•	Add pagination and sorting for the list of books.
	•	Implement user authentication (e.g., JWT).
	•	Switch to an external database (e.g., PostgreSQL) for production use.
	•	Improve error handling with more detailed responses.

Conclusion

This project demonstrates best practices in modern software development, including clean code, testing, containerization, CI/CD, and 12-factor app compliance. It is well-structured, scalable, and ready for further enhancements.