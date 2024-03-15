## Run Application

```bash
./gradlew :api-gateway-service:clean :api-gateway-service:build :api-gateway-service:bootRun
```

This will expose the REST endpoints available and the actuator: 
- health: `/actuator/health`
- gateway routes: `/actuator/gateway/routes` 
- gateway requests: `/actuator/metrics/spring.cloud.gateway.requests`
- prometheus: `/actuator/prometheus`
  
Go to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to see the corresponding API docs (OpenAPI 3.0).
Also, can check [http://localhost:8080/v3/api-docs/swagger-config](http://localhost:8080/v3/api-docs/swagger-config) to see Open API config JSON.

## Test Application

```bash
./gradlew :api-gateway-service:test
```

## Technological Stack

* Oracle JDK 21 with preview features
* Spring Boot v3 (Spring Framework v6)
  * Spring Actuator (Prometheus)
* Spring Cloud Gateway
  * Circuit Breaker (resilience4j)
 
More info:
- [Getting Started with Spring Cloud Gateway — Part One](https://medium.com/@jayaramanan.kumar/getting-started-with-spring-cloud-gateway-part-one-c2177b32d01d)
