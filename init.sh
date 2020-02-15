rm -rf ./nginx/logs
rm -rf ./standalone-logs
mkdir -p ./nginx/logs
mkdir -p ./standalone-logs
docker network remove devops_net
docker network create --subnet 10.0.9.0/24 --gateway 10.0.9.1 --scope swarm --driver overlay devops_net