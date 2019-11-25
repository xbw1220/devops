#! /bin/bash
sudo docker stack rm devops
sudo docker stack deploy --with-registry-auth -c devops-compose.yml devops