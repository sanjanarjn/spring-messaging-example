## Messaging with Spring Boot & RabbitMQ
Spring Boot application to demonstrate a simple messaging flow across microservices.

### Steps to run and test out a simple messaging flow

#### Set up RabbitMQ in local
- Run RabbitMQ in a docker container using the following command,
  ```
  docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management
  ```

#### Set up the project in local  
- Clone this repository
- Run ```CompanyServiceApplication``` class to get the company services up and running.
- Run ```ReviewServiceApplication``` class to run the review services.
- Create a company using the following curl,
  ```
  curl --location 'http://localhost:8081/company' \
  --header 'Content-Type: application/json' \
  --data '{
      "name": "Company-Name"
  }'
  ```
- Add review for the company using the below,
  ```
  curl --location 'http://localhost:8080/review' \
  --header 'Content-Type: application/json' \
  --data '{
      "rating": 4,
      "comment": "Good",
      "companyId": "1"
  }'
  ```
- The add review service would send the review message to RabbitMQ and company service would consume it and calculate the average.


