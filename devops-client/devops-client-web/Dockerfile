FROM  openjdk:8-jdk-alpine

MAINTAINER hchkang8710@gmail.com

RUN mkdir -p /data/client

WORKDIR /data/client

ADD ./target/devops-client-web-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]