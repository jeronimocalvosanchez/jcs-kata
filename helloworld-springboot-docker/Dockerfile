FROM eclipse-temurin:22-jdk-alpine
VOLUME /tmp
COPY target/helloworld-springboot-docker*.jar service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/service.jar"]