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
                   
                  emailext body: 'SonarQube have an error', subject: 'Quality Error', to: 'jorge.marroquin.ochoa@gmail.com'
                  mail bcc: '', body: 'SonarQube Failed', cc: '', from: '', replyTo: '', subject: 'QA failed', to: 'jorge.marroquin.ochoa@gmail.com'
                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
              }
          }
      }

}
