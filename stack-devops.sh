#! /bin/bash
docker stack rm devops
docker stack deploy --with-registry-auth -c devops-compose.yml devops