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
                  mail bcc: '', body: 'SonarQube Failed', cc: '', from: '', replyTo: '', subject: "QA failed in branch ${env.BRANCH_NAME}", to: 'marroquin181358@unis.edu.gt, jflores@unis.edu.gt'
                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
              }
              if (qg.status == 'OK') {
                  mail bcc: '', body: 'SonarQube Acepted', cc: '', from: '', replyTo: '', subject: "QA Acepted in branch ${env.BRANCH_NAME}", to: 'marroquin181358@unis.edu.gt, jflores@unis.edu.gt'
              }
          }
      }
      
    stage('Compile-Package-create-war-file') {
        def mvnHome =  tool name: 'M3', type: 'maven'
        sh "${mvnHome}/bin/mvn package"
    }

    stage('Deploy to Tomcat') {
        sh 'cd target/'
        deploy adapters: [tomcat9(credentialsId: 'dfc6effb-a846-47d1-8504-0e544d4c9c7a', path: '', url: 'http://172.21.0.4:8080/')], contextPath: null, war: '**/*.war'

    }    
}
