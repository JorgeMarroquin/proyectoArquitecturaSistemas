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
                  mail bcc: '', body: 'SonarQube Failed', cc: '', from: '', replyTo: '', subject: 'QA failed Jorge', to: 'marroquin181358@unis.edu.gt'
                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
              }
              if (qg.status == 'OK') {
                  mail bcc: '', body: 'SonarQube Acepted', cc: '', from: '', replyTo: '', subject: 'QA Acepted', to: 'marroquin181358@unis.edu.gt'
              }
          }
      }

}
