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
          sh "${mvnHome}/bin/mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=tzec99"
        }
    }

}
