#!/bin/bash
set -euo pipefail

echo "🚀 Створюємо та запускаємо mini-service..."

# 1. Створюємо структуру
rm -rf mini-service
mkdir -p mini-service/src/main/kotlin/com/example mini-service/src/main/resources
cd mini-service

# 2. settings.gradle.kts
cat > settings.gradle.kts <<'EOF'
rootProject.name = "mini-service"
EOF

# 3. build.gradle.kts з ShadowJar
cat > build.gradle.kts <<'EOF'
plugins {
    kotlin("jvm") version "2.0.21"
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    val ktor = "2.3.11"
    implementation("io.ktor:ktor-server-core-jvm:$ktor")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor")
    implementation("ch.qos.logback:logback-classic:1.5.8")
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("com.example.MainKt")
}

kotlin {
    jvmToolchain(17)
}
EOF

# 4. application.conf
cat > src/main/resources/application.conf <<'EOF'
ktor {
  deployment {
    port = 8080
  }
  application {
    modules = [ com.example.MainKt.module ]
  }
}
EOF

# 5. Main.kt
cat > src/main/kotlin/com/example/Main.kt <<'EOF'
package com.example

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.Serializable
import io.ktor.serialization.kotlinx.json.*

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) { json() }
    routing {
        get("/") { call.respond(mapOf("status" to "ok", "service" to "mini-service", "message" to "Hello from Mini Service! 🚀")) }
        get("/health") { call.respond(mapOf("ok" to true)) }
    }
}
EOF

# 6. Dockerfile
cat > Dockerfile <<'EOF'
FROM gradle:8.13-jdk17 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew shadowJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/mini-service-all.jar /app/mini-service-all.jar
EXPOSE 8080
CMD ["java", "-jar", "mini-service-all.jar"]
EOF

# 7. README.md
cat > README.md <<'EOF'
# mini-service (Ktor, Kotlin, JDK17)

Endpoints:
- GET /         -> {"status":"ok","service":"mini-service","message":"Hello from Mini Service! 🚀"}
- GET /health   -> {"ok":true}

## Local build & run
./gradlew shadowJar
java -jar build/libs/mini-service-all.jar

## Docker
docker build -t mini-service .
docker run --rm -p 8080:8080 mini-service
EOF

# 8. Gradle Wrapper
gradle wrapper --gradle-version 8.13
chmod +x gradlew

# 9. Локальна збірка JAR
./gradlew shadowJar --no-daemon

# 10. Локальний запуск перевірки
echo "⚡ Локальний запуск JAR..."
java -jar build/libs/mini-service-all.jar &
APP_PID=$!
sleep 5
echo "➡ Перевіряю http://localhost:8080 ..."
curl -sf http://localhost:8080 || (echo "❌ Mini-service не стартував локально" && kill $APP_PID && exit 1)
kill $APP_PID
echo "✅ Локальний jar працює!"

# 11. Docker build & test
echo "🐳 Будую Docker-образ..."
docker build -t mini-service .
docker run -d --rm -p 8080:8080 --name mini-service mini-service
sleep 5
echo "➡ Перевіряю контейнер http://localhost:8080 ..."
curl -sf http://localhost:8080 || (echo "❌ Container не стартував" && docker logs mini-service && exit 1)
docker stop mini-service
echo "✅ Docker контейнер працює!"

echo "🎉 mini-service готовий!"
