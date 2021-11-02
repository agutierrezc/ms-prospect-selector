<h1 align="center"> ms-select-prospect </h1> <br>

<p align="center">
  ms-select-prospect is a java microservice to filter leads and create prospects from them.
</p>

## Table of Contents

- [Introduction](#introduction)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Testing](#testing)

## Introduction

This microservices creates a prospect using lead's information, this information must match with the imformation store at the nacional registry. Also, the Lead must not have judicial recods from nacional archives. 

During this process we will get the current Lead's score against the CRM System, this score needs to be grater than 60.

This development is using a clean architecture, the adapters are like a mocks, using random timers against the Main Thread to simulate some latency. 

The data used for National Registry and National Archives adapters is stored in a Map of <String, Object> type. To change or add more data go to __NacionalRegistryInMemoryAdpaterConfig__ and __NacionalArchivesInMemoryAdapterConfig__. It is possbile to change the source of the data changing adapters.

## Requirements

The application can be run locally or in a docker container, the requirements setup are listed below.

### Local
* [Java 11 SDK or greater](https://www.oracle.com/java/technologies/downloads/)
* [Gradle 7.2 or greater](https://gradle.org/install/)

### Docker
* [Docker](https://www.docker.com/get-docker)

## Quick Start

The default value in the __application.yml__ file is set to connect to EGO running locally on its default port `8002`.

### Run Local

```bash
$ gradle bootRun
```

Application will run by default on port `8002`

Configure the port by changing `server.port` in __application.yml__

### Run Docker

First build the image:
```bash
$ docker-compose build
```

When ready, run it:
```bash
$ docker-compose up
```

Application will run by default on port `8002`

Configure the port by changing `services.api.ports` in __docker-compose.yml__. Port 8002 was used by default so the value is easy to identify and change in the configuration file.

## Testing

URL
```
http://localhost:8002/prospect-selector/api/v1/prospect/
```
METHOD
```
POST
```

#### Lead with judicial Record

```json
{
    "leadId":"322a2686-c58d-43c8-b1ac-d05b3f797c20",
    "documentId": "1012023314",
    "name":"Javier",
    "lastName":"Perez",
    "birthdate": "1985-03-27",
    "email":"javier@email.com"
}
```
#### Lead's information does not match

```json
{
    "leadId":"322a2686-c58d-43c8-b1ac-d05b3f797c20",
    "documentId": "1012023314",
    "name":"Javier",
    "lastName":"Perez",
    "birthdate": "1985-03-27",
    "email":"javier@email.com"
}
```

#### success exmaple

score is random so may be you shou try many times

```json
{
    "leadId":"322a2686-c58d-43c8-b1ac-d05b3f797c20",
    "documentId": "1018021365",
    "name":"Andres",
    "lastName":"Gomez",
    "birthdate": "1986-04-26",
    "email":"andres@email.com"
}
```


