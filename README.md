<h1>Card Management System</h1>
The Card Management System is a multi-module Spring Boot project designed to manage credit card requests through a RESTful API, process them using listeners, and provide a web user interface for interaction.

## Table of Contents

[Technologies Used](#technologies-used) | [Modules](#modules) | [Usage](#usage) | [Swagger Integration](#swagger-integration) | [License](#license) | [Additional Notes](#additional-notes) | [Task description](#task-description)

<hr style="border:2px solid gray"> </hr>

## Technologies Used

- Spring Boot: Framework for building robust and scalable Java applications.
- Spring Data JPA: Simplifies database access and manipulation through the use of Java Persistence API (JPA).
- PostgresSQL: Relational database management system used for storing card request data.
- Kafka: Distributed event streaming platform for building real-time data pipelines and streaming applications.
- Thymeleaf: Server-side Java template engine for web and standalone environments.
- Jackson: JSON serialization and deserialization library for Java.
- Hibernate Validator: Implementation of the Bean Validation API for declarative validation.
- AspectJ: Aspect-oriented programming (AOP) framework used for cross-cutting concerns such as logging, validation, and
  Kafka messaging.
- Maven: Build automation tool for managing dependencies and building Java projects.
- Jakarta Validation: API for defining and enforcing constraints on Java objects.
- Lombok: Library for reducing boilerplate code in Java applications.
- Swagger: API documentation tool for designing, building, and documenting APIs.

<hr style="border:2px solid gray"> </hr>

## Modules

1. Card Management API
   - Core module responsible for handling credit card requests and database operations.
   - Uses PostgresSQL for data storage.
   - Exposes REST endpoints for CRUD operations on credit card requests.
   - Utilizes aspects for sending messages to Kafka and validating objects.
   - Configuration for database and Kafka communication is provided in the application.properties file.
2. Card Management Listener
   - Module containing a Kafka listener for processing credit card requests.
   - Listens to a Kafka topic for incoming card request messages.
   - Updates the status of processed card requests and sends messages to another Kafka topic.
   - Uses an API service for interacting with the Card Management API.
3. Card Management Web UI
   - Module implementing a web user interface using Thymeleaf framework.
   - Communicates with the Card Management API using RestTemplate for data retrieval and manipulation.
   - Provides functionalities such as creating, updating, and deleting card requests, as well as searching for requests
     by OIB.

<hr style="border:2px solid gray"> </hr>

## Usage

1. Setup Database
   - Ensure that PostgresSQL is installed and running.
   - Update the application.properties file in the Card Management API module with your PostgresSQL database
     configurations.
2. Start Kafka
   - Ensure Kafka is installed and running.
   - Configure Kafka properties in the application.properties file of the Card Management API module.
3. Run Modules
   - Build and run each module separately using Maven or your preferred IDE.
   - Start with the Card Management API module, followed by the Card Management Listener and Card Management Web UI.
4. Access Web UI
   - Once all modules are running, access the web user interface at the specified port (default: 8086).
   - Use the provided functionalities to interact with the system.

<hr style="border:2px solid gray"> </hr>

## Swagger Integration

Swagger UI is integrated into the project using the `springdoc-openapi-starter-webmvc-ui` dependency. To access the
Swagger UI and explore the API endpoints, follow these steps:

1. Ensure all modules are running.
2. Navigate to `http://localhost:<port>/swagger-ui.html` in your web browser, where `<port>` is the port your
   application is running on.

<hr style="border:2px solid gray"> </hr>

## License

This project is licensed under the GNU GENERAL PUBLIC LICENSE Version 3. See
the [LICENSE](https://www.gnu.org/licenses/gpl-3.0.en.html) file for details.

<hr style="border:2px solid gray"> </hr>

## Additional Notes

- Ensure proper network configuration for Kafka communication between modules.
- For troubleshooting and further customization, refer to the individual module's source code and documentation.

<hr style="border:2px solid gray"> </hr>

## Task description

The bank issues credit cards to individuals. Individuals apply to the bank for this purpose. For the purpose of this
record keeping, a mini-application needs to be created to record individuals (O) or multiple individuals represented by
their First Name, Last Name, Personal Identification Number (OIB), and Status for which a card needs to be issued. The
type of card is not important, as there is only one, so you don't need to worry about it. Individuals must be recorded
permanently, and you can choose the method as you wish. A database (any, including H2) or a file is preferred.

To let the process for the production/printing of credit cards know whose card to make, you need to pass the data
through a RESTful API. You can find the API definition in the attached YAML file.

Note: The card production process here is imagined to give some sense, but you won't deal with it (unless you have the
desire and willingness, in which case feel free to enhance the API to do something smart with recording and checking the
existence of the card for the individual, etc.).

The application should enable:

Entering an individual (O) into the set of individuals with all corresponding attributes (First Name, Last Name, OIB,
Status).
Searching the set of individuals (O) by OIB (manual entry by the user), and if the individual (O) exists, return the
First Name, Last Name, OIB, and Status for it; otherwise, return nothing.
For the found individual (O), send the data to the mentioned API with all filled attributes (First Name, Last Name, OIB,
Status).
One API call should contain data for only one individual (O).
An individual (O) should be able to be deleted upon request by OIB (manual entry by the user).

The methods should be made to work as RESTful.

Bonus Feature I:
Implement receiving data about the card production status from the API via a KAFKA topic. (The status can be fictional,
mock, by choice, unless you've decided the API has some intelligence it performs.)

Bonus feature II:
Set up a small form that will provide user support for working with the above process. Freedom is allowed, but we prefer
React.