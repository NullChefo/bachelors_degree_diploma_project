# Diploma-Project

Microservices and Angular

User service dependencies: https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.4&packaging=jar&jvmVersion=19&groupId=com.stefan&artifactId=user-service&name=user-service&description=User%20service&packageName=com.stefan.user-service&dependencies=native,web,prometheus,lombok,actuator,data-jpa,mysql,cloud-starter-zipkin,cloud-starter-sleuth,amqp,validation,webflux





			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.10.1</version>
				<configuration>
					<source>19</source>
					<target>19</target>
					<release>19</release>
					<compilerArgs>
						--enable-preview
					</compilerArgs>
				</configuration>
			</plugin>



			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<version>3.0.0-M7</version>
				<configuration>
					<argLine>--enable-preview</argLine>
				</configuration>
			</plugin>





#Build native

./mvnw -Pnative -DskipTests clean package 

target/{application-name}



