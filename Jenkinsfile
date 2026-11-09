pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                bat 'mvn -B -ntp clean compile'
            }
        }

        stage('Static Analysis') {
            steps {
                bat 'mvn -B -ntp checkstyle:check'
            }
        }

        stage('Test') {
            steps {
                bat encoding: 'UTF-8', script: 'mvn -B -ntp test'
            }
        }

        stage('Package') {
            steps {
                bat 'mvn -B -ntp package -DskipTests'
                archiveArtifacts artifacts: 'target/music-service-*.jar'
            }
        }
    }
}
