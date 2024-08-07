#!/bin/bash

serviceName="user-service"

docker rm -f ${serviceName}

# Clean e.k.e get them out
./mvn clean

./mvnw compile jib:build

date
