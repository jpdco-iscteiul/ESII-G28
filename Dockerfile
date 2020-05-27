# Source Image name
FROM openjdk:8
# Command to update and install Apache packages
ADD target/HelloWorld-0.0.1-SNAPSHOT.jar HelloWorld.jar
# open port 
EXPOSE 8080
# Command to run Apache server in background
CMD ["java" "-jar" "/HelloWorld.jar"]


