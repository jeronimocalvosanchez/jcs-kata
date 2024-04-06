# Hello World - Spring Boot Web

![Static Badge](https://img.shields.io/badge/java-22-blue)
![Static Badge](https://img.shields.io/badge/spring_boot-3.2.4-blue)
![Static Badge](https://img.shields.io/badge/license-mit-green)

The goal of this exercise is to have up and running a web app using Spring Boot Web, with a simple hello world response

## Technologies used
- **Java 22**: This project uses JDK version 22. You can use OpenJDK.
- **Maven**: Part of the Spring framework, providing support for building web applications.
- **Spring Boot 3.2.4**: A powerful framework for building Java-based applications with minimal configuration.
We are going to use **web** and **test** modules

## Usage

```shell
# Clone the Repository
git clone https://github.com/jeronimocalvosanchez/jcs-kata.git
cd jcs-kata
cd spring-boot-hello-world

# Build the Project
mvn clean install

# Run the Application. Make sure 8080 port is not already in used
mvn spring-boot:run

```

**Access the Application**

Open a web browser.
- Navigate to `http://localhost:8080`. You should see a "App is up and running" message displayed.
- Navigate to `http://localhost:8080/hello`. You should see a "Hello, World!" message displayed.


## License

[MIT](https://choosealicense.com/licenses/mit/)