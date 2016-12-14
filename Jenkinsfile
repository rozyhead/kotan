node('node') {
  stage('Checkout') {
    checkout scm
  }

  stage('Compile'){
    sh 'bin/activator -no-colors compile'
  }

  stage('Test'){
    sh 'bin/activator -no-colors test'
    junit 'target/test-reports/**/*.xml'
  }

  stage('Multi JVM Test'){
    sh 'bin/activator -no-colors multi-jvm:test'
    junit 'target/test-reports/**/*.xml'
  }

  stage('Packaging'){
    sh 'bin/activator -no-colors dist'
  }

  stage('Archive'){
    archiveArtifacts artifacts: 'target/universal/*.zip'
  }
}
