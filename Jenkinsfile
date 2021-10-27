node {
    stage 'Checkout'
        checkout scm

    if (env.BRANCH_NAME.startsWith('PR')) {
        stage('test') {
            try{
                def mvnHome =  tool name: 'M3', type: 'maven'
                sh "${mvnHome}/bin/mvn test"
                slackSend channel: 'jenkins-pipeline', color: '#008f39', message: "${env.JOB_NAME} #${BUILD_NUMBER}\nLos unit Test han pasado con éxito", teamDomain: 'test-sa-mundo', tokenCredentialId: '216c0d8c-5fb2-4a82-b39c-3be85e57d9aa'
            }catch(e){
                slackSend channel: 'jenkins-pipeline', color: '#ff0000', message: "${env.JOB_NAME} #${BUILD_NUMBER}\nNo se ha pasado los unit test", teamDomain: 'test-sa-mundo', tokenCredentialId: '216c0d8c-5fb2-4a82-b39c-3be85e57d9aa'
            }
        }

        //def mvnHome = tool 'M3' 
        stage('SonarQube Analysis') {
            try{
                def mvnHome =  tool name: 'M3', type: 'maven'
                withSonarQubeEnv('sonarq') { 
                sh "${mvnHome}/bin/mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Jmo=47558520"
                slackSend channel: 'jenkins-pipeline', color: '#008f39', message: "${env.JOB_NAME} #${BUILD_NUMBER}\nAnálisis exitoso con SonarQube", teamDomain: 'test-sa-mundo', tokenCredentialId: '216c0d8c-5fb2-4a82-b39c-3be85e57d9aa'
                }
            }catch(e){
                slackSend channel: 'jenkins-pipeline', color: '#ff0000', message: "${env.JOB_NAME} #${BUILD_NUMBER}\nError en el análisis de SonarQube", teamDomain: 'test-sa-mundo', tokenCredentialId: '216c0d8c-5fb2-4a82-b39c-3be85e57d9aa'
            }
        }

        stage("Quality Gate"){
            timeout(time: 1, unit: 'HOURS') {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                    mail bcc: '', body: "Pipeline JENKINS SonarQube Failed in branch ${env.BRANCH_NAME}", cc: '', from: '', replyTo: '', subject: "Pipeline JENKINS QA failed", to: 'marroquin181358@unis.edu.gt, jorge.marroquin.ochoa@gmail.com, jflores@unis.edu.gt'
                    slackSend channel: 'jenkins-pipeline', color: '#ff0000', message: "${env.JOB_NAME} #${BUILD_NUMBER}\nEl código no cumple con la revisión de calidad.", teamDomain: 'test-sa-mundo', tokenCredentialId: '216c0d8c-5fb2-4a82-b39c-3be85e57d9aa'
                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }
                if (qg.status == 'OK') {
                    mail bcc: '', body: "Pipeline JENKINS SonarQube Acepted in branch ${env.BRANCH_NAME}", cc: '', from: '', replyTo: '', subject: "Pipeline JENKINS QA acepted", to: 'marroquin181358@unis.edu.gt, jorge.marroquin.ochoa@gmail.com, jflores@unis.edu.gt'
                    slackSend channel: 'jenkins-pipeline', color: '#008f39', message: "${env.JOB_NAME} #${BUILD_NUMBER}\nEl código ha pasado la revisión de calidad.", teamDomain: 'test-sa-mundo', tokenCredentialId: '216c0d8c-5fb2-4a82-b39c-3be85e57d9aa'

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
                slackSend channel: 'jenkins-pipeline', color: '#008f39', message: "${env.JOB_NAME} #${BUILD_NUMBER}\nSe ha desplegado la aplicación en servidor tomcat", teamDomain: 'test-sa-mundo', tokenCredentialId: '216c0d8c-5fb2-4a82-b39c-3be85e57d9aa'
            }
        }catch(e){
            slackSend channel: 'jenkins-pipeline', color: '#ff0000', message: "${env.JOB_NAME} #${BUILD_NUMBER}\nNo se ha podido desplegar la aplicación en servidor tomcat", teamDomain: 'test-sa-mundo', tokenCredentialId: '216c0d8c-5fb2-4a82-b39c-3be85e57d9aa'
        }
    }

    stage("Correo final"){
        mail bcc: '', body: "La pipeline #${BUILD_NUMBER} ha finalizado, puedes ver los resultados en\n${BUILD_URL}", cc: '', from: '', replyTo: '', subject: "Pipeline JENKINS #${BUILD_NUMBER} en ${env.BRANCH_NAME}", to: 'marroquin181358@unis.edu.gt, jorge.marroquin.ochoa@gmail.com, jflores@unis.edu.gt'
    }

}
