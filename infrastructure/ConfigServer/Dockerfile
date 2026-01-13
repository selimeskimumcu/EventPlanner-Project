FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY infrastructure/ConfigServer/pom.xml .
RUN mvn dependency:go-offline

COPY infrastructure/ConfigServer/src src
COPY config-repo config-repo
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/config-repo /app/config-repo
ENV SPRING_CLOUD_CONFIG_SERVER_NATIVE_SEARCH_LOCATIONS=file:///app/config-repo
ENTRYPOINT ["java", "-jar", "app.jar"]
