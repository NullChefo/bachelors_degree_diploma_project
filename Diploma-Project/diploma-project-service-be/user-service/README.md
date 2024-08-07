## Build docker native image
./mvnw spring-boot:build-image -P native,nativeTest


Create OCI images just like you would with paketobuildpacks/builder:tiny:

./mvnw -Pnative -Dmaven.test.skip=true spring-boot:build-image



# For github

Native images:
https://tafadzwalnyamukapa.medium.com/spring-boot-3-spring-native-graalvm-with-kubernetes-istio-c26687185366

./mvnw spring-boot:build-image
docker run --rm -p 8080:8080 spring-native:latest
