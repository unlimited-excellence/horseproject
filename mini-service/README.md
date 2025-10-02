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
