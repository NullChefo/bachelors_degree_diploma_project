#!/bin/bash

serviceName="authorization-service"

docker rm -f ${serviceName}

./mvn clean

./mvnw compile jib:build

date
