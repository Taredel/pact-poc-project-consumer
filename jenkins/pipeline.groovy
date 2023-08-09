pipeline {
    agent any

    environment {
        GITHUB_ACCESS_TOKEN = credentials('GITHUB_ACCESS_TOKEN')
        GIT_BASH = "C:\\Program Files\\Git\\bin\\bash.exe"
        MAVEN_HOME = "C:/Program Files/apache-maven-3.8.3" // Use forward slashes
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'GITHUB_ACCESS_TOKEN', variable: 'TOKEN')]) {
                        def cloneDirectory = 'pact-poc-project-provider'
                        bat "\"${GIT_BASH}\" -c  'rm -rf ${cloneDirectory}'"
                        bat """git clone "https://${TOKEN}@github.com/Taredel/pact-poc-project-provider" """
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    def mavenCmd = "\"${MAVEN_HOME}/bin/mvn\"" // Use forward slashes
                    def projectDirectory = "pact-poc-project-provider"
                    bat """${GIT_BASH} -c '${mavenCmd} clean install' --exec ${projectDirectory}"""
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    def mavenCmd = "\"${MAVEN_HOME}/bin/mvn\"" // Use forward slashes
                    def projectDirectory = "pact-poc-project-provider"
                    bat """${GIT_BASH} -c '${mavenCmd} deploy' --exec ${projectDirectory}"""
                }
            }
        }

    }
}