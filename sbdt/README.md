# Autoreload with Spring Boot & Dev tools + Gradle --continuous

To setup autoreload with Gradle, we need to use two different terminal window. One for running Gradle continous build, and second to run Spring Boot server.

1. In first window, start gradle build with continous flag:
    ```gradle build -continuous```

    This will ensure to that Gradle pick up any changes and re/compile class.
    
2. In second terminal start Spring Boot Server
    ```gradle bootRun```
    
   Spring Boot is started with _spring-boot-devtools_ on classpath, which will detect changes and restart application.
    
