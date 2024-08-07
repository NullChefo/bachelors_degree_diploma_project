#!/bin/bash

serviceName="car-history-service"

docker rm -f "$(serviceName)"

./mvn clean

 source DOCKER_BUILDKIT=1


   docker build -t $serviceName:jre-slim-latest -f Dockerfile-native-image-multistage .

 #   docker tag $serviceName:jre-slim-latest docker.io/nullchefo/$serviceName:latest

 #   docker push docker.io/nullchefo/$serviceName:latest


date

