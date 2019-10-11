FROM  openjdk:8-jdk-alpine

MAINTAINER hchkang8710@gmail.com

RUN mkdir -p /data/server

WORKDIR /data/server

ADD ./target/devops-server-web-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]