# BookWave Project

BookWave is a Spring Boot application designed for managing and sharing book-related data seamlessly. This README provides instructions on how to clone, run, and test the application using Docker and Postman.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Cloning the Project](#cloning-the-project)
- [Running the Application with Docker](#running-the-application-with-docker)
- [Postman Collection](#postman-collection)

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- [Docker](https://www.docker.com/get-started)
- [Git](https://git-scm.com/downloads)

## Cloning the Project

To clone the project, open your terminal and run the following command:

```bash
git clone https://github.com/TarkhanGurbanli/BookWaveAPI.git
cd BookWave

## Running the Application with Docker
After cloning the project, you can easily run the application using Docker. Follow these steps:
  1. Build and Start the Containers: In the terminal, run the following command:
  ```bash
  docker-compose up --build
  This command will build the Docker images and start the PostgreSQL and Spring Boot containers.
  2. Access the Application: Once the containers are running, you can access the application at http://localhost:9090
  3. Stop the Application: To stop the application, press CTRL + C in the terminal where the containers are running, or run:
  ```bash
  docker-compose down

## Postman Collection
