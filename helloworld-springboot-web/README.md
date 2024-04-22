# Hello World - Spring Boot Web

![Static Badge](https://img.shields.io/badge/maven-3.9-blue)
![Static Badge](https://img.shields.io/badge/java-17-blue)
![Static Badge](https://img.shields.io/badge/spring_boot-3.2.4-blue)
![Static Badge](https://img.shields.io/badge/license-mit-green)

The goal of this exercise is to have up and running a web app using Spring Boot Web, with a simple hello world response

## Technologies used
- **GIT**
- **Maven 3.9**
- **Java 17**
- **Spring Boot 3.2.4**: **web** and **test** modules

## Usage

```shell
# Clone the Repository and navigate to the project folder
git clone https://github.com/jeronimocalvosanchez/jcs-kata.git
cd jcs-kata/helloworld-springboot-web

# Build the Project, 3 tests should pass
mvn clean test

#Check that the endpoints are not accessible
curl http://localhost:8080
curl http://localhost:8080/hello
curl http://localhost:8080/bye

# Run the Application. Make sure 8080 port was not already in used
mvn spring-boot:start

# Now the endpoints should be accessible
# You should see a "App is up and running" message displayed.
curl http://localhost:8080
# You should see a "Hello, World!" message displayed.
curl http://localhost:8080/hello
# You should see a "Vye, World!" message displayed.
curl http://localhost:8080/bye

# Stop the Application
mvn spring-boot:stop
```

## How to

### Add Spring Boot Dependencies

First thing we need is to create a `pom.xml` file in the project root folder.

In the `pom.xml`, add the name and version of your project. You can also specify your project properties
such as Java version used in your sources and target, and

```xml
    <groupId>com.htic.kata</groupId>
    <artifactId>helloworld-springboot-web</artifactId>
    <version>1.0</version>
 ```

By default, maven 3.9 will use Java version 17 even if we are using JDK 22, but if we want to be sure to set Java version to 17, we need to use `<maven.compiler.release>` if we are using maven 3.6 or later, or `<maven.compiler.release>` and `<maven.compiler.release>` for earlier maven versions
    
```xml
    <properties>
        <maven.compiler.release>17</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
```

Now we add `spring-boot-starter-parent` as your project parent in the `pom.xml`. Be aware that Spring Boot 3.x is no longer compatible with Java 8.

```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.4</version>
        <relativePath/>
    </parent>
```

And also the Spring Boot Dependencies that we are going to use, in our case `web` and `test`

```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

Add Maven support for your Spring Boot project

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

### Add your tests - Spring Context Loads

To make sure Spring Boot is properly configured, we can create one simple Integration Test just by annotating one class with `@SpringBootTest` and an empty function annotated with `@Test`. When this test is run, Spring will load the context. 

```java
package com.htic.kata.helloworld.hwspringbootweb;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloWorldSpringBootWebApplicationTest {

	@Test
	public void contextLoads() { }

}
```

### Add your tests - Controller

Now we want to test our controller, that will return a Hello World! message
-`@SpringBootTest` will load Spring Context
-`@AutoConfigureMockMvc` will add a mock server automatically configured and ready to use
-`@Autowired private MockMvc mvc` will inject our mocked server bean into this variable.
- Each function annotated with `@Test` will be a test
- In `getIndex()` test
  - `mvc.perform` will make one HTTP request, using GET method to`http://localhost:8080`
  - Then the test expects the response to be status `200 OK` and its content to be `"App is up and running"`
- In `getHello()` test
    - `mvc.perform` will make one HTTP request, using GET method to`http://localhost:8080/hello`
    - Then the test expects the response to be status `200 OK` and its content to be `"Hello, World!"`

Note that we are using three endpoints just to illustrate how URL mapping will work with Spring Boot Web
* `"/"` will be mapped to `http://localhost:8080` since `8080` is the default port
* `"/hello"` will be mapped to `http://localhost:8080/hello`
* `"/bye"` will be mapped to `http://localhost:8080/bye`

```java
package com.htic.kata.helloworld.hwspringbootweb.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

	@Autowired
	private MockMvc mvc;

    @Test
    public void testIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().string(equalTo("App is up and running")));
    }
    @Test
    public void testHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().string(equalTo("Hello, World!")));
    }
    @Test
    public void testBye() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/bye").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().string(equalTo("Bye, World!")));
    }
}
```

This test should compile, since all the required dependencies have been already added in our `pom.xml` but when running this test, it should fail since we have not implemented our controller yet.

### Add Our Controller

Now we add the controller that will make the previous test pass.
* We annotate our class with `@RestController` so that Spring can create a bean and add it to its context.
* We annotate our functions with `@GetMapping` so that Spring can map the right URL to the function  expecting HTTP.GET.
* In this simple example, we simply return the expected `String` in each function.

```java
package com.htic.kata.helloworld.hwspringbootweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/")
	public String index() {
		return "App is up and running";
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello, World!";
	}

    @GetMapping("/bye")
    public String bye() {
        return "Bye, World!";
    }
}
```

Now when running the previous test file `HelloControllerTest`, it should pass.

## Running the application

We can check that the application is not started

```shell
#Expect an error
curl http://localhost:8080
curl http://localhost:8080/hello
curl http://localhost:8080/bye
```

Since we added spring boot support in our `pom.xml`, we can now simply run the application with the maven command `springboot:start`

```shell
mvn spring-boot:start
```

Now the application should be up and run. We can test it

```shell
#Expect it works
curl http://localhost:8080
curl http://localhost:8080/hello
curl http://localhost:8080/bye
```

### Stopping the application

We can stop the application simply using the following command

```shell
mvn spring-boot:stop
``` 

Now the application should not respond

```shell
#Expect an error
curl http://localhost:8080
curl http://localhost:8080/hello
curl http://localhost:8080/bye
```

## License

[MIT](https://choosealicense.com/licenses/mit/)