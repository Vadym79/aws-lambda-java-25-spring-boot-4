# Example of Lambda with Custom Runtime based on GraalVM Native Image with Java 25 using Spring Boot 4 with AWS Serverless Java Container and Amazon DynamoDB database  


## Architecture

<p align="center">
  <img src="/aws-lambda-java-25-spring-boot-4.0-aws-serverless-java-container-dynamodb-as-graalvm-native-image/src/main/resources/img/app_arch.png" alt="Application Architecture"/>
</p>

## Installation and deployment

```bash
curl -s "https://get.sdkman.io" | bash
source "/home/ec2-user/.sdkman/bin/sdkman-init.sh"

#install graalvm 25 (use the latest version available) 

sdk install java 25.0.2-graal  

# install native image  
sudo yum install gcc glibc-devel zlib-devel   
sudo dnf install gcc glibc-devel zlib-devel libstdc++-static  
sudo yum install maven

## install git and maven 

## Clone git repository locally  
git clone https://github.com/Vadym79/aws-lambda-java-25-spring-boot-4.git   
## Switch to aws-lambda-java-25-spring-boot-4.0-aws-serverless-java-container-dynamodb-as-graalvm-native-image   

## Compile and package the Java application with Maven from the root (where pom.xml is located) of the project

Set JAVA_HOME variable, for example export JAVA_HOME=/home/ec2-user/.sdkman/candidates/java/25.0.2-graal/  

mvn clean package

## Deploy your application with AWS SAM  
sam deploy -g --region us-east-1 

```

You can use the deployed API Gateway and its REST endpoints like get for /product/{id} and post for /product and so on

Use this Http Body as Json to create a sample order with 2 items :  
 { "id": 1, "name": "Print 10x13", "price": 15 }  
 and  
 { "id": 2, "name": "A5 Book", "price": 5000 }  


