
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
cd BookWaveAPI
```

## Running the Application with Docker

After cloning the project, you can easily run the application using Docker. Follow these steps:

1. **Build and Start the Containers**: In the terminal, run the following command:

   ```bash
   docker-compose up --build
   ```

   This command will build the Docker images and start the PostgreSQL and Spring Boot containers.

2. **Access the Application**: Once the containers are running, you can access the application at [http://localhost:9090](http://localhost:9090).

3. **Stop the Application**: To stop the application, press `CTRL + C` in the terminal where the containers are running, or run:

   ```bash
   docker-compose down
   ```

## Postman Collection

To facilitate API testing, a Postman collection has been provided. Follow these steps to import the collection:

1. **Open Postman**: Launch Postman on your machine.

2. **Import the Collection**: 
   - Click on the **Import** button located in the top-left corner of the Postman application.
   - Select the **File** tab and choose the exported Postman collection file (e.g., `BookWaveAPI.postman_collection.json`) from your local system.
   - Click **Import**.

3. **Use the Collection**: 
   - After importing, you will see the collection in the left sidebar. Click on it to expand and view the available API requests.
   - You can now run the requests against the application running at [http://localhost:9090](http://localhost:9090).

4. **Export the Collection** (Optional): 
   - To share the collection or save it for later, click on the three dots next to the collection name and select **Export**.
   - Choose the desired format and save it to your system.

### Example Requests

- **Get All Books**: Send a `GET` request to `/api/books`.
- **Add a New Book**: Send a `POST` request to `/api/books` with the appropriate JSON body.

---

Feel free to customize this README further as per your project specifics or any additional details you might want to include. This format should provide clear and professional guidance for anyone looking to clone and test your application.
