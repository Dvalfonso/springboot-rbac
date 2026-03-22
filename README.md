# 🏦 Secure Bank API

A secure and extensible backend API designed to simulate a **banking-grade authorization system**, built with modern Java technologies and best practices in authentication, data integrity, and layered architecture.

This project focuses on **Role-Based Access Control (RBAC)**, secure authentication using JWT, and database consistency through versioned migrations.

---

## 🚀 Tech Stack

- **Language:** Java  
- **Framework:** Spring Boot  
- **Security:** Spring Security + JWT  
- **Persistence:** JPA / Hibernate  
- **Database:** PostgreSQL  
- **Migrations:** Flyway  
- **Testing:** JUnit 5 + Mockito  
- **Password Hashing:** BCrypt  

---

## 🧠 Architecture

The application follows a **layered architecture**, commonly used in enterprise and banking systems:

- **Controller Layer** → Handles HTTP requests and responses  
- **Service Layer** → Contains business logic  
- **Repository Layer (DAO)** → Handles database operations  
- **DTOs** → Used for safe data transfer between layers  

This separation ensures:

- Maintainability  
- Testability  
- Clear responsibility boundaries  

---

## 🔐 Security

Security is a core aspect of this project and was designed with **banking standards in mind**.

### Authentication

- Stateless authentication using **JSON Web Tokens (JWT)**
- Custom JWT filter integrated into Spring Security pipeline
- Secure login flow with credential validation

### Authorization (RBAC)

- Role-Based Access Control implementation
- Roles such as:
  - `ADMIN`
  - `USER`
- Endpoint protection based on roles

### Password Security

- Passwords are securely hashed using **BCrypt**
- No plain-text password storage

### Token Management

- JWT tokens include expiration handling
- Designed to support future improvements such as refresh tokens

---

## 🧪 Testing

The project includes unit testing focused on the service layer:

- **Framework:** JUnit 5  
- **Mocking:** Mockito  

### Current Coverage

- `UserService` fully tested
- Repository layer is mocked to isolate business logic

### Testing Approach

- Isolation of business logic  
- Verification of service behavior under different scenarios  
- Clean separation between test and production code  

---

## 🗄️ Database & Migrations

Database changes are managed using Flyway, ensuring:

- Version-controlled schema evolution  
- Reproducible environments  
- Safer deployments  

---

## 📌 Current Features

- User registration  
- Secure login with JWT  
- Role-based authorization  
- Layered architecture (Controller / Service / Repository)  
- DTO-based data transfer  
- Database versioning with Flyway  
- Unit testing with mocks  

---

## 🔜 Planned Enhancements

To move closer to a **production-grade banking backend**, the following features are planned:

- API documentation with Swagger (OpenAPI)  
- Containerization using Docker  
- Audit logging persisted in the database  
- Refresh token mechanism  
- Improved token lifecycle management  
- Enhanced validation and error handling  

---
