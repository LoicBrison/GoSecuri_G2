pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'ls -la && cd GoSecuriApp && mvn --version && mvn clean && mvn package && mvn install'
      }
    }

    stage('Run') {
      steps {
        sh 'ls -la && cd GoSecuriApp && java -jar target/GoSecuriApp-1.0-SNAPSHOT.jar && ls -la'
        sh 'docker ps'
        
        sh 'cp GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/.htpasswd   $HOME/gosecuri/'
        sh 'cp GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/*  $HOME/gosecuri/'
        sh 'cp -r GoSecuriApp/src/main/java/com/epsi/gosecuri/ressourceFiles/ $HOME/gosecuri/'
        
        //sh 'cd GoSecuriApp/src/main/java/com/epsi/gosecuri/ && ls -la'
        stash includes: 'GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/*', name: 'generatedFiles'
        stash includes: 'GoSecuriApp/src/main/java/com/epsi/gosecuri/ressourceFiles/*', name: 'ressourceFiles'
      }
    }

    stage('Deploy') {
      agent {
        docker {
          image 'gosecuri:latest'
          args '-u root --privileged'
        }
      }
      steps {
        
        sh 'pwd && cat /etc/nginx/conf.d/default.conf'
        //dir('/usr/share/nginx/html'){
        dir('html'){
          unstash 'generatedFiles'
          unstash 'ressourceFiles'
        }
        sh 'ls -la'
        sh 'ls -la  html/GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/'
        sh 'cp html/GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/.htpasswd  /usr/share/nginx/html/'
        sh 'cp html/GoSecuriApp/src/main/java/com/epsi/gosecuri/generatedFiles/*  /usr/share/nginx/html/'
        sh 'cp -r html/GoSecuriApp/src/main/java/com/epsi/gosecuri/ressourceFiles/ /usr/share/nginx/html/'
        sh 'ls -la  /usr/share/nginx/html/'
        sh 'ls -la  /usr/share/nginx/html/ressourceFiles/'
      }
    }

  }
  tools {
    maven 'Maven'
    nodejs 'NodeJS'
  }
}
