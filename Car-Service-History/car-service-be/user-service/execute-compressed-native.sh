#!/bin/bash

serviceName="user-service"


docker rm -f $serviceName

# Clean e.k.e get them out
./mvn clean

docker rm -f "$(serviceName)"


   docker build -t $serviceName:jre-slim-latest -f Dockerfile-compressed-native .

  # TODO change the name
   docker tag $serviceName:jre-slim-latest docker.io/nullchefo/$serviceName:latest

  # TODO change the name
   docker push docker.io/nullchefo/$serviceName:latest

  # gets the image size
  docker image ls | grep -e "$serviceName.*jre-slim"


date

