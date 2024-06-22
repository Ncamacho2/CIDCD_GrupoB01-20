stage('Deploy to Development') {
    when {
        branch 'develop'
    }
    steps {
        sh 'docker-compose -f docker-compose.dev.yml up -d'
    }
}
stage('Deploy to Testing') {
    when {
        branch 'test'
    }
    steps {
        sh 'docker-compose -f docker-compose.test.yml up -d'
    }
}
stage('Deploy to Production') {
    when {
        branch 'main'
    }
    steps {
        sh 'docker-compose -f docker-compose.prod.yml up -d'
    }
}
