node {

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
                    slackSend channel: 'jenkins-pipeline', color: '#ff0000', message: "El código no cumple con la revisión de calidad en la rama ${env.BRANCH_NAME}", teamDomain: 'test-sa-mundo', tokenCredentialId: '216c0d8c-5fb2-4a82-b39c-3be85e57d9aa'
                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }
                if (qg.status == 'OK') {
                    mail bcc: '', body: "Pipeline JENKINS SonarQube Acepted in branch ${env.BRANCH_NAME}", cc: '', from: '', replyTo: '', subject: "Pipeline JENKINS QA acepted", to: 'marroquin181358@unis.edu.gt'
                    slackSend channel: 'jenkins-pipeline', color: '#008f39', message: "El código ha pasado la revisión de calidad en la rama ${env.BRANCH_NAME}", teamDomain: 'test-sa-mundo', tokenCredentialId: '216c0d8c-5fb2-4a82-b39c-3be85e57d9aa'

                }
            }
        }
    
    }else{

        try{
            stage("Compile WAR file ${env.BRANCH_NAME}") {
                def mvnHome =  tool name: 'M3', type: 'maven'
                sh "${mvnHome}/bin/mvn -Dspring.profiles.active=${env.BRANCH_NAME} clean install"
                sh "${mvnHome}/bin/mvn -Dspring.profiles.active=${env.BRANCH_NAME} package"
            }

            stage('Deploy to Tomcat') {
                sh 'cd target/'
                deploy adapters: [tomcat9(credentialsId: 'f9953ce9-74cc-4793-b16f-f29df93a1085', path: '', url: 'http://104.43.137.120:8085')], contextPath: "${env.BRANCH_NAME}", war: '**/*.war'
            }
        }catch(e){
            slackSend channel: 'jenkins-pipeline', color: '#ff0000', message: "${env.JOB_NAME}>>${env.BRANCH_NAME} #${BUILD_NUMBER}\nNo se ha podido desplegar la aplicación de tomcat en la rama ${env.BRANCH_NAME}", teamDomain: 'test-sa-mundo', tokenCredentialId: '216c0d8c-5fb2-4a82-b39c-3be85e57d9aa'
        }
    }
}
