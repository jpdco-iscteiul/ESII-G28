def dockeruser = "gnpsa"
def imagename = "ubuntu:16"
def container = "apache2"
node {
   echo 'Building Apache Docker Image'

stage('Git Checkout') {
    git 'https://github.com/jpdco-iscteiul/ESII-G28'
    }
    
stage('Build Docker Imagae'){
     powershell "docker-compose up -d"
    }
}
