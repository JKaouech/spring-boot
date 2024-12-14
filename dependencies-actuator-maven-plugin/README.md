# create maven plugin

## Article
[Create-maven-plugin](https://www.ji-ka.tn/create-maven-plugin/)


## How it works

1- Add actuator dependency 

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

2- Add plugin

```xml
<plugin>
	<groupId>com.jika.plugin</groupId>
	<artifactId>dependencies-actuator-maven-plugin</artifactId>
	<version>1.0.0</version>
	<executions>
		<execution>
			<goals>
				<goal>generate</goal>
			</goals>
			<configuration>
				<apiPackage>com.jik.rest</apiPackage>
			</configuration>
		</execution>
	</executions>
</plugin>
```

3- Update <code>apiPackage</code> with rest controller package or main package

4- Start your application

5- Go to {application_address}/actuator/dependencies


