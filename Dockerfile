FROM gradle:8.5-jdk21 AS build

WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY domain/build.gradle domain/
COPY application/build.gradle application/
COPY infrastructure/build.gradle infrastructure/

COPY domain/src /app/domain/src
COPY application/src /app/application/src
COPY infrastructure/src /app/infrastructure/src

RUN gradle :infrastructure:bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/application.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]