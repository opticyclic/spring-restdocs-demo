# Spring REST Docs Demo

This project demonstrates how to document your Spring REST API using [Spring Rest Docs](https://github.com/spring-projects/spring-restdocs/) and how to generate an OpenAPI spec during the build.

## REST API

A simple Spring Boot REST API is created to in order to demonstrate the documentation. If you want to interact with it you can do:

**GET**

    curl 'http://localhost:8080/api/v1/stocks'
 
**POST**
 
    curl 'http://localhost:8080/api/v1/stocks' -H 'Content-Type: application/json' --data $'{"id":1,"name": "IBM", "price":"128.27"}'
