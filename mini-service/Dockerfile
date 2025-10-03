FROM gradle:8.13-jdk17 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew shadowJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/mini-service-all.jar /app/mini-service-all.jar
EXPOSE 8080
CMD ["java", "-jar", "mini-service-all.jar"]
