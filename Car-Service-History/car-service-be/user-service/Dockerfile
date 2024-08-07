ARG NAME=user-service-0.0.1-SNAPSHOT.jar
ARG PORT=8082


FROM maven:3.8-eclipse-temurin-19 AS builder

# add pom.xml and source code
ADD ./pom.xml pom.xml


# package jar


RUN mvn dependency:go-offline -B

# copy other files
ADD ./src src/


# TODO remove skip test
RUN mvn -Dmaven.test.skip=true clean package



FROM azul/zulu-openjdk-alpine:19-latest as deps

ARG NAME

COPY --from=builder target/*.jar /app/app.jar


# Identify dependencies
# COPY ./target/*.jar  /app/app.jar

RUN mkdir /app/unpacked && \
    cd /app/unpacked && \
    unzip ../app.jar && \
    cd .. && \
    $JAVA_HOME/bin/jdeps \
    --ignore-missing-deps \
    --print-module-deps \
    -q \
    --recursive \
    --multi-release 19 \
    --class-path="./unpacked/BOOT-INF/lib/*" \
    --module-path="./unpacked/BOOT-INF/lib/*" \
    ./app.jar > /deps.info

# -------------------------------------------------------------

FROM azul/zulu-openjdk-alpine:19-latest as openjdk

# required for strip-debug to work
RUN apk add --no-cache binutils

# copy module dependencies info
COPY --from=deps /deps.info /deps.info

# Build small JRE image
RUN $JAVA_HOME/bin/jlink \
    --verbose \
    --add-modules $(cat /deps.info) \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /customjre

# main app image
FROM alpine:latest
ARG NAME
ARG PORT

ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# copy JRE from the base image
COPY --from=openjdk /customjre $JAVA_HOME

# Add app user
ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER

# Configure working directory
RUN mkdir /app && \
    chown -R $APPLICATION_USER /app

USER 1000
#COPY --chown=1000:1000 ./target/*.jar /app/app.jar
COPY --chown=1000:1000 --from=builder target/*.jar /app/app.jar

WORKDIR /app


# "-XX:+UnlockExperimentalVMOptions", "-XX:+UseZGC" makes java use ZGC
# if needed use         -Dspring.profiles.active=default \
# -XX:MinRAMPercentage=60.0 -XX:MaxRAMPercentage=90.0 \
# -XX:+UnlockExperimentalVMOptions \
# -XX:+UseZGC"
ENV _JAVA_OPTIONS "-Djava.security.egd=file:/dev/./urandom \
-Djava.awt.headless=true -Dfile.encoding=UTF-8 \
-Dspring.output.ansi.enabled=ALWAYS \
-Djdk.tls.client.protocols=TLSv1.2"
 #For kubernetes -Djdk.tls.client.protocols=TLSv1.2"

EXPOSE ${PORT}
ENTRYPOINT [ "/jre/bin/java", "-Xmx2G", "--enable-preview" ,"-jar", "/app/app.jar" ]
