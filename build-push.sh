#! /bin/bash
mvn clean package -e -B -Dmaven.test.skip=true -pl devops-server/devops-server-web,devops-client/devops-client-web
sudo docker rmi 192.168.173.91:8082/repository/container/server:latest
sudo docker rmi 192.168.173.91:8082/repository/container/client:latest
cd ~/IdeaProjects/devops/devops-server/devops-server-web && sudo docker build -t 192.168.173.91:8082/repository/container/server .
cd ~/IdeaProjects/devops/devops-client/devops-client-web && sudo docker build -t 192.168.173.91:8082/repository/container/client .
sudo docker login http://192.168.173.91:8082
sudo docker push 192.168.173.91:8082/repository/container/server:latest
sudo docker push 192.168.173.91:8082/repository/container/client:latest