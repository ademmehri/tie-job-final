From eclipse-temurin:17.0.8.1_1-jdk-focal
WORKDIR /app
COPY  target/spring-boot-docker.jar spring-boot-docker.jar
EXPOSE 8080 
ENTRYPOINT [ "java","-jar","/spring-boot-docker.jar" ]