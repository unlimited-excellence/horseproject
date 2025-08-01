## Stage 1: build all services
#FROM gradle:8.3-jdk17 AS builder
#WORKDIR /home/gradle/project
#COPY settings.gradle.kts settings.gradle.kts
## COPY gradle gradle
#COPY credentials-service credentials-service
#COPY activepermission-service activepermission-service
#COPY token-service token-service
#COPY auth-gateway auth-gateway
#COPY profile-service profile-service
#COPY variation-service variation-service
#COPY lpfcp-endpoint lpfcp-endpoint
#COPY rest-endpoint rest-endpoint
#
## Copy common gradle files and build
#RUN gradle clean assemble --no-daemon

# Stage 2: runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Копируем все собранные JAR-файлы
#COPY --from=builder /home/gradle/project/credentials-service/build/libs/credentials.jar ./credentials.jar
#COPY --from=builder /home/gradle/project/activepermission-service/build/libs/activepermission.jar ./activepermission.jar
#COPY --from=builder /home/gradle/project/token-service/build/libs/token.jar ./token.jar
#COPY --from=builder /home/gradle/project/auth-gateway/build/libs/auth.jar ./auth.jar
#COPY --from=builder /home/gradle/project/profile-service/build/libs/profile.jar ./profile.jar
#COPY --from=builder /home/gradle/project/variation-service/build/libs/variation.jar ./variation.jar
#COPY --from=builder /home/gradle/project/lpfcp-endpoint/build/libs/lpfcp.jar ./lpfcp.jar
#COPY --from=builder /home/gradle/project/rest-endpoint/build/libs/rest.jar ./rest.jar
COPY credentials-service/build/libs/credentials-service-all.jar ./credentials-service.jar
COPY activepermission-service/build/libs/activepermission-service-all.jar ./activepermission-service.jar
COPY token-service/build/libs/token-service-all.jar ./token-service.jar
COPY auth-gateway/build/libs/auth-gateway-all.jar ./auth-gateway.jar
COPY profile-service/build/libs/profile-service-all.jar ./profile-service.jar
COPY variation-service/build/libs/variation-service-all.jar ./variation-service.jar
COPY userinformation-service/build/libs/userinformation-service-all.jar ./userinformation-service.jar
COPY lpfcp-endpoint/build/libs/lpfcp-endpoint-all.jar ./lpfcp-endpoint.jar
COPY rest-endpoint/build/libs/rest-endpoint-all.jar ./rest-endpoint.jar

# Скрипт для старта всех сервисов
COPY entrypoint.sh ./entrypoint.sh
RUN chmod +x entrypoint.sh

ENV ENVIRONMENT=production
ENV POSTGRESQL_URI=jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:5432/postgres
ENV POSTGRESQL_USERNAME=postgres.ohyniytcnabvimajynqk
ENV POSTGRESQL_PASSWORD=okV8z6tFyPjmcRy4

ENV ACTIVEPERMISSION_SERVICE_URL=http://localhost:10001/lpfcp
ENV CREDENTIALS_SERVICE_URL=http://localhost:10000/lpfcp
ENV TOKEN_SERVICE_URL=http://localhost:10002/lpfcp
ENV AUTH_GATEWAY_URL=http://localhost:10003/lpfcp
ENV PROFILE_SERVICE_URL=http://localhost:10004/lpfcp
ENV VARIATION_SERVICE_URL=http://localhost:10005/lpfcp
ENV USERINFORMATION_SERVICE_URL=http://localhost:10006/lpfcp
ENV LPFCP_ENDPOINT_URL=http://localhost:50005/lpfcp
ENV REST_ENDPOINT_URL=http://localhost:8080

EXPOSE 50005
EXPOSE 8080

ENTRYPOINT ["./entrypoint.sh"]