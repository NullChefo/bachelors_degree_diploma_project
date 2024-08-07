Spring Dependencies:

https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.5&packaging=jar&jvmVersion=19&groupId=com.stefan&artifactId=user-service&name=user-service&description=User%20service&packageName=com.stefan.user-service&dependencies=native,lombok,web,validation,prometheus,actuator,webflux,amqp,mysql,data-jpa

Spring 3.0.0:

https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.0.0&packaging=jar&jvmVersion=19&groupId=com.stefan&artifactId=user-service&name=user-service&description=User%20service&packageName=com.stefan.user-service&dependencies=native,lombok,web,validation,prometheus,actuator,webflux,amqp,mysql,data-jpa,zipkin


$ ./mvnw -PnativeTest test
./mvnw -PnativeTest package 

-----------------------------------------------
                       Run

./mvnw -Dmaven.test.skip=true -Pnative native:compile


sdk use java graalVM-19-22.3


native-image -jar target/user-service-0.0.1-SNAPSHOT.jar --static --libc=musl -o target/user-app
