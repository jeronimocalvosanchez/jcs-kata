# Hello World - Docker

![Static Badge](https://img.shields.io/badge/java-17-blue)
![Static Badge](https://img.shields.io/badge/spring_boot-3.2.4-blue)
![Static Badge](https://img.shields.io/badge/dcoker-26.0.0-blue)
![Static Badge](https://img.shields.io/badge/license-mit-green)

The goal of this exercise is to learn how to deploy a hello world application 
written in Java Spring Boot using Docker.

A Docker Image will be built from a JAR file generated from the previous Hello World Spring Boot Web exercise that can be found [here](../helloworld-springboot-web/README.md), a Container will be created from this image, and then run.

## Prerequirements
- **GIT**
- **Java 17**: This project uses JDK version 22, but will run a Java 17 application. You can use OpenJDK.
- **Docker**: [How to install Docker](https://docs.docker.com/get-docker/)

## Environment preparation

First, we need to make sure our JAR application is working as expected.

```shell
# Clone the Repository
git clone https://github.com/jeronimocalvosanchez/jcs-kata.git
cd jcs-kata/helloworld-springboot-docker

# Make sure the app is not available. The following two commands should return an error
curl http://localhost:8080
curl http://localhost:8080/hello

# Run the Application. Make sure 8080 port is not already in used
java -jar helloworld-springboot-docker-1.0.jar

# Access the application
# You should see a "App is up and running" message displayed.
curl http://localhost:8080
# You should see a "Hello, World!" message displayed.
curl http://localhost:8080/hello

#Kill the java process
#In Windows
tasklist /FI "IMAGENAME eq java*"
taskkill /F /PID pid_number

# Make sure the app is no longer available. The following two commands should return an error
curl http://localhost:8080
curl http://localhost:8080/hello

```

## Usage

```shell
#Make sure Docker is up and running
docker --help

# Make sure the app is not available. The following command should return an error
curl http://localhost:8080

#Build Docker image
docker build --tag=helloworld-springboot-docker:latest .

#Check that the Docker image has been built ok
docker inspect helloworld-springboot-docker

#Create and Run the container
docker create -p 8080:8080 --name hwsbd helloworld-springboot-docker
docker start hwsbd
curl http://localhost:8080
docker stop hwsbd

#Make sure the hello world app is no longer running, this command should return a 200 success
curl http://localhost:8080
```

### Further exploration

```shell
#docker run will Create and Run a container from an image
docker run -p 8080:8080 --name hwsbd helloworld-springboot-docker:latest
```

### Useful Docker commands

```shell
#List all images
docker image ls
#Remove one image
docker image rm [image_name]
#Remove ALL images
docker image rm $(docker images -a -q)

#List all containers
docker ps -a
#Remove one container
docker rm [container_name]
```

## License

[MIT](https://choosealicense.com/licenses/mit/)