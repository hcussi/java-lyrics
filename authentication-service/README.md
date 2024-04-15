## Run Application

```bash
./gradlew :authentication-service:clean :authentication-service:build :authentication-service:bootRun
```

This will expose the REST endpoints available. Go to [http://localhost:8085/authentication-service/swagger-ui.html](http://localhost:8085/authentication-service/swagger-ui.html) to see the corresponding API docs (OpenAPI 3.0). 
Also, can check [http://localhost:8085/authentication-service/v3/api-docs](http://localhost:8085/authentication-service/v3/api-docs) to see Open API config JSON.

## Test Application

```bash
./gradlew :authentication-service:test
```

## Technological Stack

* Oracle JDK 21 with preview features
* Spring Boot v3 (Spring Framework v6)
  * Spring Web (REST)
  * Spring AOP
  * Spring Docs (OpenAPI 3.0)
  * Spring Data Mongo
  * JWT

### MongoDb

Create `credentialsdb` DB and user.

```bash
docker exec -it lyrics-mongo bash
mongosh -u admin
use credentialsdb;
db.createUser({user: "lyricsUser", pwd: "s3c3rTpaZZ", roles : [{role: "readWrite", db: "credentialsdb"}]});
```

### Kafka Consumer

When a user is created receive message from topic `org.hernan.cussi.lyrics-users.created`.
