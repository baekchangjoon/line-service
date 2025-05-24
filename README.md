# Line Service

A Spring Boot-based microservice for managing mobile line subscriptions and related operations. This service is integrated with the Member Service to provide a complete member management solution.

## Features

- Line subscription management (create, read, update, cancel)
- Line history tracking
- Plan management
- Integration with Member Service
- RESTful API endpoints

## Prerequisites

- Java 11
- MySQL 8.0
- Maven

## Database Setup

Before running the application, ensure MySQL is running and configured with the following settings:

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/linedb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: 1234
```

The database will be automatically created if it doesn't exist.

## Running the Application

1. Start MySQL server
2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

The service will start on port 8082.

## Integration with Member Service

This service is designed to work in conjunction with the Member Service:

- Member Service (port 8081) creates new members and automatically creates default lines through this service
- Line Service (port 8082) handles all line-related operations
- When a new member is created in Member Service, it automatically creates a default line through the Line Service API

## Testing

### Important Notes

1. **Database Requirement**: 
   - The MySQL database must be running and accessible with the credentials specified in `application.yml`
   - The database will be automatically created if it doesn't exist

2. **Integration Test Requirements**:
   - Integration tests require a running MySQL instance
   - Some tests may require the Member Service to be running

### Running Tests

```bash
./mvnw test
```

## API Endpoints

### Line Management
- `POST /api/lines` - Create a new line
- `GET /api/lines/{id}` - Get line by ID
- `GET /api/lines` - Get all lines
- `PUT /api/lines/{id}` - Update line
- `PUT /api/lines/{id}/cancel` - Cancel line

### Line History
- Line history is automatically recorded for all line operations
- History includes creation, updates, and cancellations

## Test Data Generation

A Python script is provided to generate test data:
```bash
python src/test/resources/generate_line_test_data.py
```

This script will create sample lines and line history data in the database. 