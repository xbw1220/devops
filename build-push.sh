#! /bin/bash
/usr/local/maven/bin/mvn clean install -e -am -B -Dmaven.test.skip=true -pl devops-server/devops-server-api,devops-client/devops-client-api
/usr/local/maven/bin/mvn clean package -e -B -Dmaven.test.skip=true -pl devops-server/devops-server-web,devops-client/devops-client-web,sentinel-cluster-server
docker rmi -f 192.168.173.91:8082/repository/container/server:latest
docker rmi -f 192.168.173.91:8082/repository/container/client:latest
docker rmi -f 192.168.173.91:8082/repository/container/sentinel-cluster-server:latest
cd ~/IdeaProjects/devops/devops-server/devops-server-web && docker build -t 192.168.173.91:8082/repository/container/server .
cd ~/IdeaProjects/devops/devops-client/devops-client-web && docker build -t 192.168.173.91:8082/repository/container/client .
cd ~/IdeaProjects/devops/sentinel-cluster-server && docker build -t 192.168.173.91:8082/repository/container/sentinel-cluster-server .
docker login http://192.168.173.91:8082
docker push 192.168.173.91:8082/repository/container/server:latest
docker push 192.168.173.91:8082/repository/container/client:latest
docker push 192.168.173.91:8082/repository/container/sentinel-cluster-server:latest