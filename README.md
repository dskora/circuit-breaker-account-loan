# Hysterix account loan demo

## Getting started
Many of us occasionally come across a loan possibility in our bank application, if we meet special criteria.
Let's imaging we have a loan microservice, which allow us to take a loan, but display nothing if service is not running.

## Prerequisite
- Framework: Spring Boot 3 / Cloud Netflix Hysterix
- Language: Java 17
- Build Tool: Maven

### Spring Boot Backend

```shell script
mvn clean install
```

### Run docker containers via docker compose

```shell script
docker-compose up -d
```

### Test steps (1)

```shell script
curl -X POST localhost:8081/loans/eligibility -d '{"balance": "20000"}' -H "Content-Type: application/json"
curl -X POST localhost:8080/accounts -d '{"firstname": "Tom", "surname": "Jenkins", "initialBalance": "2300"}' -H "Content-Type: application/json"
curl -X POST localhost:8080/accounts -d '{"firstname": "Andrew", "surname": "Hadley", "initialBalance": "20"}' -H "Content-Type: application/json"
curl -X GET localhost:8080/accounts
```

### Test steps (2) - Circuit Open State
Get IDs from localhost:8080/accounts
```shell script
docker-compose stop loan
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/actuator/health
```

### Test steps (2) - Circuit Half Open State
Get IDs from localhost:8080/accounts
```shell script
docker-compose up loan
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/actuator/health
```

### Test steps (3) - Circuit Closed State
Get IDs from localhost:8080/accounts
```shell script
curl -X GET localhost:8080/accounts/{id}
curl -X GET localhost:8080/actuator/health
```