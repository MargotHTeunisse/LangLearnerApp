FROM eclipse-temurin:26-jdk
COPY web-app/target/web-app-1.0-SNAPSHOT.jar langlearner-web-app-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/langlearner-web-app-1.0-SNAPSHOT.jar"]
EXPOSE 8080