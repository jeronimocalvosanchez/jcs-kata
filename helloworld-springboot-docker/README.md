# Hello World - Spring Boot - Docker

![Static Badge](https://img.shields.io/badge/java-22-blue)
![Static Badge](https://img.shields.io/badge/spring_boot-3.2.4-blue)
![Static Badge](https://img.shields.io/badge/license-mit-green)

The goal of this exercise is to learn how to deploy a hello world application 
written in java spring boot application using Docker. A Docker Image will be built, 
a container will be created from this image, and then run the container.

## Prerequirements
- **Java 22**: This project uses JDK version 22. You can use OpenJDK.
- **Maven**: Part of the Spring framework, providing support for building web applications.
- **Spring Boot 3.2.4**: A powerful framework for building Java-based applications with minimal configuration.
We are going to use **web** and **test** modules
- **Docker**: How to install Docker

## Environment preparation

```shell
# Clone the Repository
$> git clone https://github.com/jeronimocalvosanchez/jcs-kata.git
$> cd jcs-kata/helloworld-springboot-docker

# Build the Project artifact
$> mvn clean package

# Run the Application. Make sure 8080 port is not already in used
$> java -jar target/helloworld-springboot-docker-1.0.0.jar

# Access the application
# You should see a "App is up and running" message displayed.
$> curl http://localhost:8080
# You should see a "Hello, World!" message displayed.
$> curl http://localhost:8080/hello

#Kill the java process
#In Windows
$> tasklist /FI "IMAGENAME eq java*"
taskkill /F /PID pid_number

```

## Usage

```shell
#Make sure the hello world app is not running, this command should return a 404 error
$> curl http://localhost:8080
#Make sure Docker is up and running
$> docker --help

#Build Docker image
$> docker build --tag=helloworld-springboot-docker:latest .

#Check that the Docker image has been built ok
$> docker inspect helloworld-springboot-docker

#Create and Run the container
$> docker run -p 8080:8080 --name hwsbd helloworld-springboot-docker:latest

#Make sure the hello world app is running, this command should return a 200 success
$> curl http://localhost:8080
```

### Further exploration

```shell
#Create the container without running it
$> docker create -p 8080:8080 --name hwsbd helloworld-springboot-docker
$> docker start hwsbd
$> curl http://localhost:8080
$> docker stop hwsbd
```

### Useful Docker commands

```shell
#List all images
$> docker image ls
#Remove one image
$> docker image rm [image_name]
#Remove ALL images
$> docker image rm $(docker images -a -q)

#List all containers
$> docker ps -a
#Remove one container
$> docker rm [container_name]
```

## License

[MIT](https://choosealicense.com/licenses/mit/)