# Stage 1: build the fat JAR using Gradle
FROM gradle:8.3-jdk17 AS builder
WORKDIR /home/gradle/project

# Copy only gradle files first (to leverage caching)
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle gradle

# Copy source
COPY core core
COPY shared shared
COPY main-service main-service

# Build shadow JAR
RUN gradle clean :main-service:shadowJar --no-daemon

# Stage 2: runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy built fat JAR
COPY --from=builder /home/gradle/project/main-service/build/libs/main-service-all.jar ./main-service.jar

# Generic env (do NOT hardcode secrets here)
ENV ENVIRONMENT=production
ENV POSTGRESQL_URI="jdbc:postgresql://10.3.0.3:5432/horse"
ENV POSTGRESQL_USERNAME="horseuser"
ENV POSTGRESQL_PASSWORD=""

# Expose ports
EXPOSE 8080
EXPOSE 50008

# Run the service
ENTRYPOINT ["java", "-jar", "main-service.jar"]
