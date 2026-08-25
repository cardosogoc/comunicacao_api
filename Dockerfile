FROM eclipse-temurin:25-jdk-alpine AS build

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:25-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar /app/comunicacao_api.jar

EXPOSE 8085

CMD ["java", "-jar", "/app/comunicacao_api.jar"]