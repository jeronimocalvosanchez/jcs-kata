FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY helloworld-springboot-web-1.0.jar service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/service.jar"]