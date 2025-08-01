#!/bin/sh

# Запускаем каждый сервис в фоне
java -jar credentials-service.jar &
java -jar activepermission-service.jar &
java -jar token-service.jar &
java -jar auth-gateway.jar &
java -jar profile-service.jar &
java -jar variation-service.jar &
java -jar userinformation-service.jar &
java -jar lpfcp-endpoint.jar &
java -jar rest-endpoint.jar &

# Ожидаем завершения
wait