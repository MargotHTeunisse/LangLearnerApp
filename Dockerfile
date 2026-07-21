FROM maven:3.9.16-eclipse-temurin-26 AS build
WORKDIR /app
COPY /pom.xml .
COPY console-app/pom.xml ./console-app/pom.xml
COPY core/pom.xml ./core/pom.xml
COPY web-services/pom.xml ./web-services/pom.xml
COPY web-app/pom.xml ./web-app/pom.xml

RUN mvn dependency:go-offline -B

COPY console-app/src ./console-app/src
COPY core/src ./core/src
COPY web-services/src ./web-services/src
COPY web-app/src ./web-app/src

RUN mvn package -DskipTests

FROM eclipse-temurin:26-jre
WORKDIR /app
COPY --from=build /app/web-app/target/*.jar langlearner-web-app.jar
ENTRYPOINT ["java","-jar","langlearner-web-app.jar"]
EXPOSE 8080