#!/bin/bash

serviceName="mail-process-service"

docker rm -f ${serviceName}

./mvn clean

./mvnw compile jib:build

date
