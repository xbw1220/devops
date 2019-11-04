/#! /bin/bash
docker stack rm nacos
docker stack rm devops
docker stack deploy --with-registry-auth -c nacos-compose.yml nacos
docker stack deploy --with-registry-auth -c devops-compose.yml devops