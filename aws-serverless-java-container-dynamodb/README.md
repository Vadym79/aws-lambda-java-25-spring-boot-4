# Example of Lambda with managed Java 25 runtime using Spring Boot 4 with AWS Serverless Java Containerand Amazon DynamoDB  


## Architecture

<p align="center">
  <img src="/aws-serverless-java-container-dynamodb/src/main/resources/img/app_arch.png" alt="Application Architecture"/>
</p>

## Installation and deployment

```bash
## Install git and maven 

## Clone git repository locally  

git clone https://github.com/Vadym79/aws-lambda-java-25.git   

## Switch to aws-serverless-java-container-dynamodb directory   

## Compile and package the Java application with Maven from the root (where pom.xml is located) of the project

mvn clean package

## Deploy your application with AWS SAM  
sam deploy -g --region us-east-1 

```

Or you can use the deployed API Gateway and its REST endpoints like get for /product/{id} and post for /product and so on

Use this Http Body as Json to create a sample order with 2 items :

 { "id": 1, "name": "Print 10x13", "price": 15 }  
 and  
 { "id": 2, "name": "A5 Book", "price": 5000 }  

