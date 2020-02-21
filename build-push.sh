#! /bin/bash
/usr/local/maven/bin/mvn clean install -e -am -B -Dmaven.test.skip=true -pl devops-server/devops-server-api
/usr/local/maven/bin/mvn clean package -e -B -Dmaven.test.skip=true -pl devops-server/devops-server-web,devops-client/devops-client-web,devops-gateway
docker rmi -f 192.168.173.91:8082/repository/container/server:latest
docker rmi -f 192.168.173.91:8082/repository/container/client:latest
docker rmi -f 192.168.173.91:8082/repository/container/gateway:latest
cd ~/IdeaProjects/devops/devops-server/devops-server-web && docker build -t 192.168.173.91:8082/repository/container/server .
cd ~/IdeaProjects/devops/devops-client/devops-client-web && docker build -t 192.168.173.91:8082/repository/container/client .
cd ~/IdeaProjects/devops/devops-gateway && docker build -t 192.168.173.91:8082/repository/container/gateway .
docker login http://192.168.173.91:8082
docker push 192.168.173.91:8082/repository/container/server:latest
docker push 192.168.173.91:8082/repository/container/client:latest
docker push 192.168.173.91:8082/repository/container/gateway:latest