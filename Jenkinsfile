pipeline {
  agent {
    docker {
      image '192.168.173.7/maven:3.6.3-jdk-8'
      args '''-v /root/.m2:/root/.m2
-v /var/run/docker.sock:/var/run/docker.sock'''
    }

  }
  stages {
    stage('maven build') {
      parallel {
        stage('maven build') {
          steps {
            echo 'maven install'
            sh 'mvn -q -e -B -U clean install'
            sh 'mvn -q -e -B -U clean package -Dmaven.test.skip=true -pl devops-server/devops-server-web,devops-client/devops-client-web'
          }
        }

        stage('docker install') {
          steps {
            echo 'Install Docker'
            sh '''cat << EOF > /etc/apt/sources.list
deb https://mirrors.tuna.tsinghua.edu.cn/debian/ buster main contrib non-free
# deb-src https://mirrors.tuna.tsinghua.edu.cn/debian/ buster main contrib non-free
deb https://mirrors.tuna.tsinghua.edu.cn/debian/ buster-updates main contrib non-free
# deb-src https://mirrors.tuna.tsinghua.edu.cn/debian/ buster-updates main contrib non-free
deb https://mirrors.tuna.tsinghua.edu.cn/debian/ buster-backports main contrib non-free
# deb-src https://mirrors.tuna.tsinghua.edu.cn/debian/ buster-backports main contrib non-free
deb https://mirrors.tuna.tsinghua.edu.cn/debian-security buster/updates main contrib non-free
# deb-src https://mirrors.tuna.tsinghua.edu.cn/debian-security buster/updates main contrib non-free
EOF'''
            sh '''apt-get update -y
apt-get install -y curl apt-transport-https ca-certificates curl gnupg2 software-properties-common
curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -
add-apt-repository \\
   "deb [arch=amd64] https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/debian \\
   $(lsb_release -cs) \\
   stable"
apt-get update -y
apt-get install -y docker-ce'''
          }
        }

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