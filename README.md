# resource-account
This is a "microservice" application intended to be part of a microservice architecture.

This application is configured for Service Discovery and Configuration with the JHipster-Registry. On launch, it will refuse to start if it is not able to connect to the JHipster-Registry at [http://localhost:8761](http://localhost:8761). For more information, read our documentation on [Service Discovery and Configuration with the JHipster-Registry][].

## Development

To start your application in the dev profile, simply run:

    ./mvnw



## Building for production

To optimize the autenticacao application for production, run:

    ./mvnw -Pprod clean package

To ensure everything worked, run:

    java -jar target/*.war



## Testing

To launch your application's tests, run:

    ./mvnw clean test


## Usage

Launch all your infrastructure by running: `docker-compose up -d`.

## Configured docker services

### Service registry and configuration server:
- [JHipster Registry](http://localhost:8761)

### Applications and dependencies:
- users (microservice application)
- users's mysql database

### Additional Services:

- [JHipster Console](http://localhost:5601)

[Service Discovery and Configuration with the JHipster-Registry]: https://www.jhipster.tech/documentation-archive/v5.1.0/microservices-architecture/#jhipster-registry
