# Stage 1: Build
FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src ./src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# Stage 2: Runtime
FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
