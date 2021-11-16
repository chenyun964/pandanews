# CS203 Pandanews

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/sg/java/technologies/javase-jdk11-downloads.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally
After you successfully clone this project into your local machine, from the root folder, navigate to
```sh
cd ./backends/pandanews
```

### Application Configurations
Next, open application.properties and follow the examples/instructions given in application.properties.example.

Make sure to fill up the given {fields} according to your organisation's needs.

For Springboot application, you can place your application.properties in other locations. 
If you are not placing application.properties in `./backends/pandanews/src/main/resources`, for later parts there will be a slight difference in setup.

### Load Maven Dependencies
When you successfully clone this project into your local machine. Make sure to get all your libraries to download through this command.
For Ubuntu users:
```sh
mvn package -DskipTests
```
For OSX users:
```sh
./mvnw package -DskipTests
```
`-DskipTests` here is to skip the testing process since it is not used in this project.

However, if you are using IntelliJ IDEA, you can simply open your project using IntelliJ and head to pom.xml. There is a small pop up on the right of the window. You can click the button with the letter `M` to load your maven dependencies.

### Run Spring Boot
There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `sg.edu.smu.cs203.pandanews` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

For users with the maven plugin:
```sh
mvn spring-boot:run
```
For OSX users:
```sh
./mvnw spring-boot:run
```

If you are placing your application.properties in other location, you need to run this application in the command line:

For users with the maven plugin:
```sh
mvn spring-boot:run -Dspring.config.location="{absolute filepath of application.properties}"
```
For OSX users:
```sh
./mvnw spring-boot:run -Dspring.config.location="{absolute filepath of application.properties}"
```

Furthermore, you can run this project using Java Archive (.jar) file.

For users with the maven plugin:
```sh
mvn clean install
```
For OSX users:
```sh
./mvnw clean install
```

After that, you can run this project using `java -jar`
```sh
java -jar {projectName}.jar
```

If you are placing your application.properties in other location, you need to run this application in the command line:
```sh
java -jar {projectName}.jar --spring.config.location={absolute filepath of application.properties}
```
We can also pass a folder location where the application will search for the file instead:
```sh
java -jar {projectName}.jar --spring.config.name={name of application.properties} --spring.config.location={absolute filepath of application.properties}
```
