# Hello World - Spring Boot - Docker Compose

![Static Badge](https://img.shields.io/badge/java-17-blue)
![Static Badge](https://img.shields.io/badge/spring_boot-3.2.4-blue)
![Static Badge](https://img.shields.io/badge/nginx-1.25.5-blue)
![Static Badge](https://img.shields.io/badge/docker-26.0.0-blue)
![Static Badge](https://img.shields.io/badge/docker--compose-2.26.1-blue)
![Static Badge](https://img.shields.io/badge/license-mit-green)

The goal of this exercise is to learn how we can use **Docker Compose** to deploy several containers grouped in a project.

To do so,
- we will use a binary file (java with spring boot) that exposes 3 endpoints
- we will create two Docker Images from the binary, just to play around with the ports we use
- we will create a Docker Container from each Image
- we will configure and use a Docker Container with NGINX that will expose port 80 and will redirect requests to each Container
- finally, we will get them started with Docker Compose

## Pre requirements in our development machine
- **GIT**
- **Java 17**
- **NGINX 1.25.5**
- **Docker**: [How to install Docker](https://docs.docker.com/get-docker/)
- **Docker Compose**: [How to install Docker Compose](https://docs.docker.com/compose/install/#install-compose)
  
## Environment preparation

First, we need to make sure our JAR application is working as expected.
- http://localhost:8080
- http://localhost:8080/hello
- http://localhost:8080/bye

```shell
# Clone the Repository
git clone https://github.com/jeronimocalvosanchez/jcs-kata.git
cd jcs-kata/helloworld-springboot-dockercompose

# Make sure the app is not available. The following commands should return an error
curl http://localhost:8080
curl http://localhost:8080/hello
curl http://localhost:8080/bye

# Run the Application. Make sure 8080 port is not already in used
java -jar helloworld-springboot-docker-1.0.jar

# Access the application
# You should see a "App is up and running" message displayed.
curl http://localhost:8080
# You should see a "Hello, World!" message displayed.
curl http://localhost:8080/hello
# You should see a "Bye, World!" message displayed.
curl http://localhost:8080/bye

#Kill the java process
#In Windows
tasklist /FI "IMAGENAME eq java*"
taskkill /F /PID pid_number

# Make sure the app is no longer available. The following commands should return an error
curl http://localhost:8080
curl http://localhost:8080/hello
curl http://localhost:8080/bye
```

## Basic Usage

```shell
# Make sure the hello world app is not running.
# These commands should return a 404 error
curl http://localhost:8085
curl http://localhost:8090
#Make sure Docker and Docker Compose is up and running
docker version
docer-compose version

# Start the containers
# If the containers and images would not exist, this command also create them
# grouping them in the hello-world-docker-compose project  
docker-compose --project-name hello-world-docker-compose up -d

# Make sure the hello world app is running in the 80 port
# These commands should return a 200 success
curl http://localhost/helloWorld
curl http://localhost/byeWorld
```

### Further exploration

```shell
# Docker Compose create will create the images and containers
# Also, will group the containers in a project named hello-world-docker-compose
 docker-compose --project-name hello-world-docker-compose create

```

## How this works

The file docker-compose.yml defines the following services:

- **hw-sb-8085-service**: A simple "Hello World" service (see Dockerfile-hwsb) running on container port 8080 but exposed on port 8085
- **hw-sb-8090-service**: A simple "Hello World" service (see Dockerfile-hwsb) running on container port 8080 but exposed on port 8090
- **nginx**: Nginx reverse proxy server configured (see ./nginx.conf) to route traffic to the two "Hello World" services.

```yml
services:
  hw-sb-8085-service:
    container_name: hwsb-8085
    build:
      context: .
      dockerfile: Dockerfile-hwsb
    ports:
      - "8085:8080"
    image: hw-sb-8085
  hw-sb-8090-service:
    container_name: hwsb-8090
    build:
      context: .
      dockerfile: Dockerfile-hwsb
    ports:
      - "8090:8080"
    image: hw-sb-8090

  nginx:
    image: nginx
    container_name: nginx
    ports:
      - "80:80"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf:ro
    depends_on:
      - hw-sb-8085-service
      - hw-sb-8090-service
```

Note that the nginx service is defined without using a Dockerfile.

## License

[MIT](https://choosealicense.com/licenses/mit/)