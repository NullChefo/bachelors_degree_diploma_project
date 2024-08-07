#!/bin/bash

serviceName="gateway-service"

docker rm -f ${serviceName}

./mvn clean

./mvnw compile jib:build

date
