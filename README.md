# Circuit breaker account loan

## Getting started
Many of us occasionally come across a loan possibility in our bank application, if we meet a special criteria.
Let's imaging we have a loan microservice, which allow us to take a loan, but only when it's running.

## Prerequisite
- Framework: Spring Boot 3.1.5 / Resilience4j Circuit Breaker
- Language: Java 17
- Build Tool: Maven

### Spring Boot Backend

```shell script
./mvnw clean install
```

### Run docker containers via docker compose

```shell script
docker-compose up -d
```

## Testing
### Step (1)

```shell script
curl -X POST localhost:8081/loans/eligibility -d '{"balance": "20000"}' -H "Content-Type: application/json"
curl -X POST localhost:8080/accounts -d '{"firstname": "Tom", "surname": "Jenkins", "initialBalance": "2300"}' -H "Content-Type: application/json"
curl -X POST localhost:8080/accounts -d '{"firstname": "Andrew", "surname": "Hadley", "initialBalance": "20"}' -H "Content-Type: application/json"
```

### Step (2) - Circuit Closed State
```shell script
curl -X GET localhost:8080/accounts
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/actuator/health
```
At this point loan service is running, so we should see below in the response
```
"loanEligibility":{"maxAmount":4600.00,"maxMonthsTerm":60,"eligible":true}
```

### Step (3) - Circuit Open State
After N tries circuit breaker changes the state to open
```
docker-compose stop loan
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/actuator/health
```
Loan service is not running, so we should see below in the response
```
"loanEligibility":{"maxAmount":0,"maxMonthsTerm":0,"eligible":false}
```

### Step (4) - Circuit Half Open State
```shell script
docker-compose up loan
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/actuator/health
```