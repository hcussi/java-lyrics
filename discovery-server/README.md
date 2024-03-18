## Run Application

```bash
./gradlew :discovery-server:clean :discovery-server:build :discovery-server:bootRun
```

Go to [Eureka Server](http://http://localhost:8761/) to check how services are being registered.

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
- [Microservices with Spring Boot 3 and Spring Cloud](https://piotrminkowski.com/2023/03/13/microservices-with-spring-boot-3-and-spring-cloud/)
