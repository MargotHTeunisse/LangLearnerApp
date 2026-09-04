FROM maven:3.9.16-eclipse-temurin-26 AS build
WORKDIR /app
COPY /pom.xml .
COPY console-app/pom.xml ./console-app/pom.xml
COPY core/pom.xml ./core/pom.xml
COPY web-services/pom.xml ./web-services/pom.xml
COPY rest-services/pom.xml ./rest-services/pom.xml

RUN mvn dependency:go-offline -B

COPY core/src ./core/src
COPY rest-services/src ./rest-services/src

RUN mvn -pl core,rest-services -am package -DskipTests

FROM eclipse-temurin:26-jre
WORKDIR /app
COPY --from=build /app/rest-services/target/*.jar langlearner-web-showcase.jar

ENV SPRING_PROFILE h2
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=${SPRING_PROFILE}", "langlearner-web-showcase.jar"]
EXPOSE 8080