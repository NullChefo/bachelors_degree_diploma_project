#!/bin/bash

serviceName="mail-send-service"

docker rm -f ${serviceName}

./mvn clean

./mvnw compile jib:build

date

