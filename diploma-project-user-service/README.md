## Build docker native image

./mvnw spring-boot:build-image -P native,nativeTest

Create OCI images just like you would with paketobuildpacks/builder:tiny:

./mvnw -Pnative -Dmaven.test.skip=true spring-boot:build-image

# For github

Native images:
https://tafadzwalnyamukapa.medium.com/spring-boot-3-spring-native-graalvm-with-kubernetes-istio-c26687185366

./mvnw spring-boot:build-image
docker run --rm -p 8080:8080 spring-native:latest

			<plugin>
				<groupId>org.graalvm.buildtools</groupId>
				<artifactId>native-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>

					<imageName>ghcr.io/nullchefo/diploma-project-${project.artifactId}:${project.version}</imageName>
					<image>
						<builder>dashaun/builder:tiny</builder>
						<env>
							<BP_NATIVE_IMAGE>true</BP_NATIVE_IMAGE>
						</env>
					</image>
					<excludes>
						<exclude>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
						</exclude>
					</excludes>
				</configuration>
			</plugin>
