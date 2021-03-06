# MyRetail Case Study
MyRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps. 
The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller. 
Your goal is to create a RESTful service that can retrieve product and price details by ID. The URL structure is up to you to define, but try to follow some sort of logical convention.

## Usecases:
Build an application that performs the following actions: 
•	Responds to an HTTP GET request at /products/{id} and delivers product data as JSON (where {id} will be a number. 

•	Example product IDs: 15117729, 16483589, 16696652, 16752456, 15643793) 

•	Example response: {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}

•	Performs an HTTP GET to retrieve the product name from an external API. (For this exercise the data will come from redsky.target.com, but let’s just pretend this is an internal resource hosted by myRetail)  

•	Example: http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics

•	Reads pricing information from a NoSQL data store and combines it with the product id and name from the HTTP request into a single response.  

•	BONUS: Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store.  

# Build:
Make sure JAVA_HOME points to JDK 11 before running maven build. This application can be built as a spring boot fat JAR or a docker 
image using jib-maven-plugin which creates efficient layered docker image for spring boot apps.

## To build spring boot fat JAR. It will also run all the tests:
```./mvnw clean install```

## To use docker first install Docker:
Follow below link to install docker.

```https://docs.docker.com/install/```

## Install Docker Compose:
```https://docs.docker.com/compose/install/```

## Create docker image. It will also run all the tests:

```./mvnw verify jib:dockerBuild```

# Run:
## Standalone:
Please run MongoDB locally at port 27017.

Use below command to run myRetail application

```java -jar target/product-0.0.1-SNAPSHOT.jar```

## Docker Compose:
If docker image is already built as mentioned above use below command to run both mongo and myretail product app docker containers together 

```docker-compose up```

# Test:
To test API using Swagger UI access below url when application is successfully started.

```http://localhost:8080/swagger-ui.html```

For testing offline, there is a mock product metadata API which can be used if redsky endpoint is not reachable by changing 
the corresponding entry in application.properties

# Health Checking
Health checking actuator endpoint available at
```http://localhost:8080/actuator/health```
