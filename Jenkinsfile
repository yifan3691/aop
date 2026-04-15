pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '20'))
    }

    parameters {
        booleanParam(name: 'SKIP_TESTS', defaultValue: false, description: 'Skip Maven tests during the package stage')
    }

    environment {
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository'
        MAVEN_ARGS = '-B -U -ntp'
        DEPLOY_HOST = '170.9.59.234'
        DEPLOY_DIR = '/home/ubuntu/project/aop'
        DEPLOY_CREDENTIALS_ID = 'deploy-server-ssh'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Verify Environment') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Test') {
            when {
                expression {
                    !params.SKIP_TESTS
                }
            }
            steps {
                sh "mvn ${env.MAVEN_ARGS} clean test"
            }
        }

        stage('Package') {
            steps {
                script {
                    def packageCmd = params.SKIP_TESTS
                        ? "mvn ${env.MAVEN_ARGS} clean package -pl maven-a -am -DskipTests"
                        : "mvn ${env.MAVEN_ARGS} package -pl maven-a -am"
                    sh packageCmd
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'maven-a/target/*.jar,maven-b/target/*.jar,maven-c/target/*.jar', fingerprint: true
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([sshUserPrivateKey(
                    credentialsId: env.DEPLOY_CREDENTIALS_ID,
                    keyFileVariable: 'SSH_KEY',
                    usernameVariable: 'SSH_USER'
                )]) {
                    sh '''
                        set -e
                        ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no "$SSH_USER@$DEPLOY_HOST" "mkdir -p '$DEPLOY_DIR'"
                        scp -i "$SSH_KEY" -o StrictHostKeyChecking=no maven-a/target/maven-a-1.0-SNAPSHOT.jar "$SSH_USER@$DEPLOY_HOST:$DEPLOY_DIR/app.jar"
                        ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no "$SSH_USER@$DEPLOY_HOST" "sudo systemctl restart aop && sudo systemctl status aop --no-pager --lines=20"
                    '''
                }
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
        }
    }
}
