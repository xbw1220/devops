pipeline {
  agent {
    docker {
      image '192.168.173.7/maven:3.6.3-jdk-8'
    }

  }
  stages {
    stage('maven build') {
      steps {
        echo 'maven install'
        sh 'mvn -q -e -B -U clean install'
      }
    }

  }
}