#!/bin/bash

# remove the old image
docker rm -f user-service

# Clean e.k.e get them out
./mvn clean

# Build .jar    -Dmaven.test.skip=true
./mvnw package

# TODO make it actually better to check the next versions and to return error or something when not found
file="$(pwd)/target/user-service-0.0.1-SNAPSHOT.jar"

if [ -f "$file" ]
  then

# docker build -t user-service:jre-slim-latest -f Dockerfile .

# TODO docker buildx build --platform linux/amd64,linux/arm64 -t user-service:jre-slim-latest -f Dockerfile .
docker build -t user-service:jre-slim-latest -f Dockerfile .

# gets the image size
docker image ls | grep -e "user-service.*jre-slim"


docker tag user-service:jre-slim-latest docker.io/nullchefo/user-service:latest


docker push docker.io/nullchefo/user-service:latest

  fi
date

