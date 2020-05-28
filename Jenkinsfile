def dockeruser = "gnpsa"
def imagename = "openjdk:8"
def container = "helloworld"
node {
   echo 'Building Apache Docker Image'

stage('Git Checkout') {
    git 'https://github.com/jpdco-iscteiul/ESII-G28.git'
    }
    
stage('Build Docker Imagae'){
     powershell "docker build -t  ${imagename} ."
    }
    
stage('Stop Existing Container'){
     powershell "docker stop ${container}"
    }
    
stage('Remove Existing Container'){
     powershell "docker rm ${container}"
    }
    
stage ('Runing Container to test built Docker Image'){
    powershell "docker run -dit --name ${container} -p 8091:80 ${imagename}"
    }
   stage('Tag Docker Image'){
    powershell "docker tag ${imagename} ${env.dockeruser}/helloworld"
    }

stage('Docker Login and Push Image'){
    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'dockerpasswd', usernameVariable: 'dockeruser')]) {
    powershell "docker login -u ${dockeruser} -p ${dockerpasswd}"
    }
    powershell "docker push ${dockeruser}/helloworld"
    }
}
