def dockeruser = "gnpsa"
def imagename = "ubuntu:16.04"
def container = "apache2"
node {
   echo 'Building Apache Docker Image'

stage('Git Checkout') {
    git 'https://github.com/jpdco-iscteiul/ESII-G28'
    }
stage('Docker Compose') {
   powershell "docker-machine restart default"
   powershell "cd d:/wordpress"
   powershell "docker-compose up -d"
}
}
