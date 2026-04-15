pipeline {
    agent any

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
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
        }
    }
}
