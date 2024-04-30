# Hello World - Spring Boot - Docker Compose

![Static Badge](https://img.shields.io/badge/java-17-blue)
![Static Badge](https://img.shields.io/badge/spring_boot-3.2.4-blue)
![Static Badge](https://img.shields.io/badge/nginx-1.25.5-blue)
![Static Badge](https://img.shields.io/badge/docker-26.0.0-blue)
![Static Badge](https://img.shields.io/badge/license-mit-green)

The goal of this exercise is to learn how to we can use docker compose to deploy several containers.

To do so,
- we will use a binary file (java with spring boot) that exposes 3 endpoints
- we will create a Docker Image from the binary
- we will use and configure a NGINX docker image that will expose port 80 and will redirect hello world requests
- finally, we will get them started and access the application through NGINX

## Pre requirements in our development machine
- **GIT**
- **Java 17**
- **NGINX**
- **Docker**: [How to install Docker](https://docs.docker.com/get-docker/)
  
## Environment preparation - Hello World application

First, we need to make sure our JAR application is working as expected.
- http://localhost:8080
- http://localhost:8080/hello
- http://localhost:8080/bye

```shell
# Clone the Repository
git clone https://github.com/jeronimocalvosanchez/jcs-kata.git
cd jcs-kata/helloworld-nignx

#Make sure Docker and Docker Compose is up and running
docker version

# Make sure the app is not available. The following commands should return an error
curl http://localhost:8080/hello

# Run the Application. Make sure 8080 port is not already in used
docker build --file Dockerfile-hw-sw-8080 --tag=hw-sb-8080:latest .
docker create -p 8080:8080 --name hwsbd-8080 hw-sb-8080:latest
docker start hwsbd-8080

# Access the application
# You should see a "App is up and running" message displayed.
curl http://localhost:8080/hello

#Stop the hello world application
docker stop hwsbd-8080

# Make sure the app is no longer available. The following commands should return an error
curl http://localhost:8080/hello
```

## Hello World - Nginx

```shell
# Make sure the hello world app is running.
docker start hwsbd-8080
curl http://localhost:8080/hello

# But we are not listening in port 80
curl http://localhost/helloWorld

# Build Docker image
docker build --file Dockerfile-hw-nginx --tag=hw-nginx:latest .
docker create -p 80:80 --name hwnginx hw-nginx:latest
docker start hwnginx

# Access the application through 80 port
curl http://localhost/helloWorld

#Stop the hello world application
docker stop hwnginx
docker stop hwsbd-8080

# Make sure the hello world app is no longer running.
curl http://localhost:8080/hello
curl http://localhost/helloWorld
```

## How does this work? 

### nginx.conf 

```shell
http {
    upstream hwsb8080 {
        server 172.17.0.1:8080;
    }

    server {
        listen 80;

        location /helloWorld {
            proxy_pass http://hwsb8080/hello;
        }

        location / {
            return 404;
        }
    }
}

```

Define upstream server. 
- Note that this is optional, you can use 172.17.0.1:8080 in the proxy_pass below
- Give the upstream server a name, in this example hwsb8080.
- Give the upstream server an address, in this example 172.17.0.1:8080. 

Why not simply use **localhost**?

Since the nginx server will be running in the same physical machine than the hello world server, that could make sense.
However **localhost** is going to be interpreted for the nginx server as the nginx local container, not the physical machine where is running the nginx container.
Hence, we need to use <DOCKER_HOST_IP>.

To obtain this IP, we need to run the following docker command and look for the value of "Gateway" field under the "IPAM" section

```shell
docker network inspect bridge
```

### Nginx Dockerfile

Here we have the nginx Dockerfile to build the image

```shell
# Use the official nginx image as the base image
FROM nginx:1.25.5

# Copy the custom nginx configuration file
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

## License

[MIT](https://choosealicense.com/licenses/mit/)