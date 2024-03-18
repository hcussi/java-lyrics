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

#### Email microservice

Will provide the functionality to send emails. Check the particular `README.md` for more info.

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
Visit [http://localhost:9099/](http://localhost:9099/) and check that the cluster has been connected successfully. 

WIP
