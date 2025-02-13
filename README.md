# Vet Manager API 🏥🐕

This repository contains the RESTful API for **Vet Manager**, a veterinary
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

## 🗄️ Database

The database diagram is available in
[this lucidchart project](https://lucid.app/lucidchart/61bff0a4-a37a-4f01-904f-2a490a0f4927/edit?viewport_loc=151%2C-181%2C4119%2C2068%2C0_0&invitationId=inv_4e6908de-5caf-4079-b7ae-c473e6083fb2).
