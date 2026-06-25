# Metro Booking System (Backend API)

This project is the backend API for the Metro Booking System, built with Java and Spring Boot. It provides a complete REST architecture to manage user accounts, station networks, and ticket bookings.

## Architecture & Features

- User Management: Account creation and admin privilege validation.
- Station Network: Management of metro stations and adjacency links.
- Smart Routing: Integrated Dijkstra's algorithm for finding the shortest paths between stations.
- Ticket Booking: Secure ticket creation ensuring users only manage their own bookings.
- Pricing Algorithm: Dynamic ticket pricing based on route distance (base fare + per-kilometer distance calculation).

## Database Configuration

The application uses PostgreSQL as its primary database.

1. Install PostgreSQL and ensure you have created a database named `metro_admin_db` before running the program.
2. The project uses an `env.properties` file to securely manage database credentials without exposing them in the source code.
3. Create a file named `env.properties` in `src/main/resources/` (this file is excluded from version control) and populate it with your local credentials:
   DB_URL=jdbc:postgresql://localhost:5432/metro_admin_db
   DB_USER=your_username
   DB_PASSWORD=your_password

## Setup & Running

- Ensure you have the correct Java Development Kit (JDK) installed that matches the version specified in the `pom.xml`.
- Run the application via your preferred IDE or by using the included Maven wrapper in the terminal: `./mvnw spring-boot:run`
- For initial database setup, ensure `spring.jpa.hibernate.ddl-auto` in `application.properties` is set to `create` or `update` so Hibernate can generate the necessary tables automatically.
