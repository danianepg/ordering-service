
## Microservices Ordering System

The current project has the solution developed by [Daniane P. Gomes](https://www.linkedin.com/in/danianepg/).

## Introduction

The present application was developed in a microservice architecture with Spring Boot and Spring Cloud and contains 4 microservices.

The projects ```ordering-service``` and ```product-service``` are microservices that handle business logic and are structured according to the **MVC** (Model View Controller) pattern to allow more flexibility while handling business logic. It manipulates data and returns it in a navigable format due to compliance with **RESTful** and **HATEOAS** formats.

The project ```gateway-service``` is the one that should be exposed to the external world, because it communicates with all the available APIs and handles load balance. Lastly, ```eureka-service``` handles API discovery.

### Project Stack
 - Java 11
 - Maven 
 - SpringBoot 2.3
 - In memory database H2.
 - Eureka
 - Feign
 - Zuul
 
### How to run
* On folder /eureka-server

```mvnw.cmd clean install -DskipTests```

```mvn spring-boot:run```

* On folder /gateway-service

```mvnw.cmd clean install -DskipTests```

```mvn spring-boot:run```

* On folder /ordering-service

```mvnw.cmd clean install -DskipTests```

```mvn spring-boot:run```

* On folder /product-service

```mvnw.cmd clean install -DskipTests```

```mvn spring-boot:run```



### Project Structure
|Project  | Description | Running Port |
|--|--|--|
|eureka-server |Service discover  | 8761 |
|gateway-service |Gateway/load balance | 8000|
|ordering-service |Microservice for orders | 8080|
|product-service |Microservice for products | 8090|

### Endpoints
#### Orders
|Address| Description | METHOD |
|--|--|--|
|http://localhost:8000/ordering-service/api/orders/ |Find all orders  | GET |
|http://localhost:8000/ordering-service/api/orders/{id} |Find order by id  | GET |
|http://localhost:8000/ordering-service/api/orders/ |Create new order  | POST |
|http://localhost:8000/ordering-service/api/orders/ |Update order  | PUT|
|http://localhost:8000/ordering-service/api/orders/{id} | Delete order by id  | DELETE|
|http://localhost:8000/ordering-service/api/orders/updateOrderStatus/{id} | Update order status by id and move stock from Products microservice  | POST | 

#### Products
|Address| Description | METHOD |
|--|--|--|
|http://localhost:8000/product-service/api/products |Find all products | GET |
|http://localhost:8000/product-service/api/products{id} |Find product by id  | GET |
|http://localhost:8000/product-service/api/products |Create new product| POST |
|http://localhost:8000/product-service/api/products|Update product| PUT|
|http://localhost:8000/product-service/api/products{id} | Delete product by id  | DELETE|
|http://localhost:8000/product-service/api/products/addToStock/{productId} | Increase the stock quantity of a product  | POST | 
|http://localhost:8000/product-service/api/products/addToStock/{productId} | Decrease the stock quantity of a product  | POST | 

#### Database
|Service| Address| user | password |
|--|--|--|--|
|Orders |http://localhost:8080/h2-console/ | root | |
|Products|http://localhost:8090/h2-console | root | |

### Unit Tests
Check folders:
* ordering-service\src\test\java\com\danianepg\orderingservice
* product-service\src\test\java\com\danianepg\productservice

## Future implementations
* Create **Docker** images for each microservice and allow communications between them to simplify development environment.
* Enable **service configuration** for different environments (dev, qa, production).
* Define **call limits** for the application.
* Unit tests improvements and integration tests.

### Questions?
Email me: danianepg@gmail.com :)
