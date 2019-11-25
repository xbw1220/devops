#! /bin/bash
sudo docker stack rm devops
sleep 5
sudo docker stack deploy --with-registry-auth -c devops-compose.yml devops