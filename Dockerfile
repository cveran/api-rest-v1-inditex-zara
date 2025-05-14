# Etapa 1: Build (compilar la app)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Dev (imagen para desarrollo
FROM eclipse-temurin:17-jdk-alpine AS dev
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

# Etapa 3: Prod (imagen final, optimizada y compacta)
FROM gcr.io/distroless/java17-debian11:nonroot AS prod
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
