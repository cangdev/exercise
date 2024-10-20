liveBet
=======

liveBet is a live betting application built using Java 17 and Spring Boot 3.3.4. The project includes real-time match updates and dynamic odds, utilizing the H2 Database Engine and Maven as the build tool.

Features
--------
- Real-time match odds updates.
- Multi-betting support for up to 500 bets per match.
- Odds freeze and timeout mechanism for placed bets.
- Periodic updates using Spring's @Scheduled annotation.

Technologies
------------
- Java: 17
- Spring Boot: 3.3.4
- H2 Database Engine: In-memory database for fast testing and prototyping.
- Maven: Dependency management and build tool.
- Spring Scheduling: org.springframework.scheduling.annotation.Scheduled used for real-time odds updates.

Setup and Installation
----------------------
1. Clone the repository:
   git clone https://github.com/cangdev/exercise.git

2. Navigate to the project directory:
   cd liveBet

3. Run the application:
   mvn spring-boot:run

4. Access the application at:
   http://localhost:9090

5. To Run application with start script file:
   start-application.sh

5. To stop application with stop script file:
   stop-application.sh

Running Tests
-------------
To run the tests:
   mvn test

Database
--------
The project uses H2 Database Engine, and the database is configured to run in-memory. You can access the H2 Console to view the database schema and records:

- H2 Console URL: http://localhost:9090/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: 