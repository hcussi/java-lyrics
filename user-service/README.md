## Run Application

```bash
./gradlew :user-service:clean :user-service:build :user-service:bootRun
```

This will expose the REST endpoints available. Go to [http://localhost:8081/user-service/swagger-ui.html](http://localhost:8081/user-service/swagger-ui.html) to see the corresponding API docs (OpenAPI 3.0). 
Also, can check [http://localhost:8081/user-service/v3/api-docs](http://localhost:8081/user-service/v3/api-docs) to see Open API config JSON.

## Test Application

```bash
./gradlew :user-service:test
```

## Technological Stack

* Oracle JDK 21 with preview features
* Spring Boot v3 (Spring Framework v6)
  * Spring Web (REST)
  * Spring Data Mongo
  * Spring AOP
  * Spring Docs (OpenAPI 3.0)
* Kafka Publisher

### MongoDb

Create DB `usersdb` and `users` collections using Mongo Compass UI.
Create user for the DB.

```bash
docker exec -it lyrics-mongo bash
```

```bash
mongosh -u admin
use usersdb;
db.createUser({user: "lyricsUser", pwd: "s3c3rTpaZZ", roles : [{role: "readWrite", db: "usersdb"}]});
```

### Kafka Publisher

When a user is created send message to topic `org.hernan.cussi.lyrics-users.created`.

More info:
- [Implementing a Kafka Producer and Consumer using Spring Cloud Stream](https://medium.com/javarevisited/implementing-a-kafka-producer-and-consumer-using-spring-cloud-stream-d4b9a6a9eab1)
- [Unit Testing a Spring Cloud Stream Producer / Publisher](https://medium.com/@sumant.rana/unit-testing-a-spring-cloud-stream-producer-publisher-ecf39d29ea13)
