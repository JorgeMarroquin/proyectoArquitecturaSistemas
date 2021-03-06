node {

    environment {
        GENERALPASSWORD = credentials('aae686ba-0810-4fc9-8c89-eb2cd201f71c')
    }
    stage 'Checkout'
        checkout scm

    if (env.BRANCH_NAME.startsWith('PR')) {
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
                    mail bcc: '', body: "Pipeline JENKINS SonarQube Failed in branch ${env.BRANCH_NAME}", cc: '', from: '', replyTo: '', subject: "Pipeline JENKINS QA failed", to: 'marroquin181358@unis.edu.gt'
                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }
                if (qg.status == 'OK') {
                    mail bcc: '', body: "Pipeline JENKINS SonarQube Acepted in branch ${env.BRANCH_NAME}", cc: '', from: '', replyTo: '', subject: "Pipeline JENKINS QA acepted", to: 'marroquin181358@unis.edu.gt'
                }
            }
        }
    
    }else if(env.BRANCH_NAME == 'dev') {
      
        stage("Compile WAR file ${env.BRANCH_NAME}") {
            def mvnHome =  tool name: 'M3', type: 'maven'
            sh "${mvnHome}/bin/mvn -Dspring.profiles.active=dev clean install"
            sh "${mvnHome}/bin/mvn -Dspring.profiles.active=dev package"
        }

        stage('Deploy to Tomcat') {
            sh 'cd target/'
            deploy adapters: [tomcat9(credentialsId: 'f9953ce9-74cc-4793-b16f-f29df93a1085', path: '', url: 'http://104.43.137.120:8085')], contextPath: 'dev', war: '**/*.war'

        }

    }else if(env.BRANCH_NAME == 'uat') {
      
        stage("Compile WAR file ${env.BRANCH_NAME}") {
            def mvnHome =  tool name: 'M3', type: 'maven'
            sh "${mvnHome}/bin/mvn -Dspring.profiles.active=uat package"
        }

        stage('Deploy to Tomcat') {
            sh 'cd target/'
            deploy adapters: [tomcat9(credentialsId: 'f9953ce9-74cc-4793-b16f-f29df93a1085', path: '', url: 'http://104.43.137.120:8085')], contextPath: 'uat', war: '**/*.war'

        }
        
    }else if(env.BRANCH_NAME == 'main') {
      
        stage("Compile WAR file ${env.BRANCH_NAME}") {
            def mvnHome =  tool name: 'M3', type: 'maven'
            sh "${mvnHome}/bin/mvn -Dspring.profiles.active=main package"
        }

        stage('Deploy to Tomcat') {
            sh 'cd target/'
            deploy adapters: [tomcat9(credentialsId: 'f9953ce9-74cc-4793-b16f-f29df93a1085', path: '', url: 'http://104.43.137.120:8085')], contextPath: 'main', war: '**/*.war'

        }
        
    }    
}
