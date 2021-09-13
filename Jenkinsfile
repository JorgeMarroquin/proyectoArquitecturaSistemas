node {
stage 'Checkout'

    checkout scm
  stage('test') {
        def mvnHome =  tool name: 'M3', type: 'maven'
        sh "${mvnHome}/bin/mvn test"

    }
 
 //def mvnHome = tool 'M3' 
  stage('SonarQube Analysis') {
        def mvnHome =  tool name: 'M3', type: 'maven'
        withSonarQubeEnv('sonarq') { 
          sh "${mvnHome}/bin/mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Jmo=47558520"
        }
    }
  
  stage("Quality Gate"){
          timeout(time: 1, unit: 'HOURS') {
              def qg = waitForQualityGate()
              if (qg.status != 'OK') {
                  mail bcc: '', body: 'SonarQube Failed', cc: '', from: '', replyTo: '', subject: "QA failed in branch ${env.BRANCH_NAME}", to: 'marroquin181358@unis.edu.gt'
                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
              }
              if (qg.status == 'OK') {
                  mail bcc: '', body: 'SonarQube Acepted', cc: '', from: '', replyTo: '', subject: "QA Acepted in branch ${env.BRANCH_NAME}", to: 'marroquin181358@unis.edu.gt'
              }
          }
      }
      
    stage('Compile-Package-create-war-file') {
        def mvnHome =  tool name: 'M3', type: 'maven'
        sh "${mvnHome}/bin/mvn package"
    }

    stage('Deploy to Tomcat') {
        try {
        sh 'ls'

        dir('ventas/target') {
           sh 'mv ventas-0.0.1-SNAPSHOT.jar main-dev.war'          
          sh 'docker cp main-dev.jar tomcatdev:/usr/local/tomcat/webapps'
        }
    }

}
