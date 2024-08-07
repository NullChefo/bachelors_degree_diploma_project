Starter:

https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.5&packaging=jar&jvmVersion=17&groupId=com.stefan&artifactId=car-history-service&name=car-history-service&description=Car%20history%20service&packageName=com.stefan.car-history-service&dependencies=native,lombok,web,validation,cloud-starter-zipkin,cloud-starter-sleuth,prometheus,actuator,webflux,amqp,mysql,data-jpa


./mvnw -Pnative -DskipTests clean package

./mvnw -Pnative clean package

sdk use java graalVM

native-image -jar target/user-service-0.0.1-SNAPSHOT.jar --static --libc=musl -o target/user-app
