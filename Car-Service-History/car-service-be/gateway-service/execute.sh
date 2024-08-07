#!/bin/bash

serviceName="gateway-service"

docker rm -f "$(serviceName)"

./mvn clean

# Build .jar
./mvnw package

file="$(pwd)/target/$serviceName-0.0.1-SNAPSHOT.jar"

if [ -f "$file" ]
  then

#   docker buildx build --platform linux/arm64 -t $serviceName:jre-slim-latest -f Dockerfile .
    docker build -t $serviceName:jre-slim-latest -f Dockerfile .

    docker tag $serviceName:jre-slim-latest docker.io/nullchefo/$serviceName:latest

    docker push docker.io/nullchefo/$serviceName:latest

  fi
date

