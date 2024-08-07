#!/bin/bash

serviceName="auth-service"

docker rm -f ${serviceName}

./mvn clean

./mvnw compile jib:build

date
