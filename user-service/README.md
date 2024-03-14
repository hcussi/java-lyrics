## Run Application

```bash
./gradlew :user-service:clean :user-service:build :user-service:bootRun
```

This will expose the REST endpoints available. Go to [http://localhost:8081/api/docs/swagger-ui](http://localhost:8081/api/docs/swagger-ui) to see the corresponding API docs (OpenAPI 3.0). 
Also, can check [http://localhost:8081/api/docs](http://localhost:8081/api/docs) to see Open API config JSON.
Use the basic auth credentials set in the `.env` file.

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
