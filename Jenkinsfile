@Library('chattingo-pipeline') _

pipeline {
    agent any

    // Set Docker tag for entire pipeline
    environment {
        DOCKER_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Run Chattingo Pipeline') {
            steps {
                script {
                    chatPipeline()
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}

