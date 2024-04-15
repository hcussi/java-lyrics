# Getting Started

Clone the repository and setup JDK 21 locally, using `sdkman` is a good alternative.
Rename the `.env.example` file to be `.env`.

## Run Application

Each subproject can be run independently, check `README.md` for each of those.

```bash
./gradlew :subproject:bootRun
```

## Build Image

```bash
./gradlew subproject:bootBuildImage --imageName=java-lyrics-subproject:0.0.1 --createdDate now
```

## Test Application

Will run the test task for all projects

```bash
./gradlew test
```

## Technological Stack

* Oracle JDK 21 with preview features
* Spring Boot v3 (Spring Framework v6)
* Spring Cloud (Using 2023.x compatible with Spring Boot 3.2.x)
* Micrometer tracing (Zipkin)
* OAuth2
* Docker Compose
  * Prometheus WIP
  * Grafana WIP
  * Redis / Rate Limiter
  * Zipkin / Distributed Tracing
  * ELK (ElasticSearch, Logstash and Kibana) / Log Aggregation
  * Mongo
  * Kafka / Asynchronous Eventing

### Application Architecture

Create docker images for each microservice.

More info:
  - [Building a Spring Boot microservices monorepo project with docker-compose](https://marceloh-web.medium.com/deploy-spring-boot-microservices-monorepo-project-with-docker-compose-ae4abbe8d2b4)
  - [Docker Compose: MongoDB and Spring Boot example](https://www.bezkoder.com/mongodb-docker-compose-spring-boot/)
  - [Get started with Spring Boot, MongoDB and Docker Compose](https://sfmohassel.medium.com/get-started-with-spring-boot-mongodb-and-docker-compose-cfae8283ed1b)

![Diagram](java-lyrics.drawio.png)

In order to use the user and lyrics API endpoints at least one user has to be registered. Use `api/register` from the API Gateway.

#### Discovery Server

Eureka server that allows microservices to self discover, basic auth set for connection. Check the particular `README.md` for more info.
Design Patterns implemented:
- Service Discovery

#### Api Gateway microservice

Contains the routes to redirect to users and lyrics service. Check the particular `README.md` for more info.
Design Patterns implemented:
- API Gateway
- Circuit Breaker
- Rate Limiter
- Retry
- Distribute Tracing
- Log Aggregation
- Load Balancing

#### User microservice

Will provide the functionality to CRUD users. Check the particular `README.md` for more info.
Design Patterns implemented:
- Asynchronous Eventing
- Distribute Tracing
- Log Aggregation
- Single Database

#### Email microservice

Will provide the functionality to send emails. Check the particular `README.md` for more info.
Design Patterns implemented:
- Asynchronous Eventing
- Distribute Tracing
- Log Aggregation

#### Lyrics client microservice

Will provide the external connection with the Deezer API, wraps the REST communication.
Design Patterns implemented:
- Distribute Tracing
- Log Aggregation
- Edge

#### Lyrics microservice

Will connect with the lyrics client microservice and wraps the communication implementation fallback mechanism and circuit breaker pattern.
Design Patterns implemented:
- Circuit Breaker
- Fallback
- Distribute Tracing
- Log Aggregation

#### Authentication microservice

Will validate the API authentication required to use the APIs.
Design Patterns implemented:
- Distribute Tracing
- Log Aggregation
- Data Replication with Eventual Consistency
- Single Database

### Common Utils library

Contains common and utility classes to be used across different services.

### OAuth2

Client Credentials Flow is used in order to authorize requests from the API Gateway and the different microservices,
this adds a layer of security to prevent to call the microservices directly bypassing the API gateway.
Api Gateway it is configured as the oauth client and the microservices will be the resource servers.

#### Auth0

Auth0 is used in order to use OAuth as a service. An application machine-to-machine should be configured, 
setting an API with permissions should be defined "read:lyrics", "read:users" and "write:users" and set
those permissions as scopes in the application.

[JWT Access Token example](https://jwt.io/#debugger-io?token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImRraFMzV2xKNFhsSUUwXy03aXFkSSJ9.eyJpc3MiOiJodHRwczovL2Rldi1hb2N6ajhzNWI4em1yazc0LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJlblo1Zkx0Uk1UbVZQTGhadlRhb2x6QzBuN0VFUldTY0BjbGllbnRzIiwiYXVkIjoiamF2YS1seXJpY3MtYXBpIiwiaWF0IjoxNzEyOTY4NDkyLCJleHAiOjE3MTMwNTQ4OTIsInNjb3BlIjoicmVhZDpseXJpY3MgcmVhZDp1c2VycyB3cml0ZTp1c2VycyIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyIsImF6cCI6ImVuWjVmTHRSTVRtVlBMaFp2VGFvbHpDMG43RUVSV1NjIn0.hv3tPXFPb9uHvlQsYer8HMymVV-Qlh_NBWU8WYdr7NER3T8C96nugnBhlA-MNh_zsC2JBzqk851mZVgpaZlzGl8Rq02E5n4lJ0InMVgLnM8Fc-fN2UcvUbB41iYH0sb038PUMPv_1FRojFYX8R4qj0xQHkPkej_NWcPxCHbb92ddz5hPJTyCNlQPfEkBAp1m9hBzctsErYwXPlruyxJr_NRz1nqO7HEITEnnG8EHZStUiaWKvFcaQq-yHZtogVSU8bZWJ6hiCjI9D5ZM1o-pMW31twz-eRrYOnfogmGicSxDDgPNDWccTBn7-sVbhkyOfaWCc5SKgIAC2vE1qQyGLQ&publicKey=%7B%0A%20%20%22e%22%3A%20%22AQAB%22%2C%0A%20%20%22kty%22%3A%20%22RSA%22%2C%0A%20%20%22n%22%3A%20%22qs3Hw0py7C_NaljHW-gLOt_g1Ks-rRKlU3T702aBqbk1LCOhAUOLwawd6-oWCMfRpfFvNspXZfBRyfK6UGC9-AbBp8vw8sKYUCGn3KitZOduT59rRdttM_kHTKG8wSfGuqMlXc85kqyHUbkmCepsaAy--96zypG48S4YpafitC5ga7uOBOVmNfv_lBCod6fDREeVY4fekahBiL0hlnYhYIkOK7kp6wvI9qpU-eRihsf5Egih7gNeRoyEMn4Elg6ShTDCrLBrASo5abBHKZ8O4Jcjxonz6eRX4kvzr2LT9fhrjSWUPgIDLyiZ4C0iCsACDN2IWL5E1MqGIqj9RNPESQ%22%0A%7D)

More info:
- [Tackling the OAuth2 Client component model in Spring Security](https://spring.io/blog/2023/08/22/tackling-the-oauth2-client-component-model-in-spring-security)
- [Spring Security OAuth2 Client Credentials Grant](https://dev.to/relive27/spring-security-oauth2-client-credentials-grant-fj5)
- [How to Use Client Credentials Flow with Spring Security](https://developer.okta.com/blog/2021/05/05/client-credentials-spring-security)

### AOP

It is used for the following cases:
* Logging REST request and params.
* Logging unexpected exceptions in services.

### Docker Compose

Start ELK stack:

```bash
docker-compose -f docker-compose-lyrics-elk.yml up -d
```

In order to collect metrics with Prometheus and use Grafana dashboard, use Redis for api rate limit and Mongo DB as storage system and Zipkin for tracing:

```bash
docker-compose -f docker-compose-lyrics.yml up -d
```

Start kafka cluster without Zookeeper (KRaft):

```bash
docker-compose -f docker-compose-lyrics-kafka.yml up -d
```

More info:
 - [Docker Compose for Running Kafka in Kraft Mode](https://medium.com/@katyagorshkova/docker-compose-for-running-kafka-in-kraft-mode-20c535c48b1a)
 - [Kafka raft (KRaft) cluster configuration from dev to prod — part 1](https://gsfl3101.medium.com/kafka-raft-kraft-cluster-configuration-from-dev-to-prod-part-1-8a844fabf804)
 - [https://github.com/provectus/kafka-ui/blob/master/documentation/compose/kafka-ui.yaml](https://github.com/provectus/kafka-ui/blob/master/documentation/compose/kafka-ui.yaml)
 - [https://github.com/Guilhermesfl/kafka-kraft/blob/main/docker/docker-compose.yml](https://github.com/Guilhermesfl/kafka-kraft/blob/main/docker/docker-compose.yml)

### Telemetry

Spring actuator is used in order to use different metrics and collect them in Prometheus.
`actuator/*` endpoints are accessed by using basic authentication.

#### Prometheus WIP

Consume the REST endpoint `actuator/prometheus` in order to collect the different metrics for the different endpoints.
It also uses the basic authentication to access the info.
Visit [http://localhost:9090](http://localhost:9090), no login credentials are required.

#### Grafana WIP

A default datasource and dashboard has been provided in order to check the different metrics collected by Prometheus.
Visit [http://localhost:3000/login](http://localhost:3000/login) and login with default credentials `admin/admin`.

#### Zipkin

Will be used for distributed tracing.
Visit [http://localhost:9411/zipkin](http://localhost:9411/zipkin), no login credentials are required.
More info
- [https://github.com/openzipkin/zipkin/tree/master/zipkin-server](https://github.com/openzipkin/zipkin/tree/master/zipkin-server).
- https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.micrometer-tracing

##### Troubleshooting

- If this container starts before the `discovery server`, it will fail to register; to solve this, just return the container
- or be sure that this starts after the `discovery server`.

#### ELK (ElasticSearch, Logstash and Kibana)

Check [http://localhost:9200/](http://localhost:9200/) to see elasticsearch info.
Will contain the log aggregation for all services. Be sure to check the `logs` folder and see some `*.json.logs` files.
Visit [http://localhost:5601/app/discover](http://localhost:5601/app/discover) and click `Create data View`.
Set a name and define an index pattern `logback-*`, you will see logs entries in the main view.
You can also create another data view for Zipkin logs using the index `zipkin-span-*`.

More info
- [Deploying ELK inside Docker Container: Docker-Compose](https://medium.com/@lopchannabeen138/deploying-elk-inside-docker-container-docker-compose-4a88682c7643)
- [Creating Log Infrastructure with Elastic Stack and Docker Compose (Part 1)](https://arceister.medium.com/creating-log-infrastructure-with-elastic-stack-and-docker-compose-part-1-6195e8b9f0b2)
- [Send the Logs of a Java App to the Elastic Stack (ELK)](https://www.baeldung.com/java-application-logs-to-elastic-stack)
- [Distributed Tracing with Spring Cloud Sleuth and Zipkin](https://medium.com/@bubu.tripathy/distributed-tracing-with-spring-cloud-sleuth-and-zipkin-9106c8afd349)

##### Troubleshooting

- If you don't see the message `"You have data in Elasticsearch. Now, create a data view."`, restart the docker image `lyrics-logstash`.
  Check the container logs and see if the events are being processed.

### Redis

Will be used for API rate limiting.

### Mongo DB

Will be used with users and lyrics collections. You can connect to the DB `lyricsdb` using MongoDB Compass. 

### Kafka

Will be used for async event driven communication.
Visit [http://localhost:9099/](http://localhost:9099/) and check that the cluster has been connected successfully, also can check the topic created. 
