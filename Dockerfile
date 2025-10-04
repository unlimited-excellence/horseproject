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

# Generic env (do NOT bake secrets here)
ENV ENVIRONMENT=production
# Leave DB env empty here; they come from Cloud Run deploy flags/secrets
ENV POSTGRESQL_URI=""
ENV POSTGRESQL_USERNAME=""
ENV POSTGRESQL_PASSWORD=""

# Expose ports (optional, for local run / docs)
EXPOSE 8080
EXPOSE 50008

# Run the service and honor Cloud Run's PORT env
# Ktor will listen on -Dktor.deployment.port; Cloud Run injects PORT.
ENTRYPOINT ["sh","-c","java -Dktor.deployment.port=${PORT:-8080} -Dktor.deployment.host=0.0.0.0 -jar main-service.jar"]
