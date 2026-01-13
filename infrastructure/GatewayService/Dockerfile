FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY infrastructure/GatewayService/pom.xml .
RUN mvn dependency:go-offline

COPY infrastructure/GatewayService/src src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
