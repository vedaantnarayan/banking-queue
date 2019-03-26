# banking-queue

## Tech Stack:
* 1. Spring Boot - 2.1.3
* 2. MySQL - 5.7.21-0ubuntu0.16.04.1
* 3. Java -1.8

## Code setup
### Build
* Install mvn 3.3 or above
* Install Java 8 or above
* run in terminal 
```
mvn clean install
```
* Deploy code at localhost:8080 using terminal
```
mvn spring-boot:run
```

## ER Diagram
![ER-Diagram](https://user-images.githubusercontent.com/46216991/54922853-0bb55080-4f2f-11e9-97ff-3624f4fc9747.png)


## Class Diagram
![Class-Diagram](https://user-images.githubusercontent.com/46216991/54974577-85465080-4fba-11e9-89b5-c710bfde3016.png)

## API Design


1. **/token (Method: POST)**

- To create token for each customer as per his account type(priority/regular). 
- If customer is new user then customer details will be persisted first and generates token.
- If customer is existing user then token will be generated.
- If customer opts for multiple services then once one service gets completed token will be reassigned to another counter to serve next services.

Request Body:

`{
    "servicePriority":"REGULAR",
    "tokenType":"ACCOUNT_OPEN",
    "branchName":"midtown",
    "customer":{
                "name":"Vedant",
                "phoneNumber":"5466788889",
                "address":{
                  "streeName":"streeName",
                  "city":"Hyderabad",
                  "state":"Telengana",
                  "country":"India",
                  "zipCode":"5600087"
          }
    }
} `

2. **/token/{tokenId}**
   -- fetch token details by token id.
   
3. **/counter/status**
   -- Lists down all counters and respective tokens assigned.
   
4. **/{branchName}/counter/status**
   -- Lists down all counters of that branch and respective tokens assigned to counters.
   

# Assumptions
- For new customer, customer details will be saved into database and then token will be issued and assigned to counter.
- For Existing Customer token will be generated and assigned to counter to operate based on services opted and type of account.
- At any given point of time tokens will be queued up at counters based on counter ranking (no of tokens assigned to counter).


