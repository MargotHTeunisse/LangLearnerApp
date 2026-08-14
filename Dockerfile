FROM maven:3.9.16-eclipse-temurin-26 AS build
WORKDIR /app
COPY /pom.xml .
COPY console-app/pom.xml ./console-app/pom.xml
COPY core/pom.xml ./core/pom.xml
COPY web-services/pom.xml ./web-services/pom.xml

RUN mvn dependency:go-offline -B

COPY core/src ./core/src
COPY web-services/src ./web-services/src

RUN mvn -pl core,web-services -am package -DskipTests

FROM eclipse-temurin:26-jre
WORKDIR /app
COPY --from=build /app/web-services/target/*.jar langlearner-web-showcase.jar
ENTRYPOINT ["java","-jar","langlearner-web-showcase.jar"]
EXPOSE 8080