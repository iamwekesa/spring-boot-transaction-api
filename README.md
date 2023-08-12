# Spring Boot Assessment Coding Test

This repository contains the solution for the Spring Boot Assessment Coding Test. This project demonstrates that demonstrates proficiency in various aspects of Spring Boot development, including entity and repository creation, service and controller implementation, exception handling, unit testing, and optional pagination implementation.

## Requirements

- Java 
- Maven
- PostgreSQL (Through Docker)

## Docker Compose Setup

1. Ensure you have Docker installed and running on your system.
 
## Setting Up

1. Clone this repository to your local machine.
2. Run the following command to start the PostgreSQL database container:
   ```bash
   docker-compose up -d

## API Endpoints

- `GET /transactions`: Get a list of all transactions.
- `GET /transactions/{id}`: Get a specific transaction by ID.
- `POST /transactions`: Create a new transaction.
- `PUT /transactions/{id}`: Update an existing transaction.
- `DELETE /transactions/{id}`: Delete a transaction.

## Testing

1. Run the unit tests: `mvn test`
