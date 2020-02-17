FROM 192.168.173.7/openjdk:8-jdk-alpine

RUN mkdir -p /app && \
    export http_proxy="http://192.168.173.5:10809" && \
    export https_proxy="http://192.168.173.5:10809" && \
    sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && \
    chmod u+s /bin/busybox && \
    apk update && apk add curl busybox-extras && \
    apk add -U tzdata && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    unset http_proxy https_proxy

COPY ./target/devops-client-web-*.jar /app/client.jar
RUN  chown -R nobody:nobody /app

ARG git_hash
ENV git_version=$git_hash
ENV JAVA_OPTS=$JAVA_OPTS
EXPOSE 8080
USER nobody
WORKDIR /app

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","client.jar"]