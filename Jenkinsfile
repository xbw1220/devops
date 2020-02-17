pipeline {
  agent {
    docker {
      image '192.168.173.7/maven:3.6.3-jdk-8'
      args '-v /root/.m2:/root/.m2'
    }

  }
  stages {
    stage('maven build') {
      steps {
        echo 'maven install'
        sh 'mvn -q -e -B -U clean install'
        sh 'mvn -q -e -B -U clean package -Dmaven.test.skip=true -pl devops-server/devops-server-web,devops-client/devops-client-web'
      }
    }

    stage('docker build') {
      steps {
        echo 'docker build image client'
        dir(path: 'devops-client/devops-client-web') {
          sh 'pwd'
          sh '''docker build -t ${REGISTRY}/client \\
  --build-arg git_hash=client_`git rev-parse HEAD` .'''
          sh '''echo "${password}" | docker login \\
  --username=${user} --password-stdin ${REGISTRY}'''
          sh '''docker push ${REGISTRY}/client
docker logout'''
        }

        echo 'docker build image server'
        dir(path: 'devops-server/devops-server-web') {
          sh 'pwd'
          sh '''docker build -t ${REGISTRY}/server \\
  --build-arg git_hash=server_`git rev-parse HEAD` .'''
          sh '''echo "${password}" | docker login \\
  --username=${user} --password-stdin ${REGISTRY}'''
          sh '''docker push ${REGISTRY}/server
docker logout'''
        }

      }
    }

  }
  environment {
    REGISTRY = '192.168.173.7'
    user = 'admin'
    password = 'admin123'
  }
}