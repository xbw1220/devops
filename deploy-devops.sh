docker rmi -f 192.168.173.91:8082/repository/container/client:latest
docker build -t 192.168.173.91:8082/repository/container/client:latest /home/vin/IdeaProjects/devops/devops-client/devops-client-web
docker push 192.168.173.91:8082/repository/container/client:latest
docker rmi -f 192.168.173.91:8082/repository/container/server:latest
docker build -t 192.168.173.91:8082/repository/container/server:latest /home/vin/IdeaProjects/devops/devops-server/devops-server-web
docker push 192.168.173.91:8082/repository/container/server:latest
docker stack rm devops
docker stack deploy --with-registry-auth -c docker-compose.yml devops
