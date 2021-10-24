pipeline {
    agent any 
    tools{
      maven 'M3'
   }
    stages {
        stage('checkout'){
            steps{
                checkout scm
                }
            }
        stage("Compile WAR file") {
            steps{
             mvnHome =  tool name: 'M3', type: 'maven'
                sh "${mvnHome}/bin/mvn clean install"
                sh "${mvnHome}/bin/mvn package"
            }    
        }

        stage('Deploy to Tomcat') {
            steps {
            sh 'cd target/'
            deploy adapters: [tomcat9(credentialsId: 'f9953ce9-74cc-4793-b16f-f29df93a1085', path: '', url: 'http://104.43.137.120:8085')], contextPath: "devv", war: '**/*.war'
          }
        }     
    }
}