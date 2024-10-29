# Stage 1: Build
FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY cred.json /app/cred.json
COPY src ./src
COPY .env .env
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# Stage 2: Runtime
FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
COPY cred.json /app/cred.json
COPY .env .env
RUN export $(cat .env | xargs)
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
