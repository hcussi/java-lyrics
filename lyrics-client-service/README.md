## Run Application

```bash
./gradlew :lyrics-client-service:clean :lyrics-client-service:build :lyrics-client-service:bootRun
```

This will expose the REST endpoints available. Go to [http://localhost:8087/lyrics-client-service/swagger-ui.html](http://localhost:8087/lyrics-client-service/swagger-ui.html) to see the corresponding API docs (OpenAPI 3.0). 
Also, can check [http://localhost:8087/lyrics-client-service/v3/api-docs](http://localhost:8081/user-service/v3/api-docs) to see Open API config JSON.

## Test Application

```bash
./gradlew :lyrics-client-service:test
```

## Technological Stack

* Oracle JDK 21 with preview features
* Spring Boot v3 (Spring Framework v6)
  * Spring Web (REST)
  * Spring AOP
  * Spring Docs (OpenAPI 3.0)
