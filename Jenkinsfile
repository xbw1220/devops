pipeline {
  agent {
    docker {
      args '''-v /root/.m2:/root/.m2
-v /var/run/docker.sock:/var/run/docker.sock'''
      image '192.168.173.7/maven-docker:3.6.3-jdk-8'
    }

  }
  stages {
    stage('maven build') {
      steps {
        echo 'maven build jar'
        sh 'mvn clean install -q -e -am -B -Dmaven.test.skip=true -pl devops-server/devops-server-api,devops-client/devops-client-api'
        sh 'mvn clean package -q -e -B -Dmaven.test.skip=true -pl devops-server/devops-server-web,devops-client/devops-client-web'
      }
    }

    stage('docker build') {
      steps {
        echo 'docker build image client'
        dir(path: 'devops-client/devops-client-web') {
          sh 'pwd'
          sh '''\\cp xbw/Dockerfile .
docker build -t ${REGISTRY}/client \\
  --build-arg git_hash=client_`git rev-parse HEAD` .'''
          sh '''echo "${password}" | docker login \\
  --username=${user} --password-stdin ${REGISTRY}'''
          sh '''docker push ${REGISTRY}/client
docker logout
docker rmi ${REGISTRY}/client'''
        }

        echo 'docker build image server'
        dir(path: 'devops-server/devops-server-web') {
          sh 'pwd'
          sh '''cp xbw/Dockerfile .
docker build -t ${REGISTRY}/server \\
  --build-arg git_hash=server_`git rev-parse HEAD` .'''
          sh '''echo "${password}" | docker login \\
  --username=${user} --password-stdin ${REGISTRY}'''
          sh '''docker push ${REGISTRY}/server
docker logout
docker rmi ${REGISTRY}/server'''
        }

      }
    }

    stage('master clean image') {
      steps {
        echo 'clean images'
        sh 'docker image prune -f'
      }
    }

  }
  environment {
    REGISTRY = '192.168.173.7:5001'
    user = 'admin'
    password = 'admin123'
  }
}