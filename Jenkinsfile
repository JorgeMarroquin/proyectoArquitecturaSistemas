pipeline {
    agent any 
    tools{
      maven 'maven'
   }
    stages {
        stage('checkout'){
            steps{
                echo GIT_BRANCH 
                git 'https://github.com/JuanCaceresDL/arquitecturadesistemas.git'
                }
            }
        stage("Compile WAR file") {
            steps{
             withMaven(maven: 'M3') {
                sh "mvn clean install"
                sh "mvn package"
              }
            }    
        }

        stage('Deploy to Tomcat') {
            steps {
            sh 'cd target/'
            deploy adapters: [tomcat9(credentialsId: 'efd1443a-a9d5-43ce-941b-78e8aaf77fab', path: '', url: 'http://104.43.137.120:8085')], contextPath: "devv", war: '**/*.war'
          }
        }     
    }