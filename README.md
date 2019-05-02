# Stack Overflow API

Startup
-------

To generate bootable jar use ***./gradlew clean build*** command, after execution you can find jar into the ***build/libs*** folder and run it ***java -jar stackoverflowapi-0.0.1-SNAPSHOT.jar***, tomcat will be started on the port 8080 by default and api documentation will be generated and published ***http://localhost:8080/docs/index.html***

Links
-------

Web application link - ***http://localhost:8080/***

Search rest api link - ***http://localhost:8080/api/v1/search***

> [!NOTE]
If you start api with ***dev*** profile, additional actuator links will be exposed and you can see all application links using ***http://localhost:8080/actuator/mappings***