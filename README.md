# DB Connector

This is an application providing ability to connect any database and fetch its data. Application hosted on Heroku and could be easy tested.

## Heroku database credentials 

- Host: ec2-34-237-247-76.compute-1.amazonaws.com
- Database: d8aen9s893c7dv
- User: qjrfdcsfpwiwzo
- Port: 5432
- Password: 01ab19bc662855a6d69817fc9406c4c0b50d9224590ac4d35364bfde6aed88f1
- URI: postgres://qjrfdcsfpwiwzo:01ab19bc662855a6d69817fc9406c4c0b50d9224590ac4d35364bfde6aed88f1@ec2-34-237-247-76.compute-1.amazonaws.com:5432/d8aen9s893c7dv

## Swagger
Application provides swagger interface for convinient interaction.
https://db-connector-app.herokuapp.com/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

## Running the Application Locally
There are 2 ways to start application on local machine:

   1) #### Using docker. 
   
      - Navigate to ./cicd folder and run docker-compose.yml file. Application and database will be started in docker contaiters. 
      
   2) #### Build and run manually.
   
      - Navigate to /.cicd/db folder. Run cmd script if you are using Windows OS or call commands from file manually if Unix OS. If docker cant be started for you, its needed to create database manually and then specify correct credentials and host in application.yml.
      
      - Run Spring boot app with dev profile. ( to use default creds for local development)
   
 
      - Open localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#
   
Happy playing:)
