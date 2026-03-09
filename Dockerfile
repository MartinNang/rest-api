FROM eclipse-temurin:25
ADD target/backend-0.0.1-SNAPSHOT.jar backend-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]