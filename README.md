# LangLearner

[![Java CI with Maven](https://github.com/MargotHTeunisse/LangLearnerApp/actions/workflows/maven.yml/badge.svg)](https://github.com/MargotHTeunisse/LangLearnerApp/actions/workflows/maven.yml)
[![Docker Image CI](https://github.com/MargotHTeunisse/LangLearnerApp/actions/workflows/docker-image.yml/badge.svg)](https://github.com/MargotHTeunisse/LangLearnerApp/actions/workflows/docker-image.yml)

This is a project where I build a language learning app, called LangLearner, using Java with Spring Boot®. 

A more detailed discussion of this project can be found on my [personal blog](https://margothteunisse.github.io/posts/building-a-language-learning-application-with-java-and-spring-boot/). 

## Structure

This project is built using Maven and has a multi-module structure. It currently comprises three modules:

### core

The **core** module contains the domain logic of the application, implemented via Spring Boot, and associated tests. It contains a ``Deck`` component from which cards can be drawn, based on a given vocabulary. Multiple vocabulary implementations are supported. The current implementations are:

- text: the vocabulary is read out from a csv file.
- database: the vocabulary is read out from a database connection. In its current implementation, this is an embedded H2 database, which is populated based on the files schema.sql and data.sql under resources.

### console-app

The **console-app** module contains a minimal implementation of the functionalities in **core**. 

### web-services

The **web-services** module contains a (non-REST) web API, which can be used to call the functionalities in **core** remotely. It also contains HTML, CSS and JavaScript resources which are used to construct a simple frontend for the API.

### rest-services

The **rest-services** module is similar to **web-services**, but uses a slightly different architecture. In **web-services**, the domain logic is fully on the server side, and as a result the API is stateful. In **rest-services**, the deck logic is on the client side, and the API is stateless, as it is only responsible for fetching resources from the vocabulary.

## Deployment

A web showcase of this application is available via Render at [langlearnerapp.onrender.com](http://langlearnerapp.onrender.com). This deployment is based on a Docker container which uses a fixed vocabulary.

---------------------
Spring is a trademark of Broadcom Inc and its subsidiaries.
