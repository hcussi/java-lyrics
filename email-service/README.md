## Run Application

```bash
./gradlew :email-service:clean :email-service:build :email-service:bootRun
```

## Test Application

```bash
./gradlew :email-service:test
```

## Technological Stack

* Oracle JDK 21 with preview features
* Spring Boot v3 (Spring Framework v6)
  * Spring Web (REST)
* Kafka Consumer

### Kafka Consumer

When a user is created receive message from topic `org.hernan.cussi.lyrics-users.created`.

More info:
- [Implementing a Kafka Producer and Consumer using Spring Cloud Stream](https://medium.com/javarevisited/implementing-a-kafka-producer-and-consumer-using-spring-cloud-stream-d4b9a6a9eab1)
