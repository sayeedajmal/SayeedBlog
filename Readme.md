# Microservices Blog Project

This project demonstrates a microservices architecture for a blogging platform using Spring Boot, MongoDB, and various Spring technologies.

## Project Overview

The project consists of several microservices that collectively provide a blogging platform with functionalities such as post management, user authentication, and notifications.

### Modules

1. **PostService**: Manages posts, comments, likes, and search functionality.
2. **UserService**: Manages user profiles, authentication, and user-related operations.
3. **NotificationService**: Manages notifications and alerting users about activities.

## Getting Started

To run the project locally, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/SayeedBlog.git
   cd /SayeedBlog
   ```

2. **Build the project:**

   ```bash
   mvn clean install
   ```

3. **Run each microservice:**

   - **PostService:**

     ```bash
     cd PostService
     mvn spring-boot:run
     ```

   - **UserService:**
     ```bash
     cd UserService
     mvn spring-boot:run
     ```
   - **NotificationService:**
     ```bash
     cd NotificationService
     mvn spring-boot:run
     ```

4. **Access APIs:**

   - **PostService APIs:** http://localhost/api/posts
   - **UserService APIs:** http://localhost/api/users
   - **NotificationService APIs:** http://localhost/api/notifications

5. **Documentation:**

   Swagger/OpenAPI documentation can be accessed at:

   - **PostService:** http://localhost/swagger-ui.html
   - **UserService:** http://localhost/swagger-ui.html
   - **NotificationService:** http://localhost/swagger-ui.html

## Project Structure

The project is organized into the following modules:

### 1. PostService

**Structure:**

```
PostService
├── src
│   ├── main
│   │   ├── java/com/strong/PostService
│   │   │   ├── controller     // REST controllers
│   │   │   ├── model          // Entity classes (Post, Comment, Like, Search)
│   │   │   ├── repository     // Spring Data MongoDB repositories
│   │   │   ├── service        // Service classes (PostService, CommentService, etc.)
│   │   │   └── PostServiceApplication.java  // Spring Boot application main class
│   │   └── resources
│   │       ├── application.properties  // Configuration properties
│   │       └── static
│   └── test
│       └── java/com/strong/PostService
│           └── (Unit and Integration Tests)
└── pom.xml
```

### 2. UserService

**Structure:**

```
UserService
├── src
│   ├── main
│   │   ├── java/com/strong/UserService
│   │   │   ├── controller     // REST controllers
│   │   │   ├── model          // Entity classes (User, Role, AuthRequest, AuthResponse)
│   │   │   ├── repository     // Spring Data MongoDB repositories
│   │   │   ├── service        // Service classes (UserService, AuthenticationService, etc.)
│   │   │   └── UserServiceApplication.java  // Spring Boot application main class
│   │   └── resources
│   │       ├── application.properties  // Configuration properties
│   │       └── static
│   └── test
│       └── java/com/strong/UserService
│           └── (Unit and Integration Tests)
└── pom.xml
```

### 3. NotificationService

**Structure:**

```
NotificationService
├── src
│   ├── main
│   │   ├── java/com/strong/NotificationService
│   │   │   ├── controller     // REST controllers
│   │   │   ├── model          // Entity classes (Notification, NotificationTemplate)
│   │   │   ├── repository     // Spring Data MongoDB repositories
│   │   │   ├── service        // Service classes (NotificationService, EmailService, etc.)
│   │   │   └── NotificationServiceApplication.java  // Spring Boot application main class
│   │   └── resources
│   │       ├── application.properties  // Configuration properties
│   │       └── static
│   └── test
│       └── java/com/strong/NotificationService
│           └── (Unit and Integration Tests)
└── pom.xml
```

## Technologies Used

- **Spring Boot:** Framework for creating microservices.
- **Spring Data MongoDB:** Integration with MongoDB for data storage.
- **Spring Security:** Authentication and authorization.
- **Swagger/OpenAPI:** API documentation and testing.
- **Maven:** Dependency management and build automation.

## Contributing

Feel free to contribute to this project by forking and creating pull requests. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
