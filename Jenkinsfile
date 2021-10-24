pipeline {
    agent any 
    tools{
      maven 'M3'
   }
    stages {
        stage('checkout'){
            steps{
                git branch: 'origin/development', url:'https://github.com/JuanCaceresDL/arquitecturadesistemas.git'
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
            sh "ls"
            deploy adapters: [tomcat9(credentialsId: 'f9953ce9-74cc-4793-b16f-f29df93a1085', path: '', url: 'http://104.43.137.120:8085')], contextPath: "devv", war: '**/*.war'
          }
        }     
    }
}