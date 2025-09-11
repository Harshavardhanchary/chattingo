pipeline {
    agent any

    environment {
        DOCKER_TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scmGit(
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[
                        url: 'https://github.com/Harshavardhanchary/chattingo.git'
                    ]]
                )
                echo 'Checkout successful'
            }
        }

        stage('Prepare .env') {
            steps {
                withCredentials([
                    string(credentialsId: 'SPRING_DATASOURCE_URL', variable: 'SPRING_DATASOURCE_URL'),
                    string(credentialsId: 'SPRING_DATASOURCE_USERNAME', variable: 'SPRING_DATASOURCE_USERNAME'),
                    string(credentialsId: 'SPRING_DATASOURCE_PASSWORD', variable: 'SPRING_DATASOURCE_PASSWORD'),
                    string(credentialsId: 'JWT_SECRET', variable: 'JWT_SECRET'),
                    string(credentialsId: 'CORS_ALLOWED_ORIGINS', variable: 'CORS_ALLOWED_ORIGINS'),
                    string(credentialsId: 'CORS_ALLOWED_METHODS', variable: 'CORS_ALLOWED_METHODS'),
                    string(credentialsId: 'REACT_APP_API_URL', variable: 'REACT_APP_API_URL'),
                    string(credentialsId: 'CORS_ALLOWED_HEADERS', variable: 'CORS_ALLOWED_HEADERS'),
                    string(credentialsId: 'MYSQL_ROOT_PASSWORD', variable: 'MYSQL_ROOT_PASSWORD'),
                    string(credentialsId: 'MYSQL_DATABASE', variable: 'MYSQL_DATABASE')
                ]) {
                    sh '''
                        echo "SPRING_DATASOURCE_URL=$SPRING_DATASOURCE_URL" > .env
                        echo "SPRING_DATASOURCE_USERNAME=$SPRING_DATASOURCE_USERNAME" >> .env
                        echo "SPRING_DATASOURCE_PASSWORD=$SPRING_DATASOURCE_PASSWORD" >> .env
                        echo "JWT_SECRET=$JWT_SECRET" >> .env
                        echo "CORS_ALLOWED_ORIGINS=$CORS_ALLOWED_ORIGINS" >> .env
                        echo "CORS_ALLOWED_METHODS=$CORS_ALLOWED_METHODS" >> .env
                        echo "CORS_ALLOWED_HEADERS=$CORS_ALLOWED_HEADERS" >> .env
                        mkdir -p frontend
                        echo "REACT_APP_API_URL=$REACT_APP_API_URL" > frontend/.env
                        echo "MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD" >> .env
                        echo "MYSQL_DATABASE= $MYSQL_DATABASE" >> .env
                    '''
                }
            }
        }

        stage('File Scan') {
            steps {
                echo "🔍 Running filesystem security scan..."
                // Example: Trivy or SonarQube CLI
                sh 'trivy fs --exit-code 0 --no-progress .'
            }
        }

        stage('Build and Start Services') {
            steps {
                echo "⚙️ Building Docker containers..."
                sh 'docker system prune -f'
                sh "docker compose build --build-arg BUILD_NUMBER=${DOCKER_TAG}"
                echo 'Build complete'
            }
        }

        stage('Image Scan') {
            steps {
                echo "🔍 Scanning Docker images for vulnerabilities..."
                sh '''
                trivy image --exit-code 0 --no-progress harshavardhan303/chattingo-backend:${DOCKER_TAG}
                trivy image --exit-code 0 --no-progress harshavardhan303/chattingo-frontend:${DOCKER_TAG}
                '''
            }
        }
        stage('Docker Login') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin $DOCKER_REGISTRY
                    '''
                    }
                }
            }
        }
        stage('Docker-push') {
            steps {
            sh '''
            # Push to Docker Hub
            docker push harshavardhan303/chattingo-frontend:${BUILD_NUMBER}
            docker push harshavardhan303/chattingo-backend:${BUILD_NUMBER}
            '''
            }
        }
        stage ('Git-Login') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'git-cred', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
                    sh '''
                    git config user.name "${GIT_USER}"
                    git config user.email "harshavardhanchary7@gmail.com"
                    echo "https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com" > ~/.git-credentials
                    '''
                    }
                }
            }
        }
        stage('Checkout Manifests') {
            steps {
                dir('manifests') {
                git url: 'https://github.com/Harshavardhanchary/chattingo-manifests.git', branch: 'main'
                }
            }
        }
        stage('update deployment') {
            steps {
                dir('manifests') {
                    script {
                        withCredentials([usernamePassword(credentialsId: 'git-cred', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_PASS')]) {
                        sh '''
                        git config --global user.name "${GIT_USER}"
                        git config --global user.email "harshavardhanchary7@gmail.com"
                        git remote set-url origin https://${GIT_USER}:${GIT_PASS}@github.com/Harshavardhanchary/chattingo-manifests.git
                        chmod +x update.sh
                        ./update.sh ${BUILD_NUMBER}
                        git add .
                        git commit -m "updated image number with ${BUILD_NUMBER}"
                        git push origin HEAD:main
                        '''
                        }
                    }
                }
            }
        }
    }
}
