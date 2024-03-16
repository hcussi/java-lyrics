# Getting Started

Clone the repository and setup JDK 21 locally, using `sdkman` is a good alternative.
Rename the `.env.example` file to be `.env`.

## Run Application

Each subproject can be run independently, check `README.md` for each of those.

```bash
./gradlew :subproject:bootRun
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
* Docker Compose
  * Prometheus
  * Grafana
  * Redis
  * Zipkin
  * ELK (ElasticSearch, Logstash and Kibana)
  * Kafka

### Application Architecture

Create docker images for each microservice:
  - [Building a Spring Boot microservices monorepo project with docker-compose](https://marceloh-web.medium.com/deploy-spring-boot-microservices-monorepo-project-with-docker-compose-ae4abbe8d2b4)
  - [Docker Compose: MongoDB and Spring Boot example](https://www.bezkoder.com/mongodb-docker-compose-spring-boot/)
  - [Get started with Spring Boot, MongoDB and Docker Compose](https://sfmohassel.medium.com/get-started-with-spring-boot-mongodb-and-docker-compose-cfae8283ed1b)

#### Discovery Server

Eureka server that allow microservices to self discover, basic auth set for connection.

#### Api Gateway microservice

Contains the routes to redirect to users and lyrics service.

#### User microservice

Will provide the functionality to CRUD users. Check the particular `README.md` for more info.

#### Lyrics microservice

WIP

#### Authorization microservice

WIP

### Common Utils library

Contains common and utility classes to be used across different services.

### AOP

It is used for the following cases:
* Logging REST request and params.
* Logging unexpected exceptions in services.

### Docker Compose

In order to collect metrics with Prometheus and use Grafana dashboard, use Redis for api rate limit and Mongo DB as storage system and Zipkin for tracing; execute the following command:

```bash
docker-compose -f docker-compose-lyrics.yml up -d
```

### Telemetry

Spring actuator is used in order to use different metrics and collect them in Prometheus.
`actuator/*` endpoints are accessed by using basic authentication.

#### Prometheus

Consume the REST endpoint `actuator/prometheus` in order to collect the different metrics for the different endpoints.
It also uses the basic authentication to access the info.
Visit [http://localhost:9090](http://localhost:9090), no login credentials are required.

#### Grafana

A default datasource and dashboard has been provided in order to check the different metrics collected by Prometheus.
Visit [http://localhost:3000/login](http://localhost:3000/login) and login with default credentials `admin/admin`.

#### Zipkin

Will be used for distributed tracing.
Visit [http://localhost:9411/zipkin](http://localhost:9411/zipkin), no login credentials are required.
More info
- [https://github.com/openzipkin/zipkin/tree/master/zipkin-server](https://github.com/openzipkin/zipkin/tree/master/zipkin-server).
- https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.micrometer-tracing

#### ELK (ElasticSearch, Logstash and Kibana)

Will contain the log aggregation for all services. Be sure to check the `logs` folder and see some `*.json.logs` files.
Visit [http://localhost:5601/app/discover](http://localhost:5601/app/discover) and click `Create data View`.
Set a name and define an index pattern `logback-*`, you will see logs entries in the main view.

More info
- [Deploying ELK inside Docker Container: Docker-Compose](https://medium.com/@lopchannabeen138/deploying-elk-inside-docker-container-docker-compose-4a88682c7643)
- [Creating Log Infrastructure with Elastic Stack and Docker Compose (Part 1)](https://arceister.medium.com/creating-log-infrastructure-with-elastic-stack-and-docker-compose-part-1-6195e8b9f0b2)
- [Send the Logs of a Java App to the Elastic Stack (ELK)](https://www.baeldung.com/java-application-logs-to-elastic-stack)

##### Troubleshooting

- If you don't see the message `"You have data in Elasticsearch. Now, create a data view."`, restart the docker image `lyrics-logstash`.
  Check the container logs and see if the events are being processed.

### Redis

Will be used for API rate limiting.

### Mongo DB

Will be used with users and lyrics collections.

### Kafka

Will be used for async event driven communication.

WIP
