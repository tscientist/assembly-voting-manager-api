## _Assembly voting manager API_


API para gerenciamento de votações de assembleia


## Features


- Register a new topic to be voted
- Open a voting session for a topic with customizable duration
- Receive votes from members
- Valid the CPF of the voting member
- ✨Counts the votes and returns the result of the poll ✨


## Tech

Assembly voting manager API uses a number of open source projects to work properly:

- [Java 11](https://www.java.com/pt-BR/) - Java is an object-oriented programming language.
- [Spring Boot](https://spring.io/projects/spring-boot) - Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".
- [PostgreSQL](https://www.postgresql.org/) - PostgreSQL is a powerful, open source object-relational database system.
- [Apache Maven](https://maven.apache.org/) - Maven is a build automation tool used in Java projects.
- [Docker](https://www.docker.com/) - Docker is a set of paas products that use operating system-level virtualization to deliver software in packages called containers.
- [Amazon SQS](https://aws.amazon.com/pt/sqs/) - Amazon Simple Queue Service (SQS) allows you to send, store, and receive messages between software components.


And of course Assembly voting manager API itself is open source with a [public repository](https://github.com/tscientist/assembly-voting-manager-api) on GitHub.

## Installation

Assembly voting manager API requires [Docker](https://www.docker.com/), [Docker compose](https://docs.docker.com/compose/), [Java 11](https://www.java.com/pt-BR/) and [Apache Maven](https://maven.apache.org/) to run.

First we need to run docker to get postgresql running.

```sh
cd assembly-voting-manager-api
docker-compose up
```

You must provide AWS IAM access credentials with permissions of **AmazonSQSFullAccess** in addition to the access uri of an Amazon SQS queue, credentials need to be added in resources/application.yml file. *The credentials entered in the example below are for demonstration purposes only.* 
```sh
cloud:
  aws:
    region:
      static: sa-east-1
      auto: false
    credentials:
      access-key: HAUA43I29402
      secret-key: FNEAOIGUGA09459043J534
    end-point:
      uri: https://sqs.sa-east-1.amazonaws.com/5435353/assembly-voting-manager-queue
```

Then you have to run maven to install the dependencies and let the app run.

```sh
mvn clean
mvn install -DskipTests
mvn spring-boot:run
```

The application is running on localhost port 8080 and it is possible to perform the tests.

## Routes

#### Register a new topic to be voted - POST

```sh
curl --location 'http://localhost:8080/topic' \
--header 'Content-Type: application/json' \
--data '{
    "title": "Titulo de pauta",
    "description": "Descrição do meu topico para ser aberto para votação"
}'
```

#### List all existing topics - GET
Page, size and sort parameters are optional.
```sh
curl --location 'http://localhost:8080/topic?page=0&size=2&sort=sessionEnd%2CASC'
```

#### Open a voting session for a topic - PUT 
The topicId must be passed in the route and in the body of the session it is possible to define how many minutes the session must remain open. If no value is passed, the default time is 1 minute.
```sh
curl --location --request PUT 'http://localhost:8080/topic/{topicId}' \
--header 'Content-Type: application/json' \
--data '{
    "sessionEnd": 3
}'
```

#### Vote for a topic - POST
Possible votes are "Sim" or "Não". If the passed cpf is invalid or if the session has already been closed, the vote will not be registered.

```sh
curl --location 'http://localhost:8080/vote' \
--header 'Content-Type: application/json' \
--data '{
    "topicId": "b6bf9d18-e011-42a9-8eb3-86034ae6d91f",
    "cpf": "33816982069",
    "vote": "Sim"
}'
```
#### Search results by topic - GET
You can only see the result of polls that have already ended.

```sh
curl --location 'http://localhost:8080/topic/result?topicId=e07e6d0f-b3c0-4df1-93dc-07c61ae05d6c'
```

#### Validate CPF - GET
Returns the status of the informed CPF "ABLE_TO_VOTE" or "UNABLE_TO_VOTE".
```sh
curl --location 'http://localhost:8080/vote/validate-cpf' \
--header 'cpf: 68760702044'
```

**Free Software, Hell Yeah!**



