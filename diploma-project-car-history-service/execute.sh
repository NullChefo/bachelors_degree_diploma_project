#!/bin/bash

serviceName="car-history-service"

docker rm -f ${serviceName}

./mvn clean

./mvnw compile jib:build

date
