# Vet Manager HC API

This repository contains the RESTful API for **Vet Manager HC**, a veterinary
management system designed for efficiency and scalability.

## 🚀 Technologies

The project leverages the following technologies and libraries:

- **Backend**: Java, Spring Boot, Spring MVC, Spring Security
- **Authentication**: JWT
- **Data Persistence**: JPA, MySQL
- **Validation**: Jakarta Validation
- **Documentation**: Swagger
- **Utilities**: Lombok, MapStruct, Google Guava, Apache Commons, Apache POI
- **Testing**: JUnit, Mockito

## 🛠️ How to Run the Application

### Development Mode

Run the application in development mode with:

```bash
./mvnw spring-boot:run
```

- The application will start on port `8080` with the `dev` profile enabled by
  default.

### Production Mode

For production, execute:

```bash
./mvnw spring-boot:run -Dspring.profiles.active=prod
```

**Required Environment Variables**:

- `SPRING_DATABASE_URL`: Database connection URL.
- `SPRING_DATABASE_USERNAME`: Database username.
- `SPRING_DATABASE_PASSWORD`: Database password.

## 🔒 Security

This API uses **JWT (JSON Web Token)** for authentication.  
To access protected endpoints, include a valid token in the `Authorization`
header of your requests:

```http
Authorization: Bearer <your_token>
```

### Token Generation

- **Login**: `/auth/login`
- **Register**: `/auth/register`

### Public Endpoints

All publicly accessible endpoints are configured in
[`application.yml`](src/main/resources/application.yml).

## 📚 API Documentation

Interactive API documentation is available via **Swagger**.  
After starting the application locally, visit:  
<http://localhost:8080/api/v0/docs>

The production documentation is available at:  
<https://api.vet-manager-hc.cupscoffee.xyz/api/v0/docs>
