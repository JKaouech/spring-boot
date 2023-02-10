# Spring boot actuator

## 1- Actuator
------------

Actuator endpoints let you monitor and interact with your Spring Boot application.

### 1.1 Setup
By adding the following dependency to your project:

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
</dependencies>
```
the actuator endpoints will become available in your project. : http://localhost:8080/actuator

> Be aware that all actuators except for `/health` are disabled by default for security reasons. They might expose sensitive information. You can use the `management.endpoints.web.exposure.include` property to enable the actuators.

**To expose all actuator endpoint : **
```yaml
management:
  endpoints:
    web:
      exposure:
        include: '*'
```
**To expose only info actuator  endpoint : **
```yaml
management:
  endpoints:
    web:
      exposure:
        include: "info"
```
You can reach the info actuator on your local machine: [http://localhost:8080/actuator/info](http://localhost:8080/actuator/info "http://localhost:8080/actuator/info") once you start your Spring Boot application.

### 1.2 Spring Boot info actuator contributors

There are different info contributors that “contribute” to the information exposed by the actuator info endpoint:

* **build**
Enabled by default: `true`
Goal: Exposes build information.
Requires: you to generate build information
Configuration property: `management.info.build.enabled`

* **env**
Enabled by default: `false` (since Spring Boot 2.6. For the older Spring Boot version, this contributor is enabled by default!)
Goal: Exposes any property from the SpringEnvironment .
Required: to specify properties with names that start with info.*
in you application.yml or application.properties
Configuration property: `management.info.env.enabled`

* **git**
Enabled by default: `true`
Goal: exposes git information.
Requires: you to generate [git information](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.build.generate-git-info "git information")
Configuration property: `management.info.git.enabled`

* **java**
Enabled by default: `false`  (since Spring Boot 2.6)
Goal: exposes Java runtime information.
Configuration property: `management.info.java.enabled`

### 1.3 Static Properties in /info 
You can customize the environment info contributor by adding `info.*`properties to your application.yml or application.properties :

```yaml
management.info
  env.enabled: true

info:
  application:
    name: ${spring.application.name}
    description: spring actuator application
    version: '@project.version@'
```

### 1.4 GIT details in /info 
We can add git details using the respective Maven dependency:
```xml
<dependency>
    <groupId>pl.project13.maven</groupId>
    <artifactId>git-commit-id-plugin</artifactId>
</dependency>
```
and enable management.info.git in configuration property:
 ```yaml
management.info:
  git.enabled: true
```

### 1.5 GIT  build information details in /info

We can also include build information including name, group, and version using the Maven plugin:
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <executions>
        <execution>
            <goals>
                <goal>build-info</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
and enable management.info.build in configuration property:
 ```yaml
management.info:
  build.enabled: true
```

[========]

## 2- Startup Tracking
------------
Tracking the various steps during application startup can provide useful information that can help us to understand the time spent during various phases of application startup. Such instrumentation can also improve our understanding of the context lifecycle and the application startup sequence.

### 2.1 Setup
* Add the spring-boot-starter-actuator dependency to our POM

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
</dependencies>
```
* Expose the required endpoint over HTTP by setting the configuration property in our application properties file:
```yaml
management: 
  endpoints: 
    web: 
      exposure: 
        include: "startup"
```

### 2.2 Startup Endpoint
We need to set the application's startup configuration to an instance of `BufferingApplicationStartup`. This is an in-memory implementation of the `ApplicationStartup` interface provided by Spring Boot. It captures the events during Spring's startup process and stores them in an internal buffer.

*  Create a simple application with this implementation:
```java
@SpringBootApplication
public class SpringActuatorApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringActuatorApplication.class);
		app.setApplicationStartup(new BufferingApplicationStartup(2048));
		app.run(args);
	}
}
```

* Now, we can start our application and query the startup actuator endpoint.
[http://localhost:8080/actuator/startup](http://localhost:8080/actuator/startup "http://localhost:8080/actuator/startup")

> Be aware before spring boot version 2.6 startup endpoint only allow POST method call.

### 2.3 Filtering Startup Events
The buffering implementation has a fixed capacity for storing events in memory. Therefore, it might not be desirable to store a large number of events in the buffer.
We can filter the instrumented events and only store those that may be of interest to us:

```java
public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringActuatorApplication.class);
		BufferingApplicationStartup startup = new BufferingApplicationStartup(2048);
		startup.addFilter(startupStep -> startupStep.getName().matches("spring.boot.application.starting"));
		app.setApplicationStartup(startup);
		app.run(args);
	}
```
Here, we've used the addFilter method to only instrument steps with the specified name.



# REF

https://www.baeldung.com/spring-boot-actuators

https://www.baeldung.com/spring-boot-info-actuator-custom

https://medium.com/@TimvanBaarsen/help-my-spring-boot-info-actuator-endpoint-is-enabled-but-i-dont-see-any-environment-details-c2d41a7b24d7

https://www.baeldung.com/spring-boot-actuator-startup
https://medium.com/techwasti/startup-actuator-endpoint-spring-boot-54a8dd0a1fdb

