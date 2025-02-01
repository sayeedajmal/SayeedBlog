# Docker Setup for FullStack Java React Blog Project

      This project uses Docker to containerize and manage various services of the FullStack Java React Blog Project. Docker Compose is used to orchestrate these services, including microservices for posts, users, and notifications, as well as the frontend application.

## Project Overview

This Docker setup includes the following services:

1. **author-service**: Manages author-related operations.
2. **blog-discovery**: Acts as a service registry for discovering microservices.
3. **blog-gateway**: Serves as an API gateway to route requests.
4. **post-service**: Manages posts, comments, and likes.

## Directory Structure

The project directory is structured as follows:

```
project-root/
├── AuthorService/
│   ├── Dockerfile
│   └── .env.list
├── BlogDiscovery/
│   ├── Dockerfile
│   └── .env.list
├── BlogGateway/
│   ├── Dockerfile
│   └── .env.list
├── PostService/
│   ├── Dockerfile
│   └── .env.list
└── docker-compose.yml
```

- **AuthorService/**: Contains the Dockerfile and environment file for the author service.
- **BlogDiscovery/**: Contains the Dockerfile and environment file for the service discovery.
- **BlogGateway/**: Contains the Dockerfile and environment file for the API gateway.
- **PostService/**: Contains the Dockerfile and environment file for the post service.
- **docker-compose.yml**: Defines the configuration for Docker Compose to manage the services.

## Getting Started

To run the project using Docker, follow these steps:

1. **Navigate to the Project Root Directory**

   Open your terminal and go to the directory containing `docker-compose.yml`:

   ```bash
   cd /path/to/project-root
   ```

2. **Start Services**

   To build and start all services, run:

   ```bash
   docker-compose up
   ```

   To start the services in detached mode (background), use:

   ```bash
   docker-compose up -d
   ```

3. **Access the Services**

   Each service will be available on the following ports:

   - **author-service**: (Map to your local port, check `docker-compose.yml` for port details)
   - **blog-discovery**: [http://localhost:8761](http://localhost:8761)
   - **blog-gateway**: [http://localhost:8080](http://localhost:8080)
   - **post-service**: (Map to your local port, check `docker-compose.yml` for port details)
   - Create a `.env` file in the `FrontEnd` directory with the following content:
      ```env
      REACT_APP_AUTHOR="http://localhost:8080/api/authorservice"
      REACT_APP_POST="http://localhost:8080/api/postservice"
      ```
4. **Stop Services**

   To stop and remove all running services, run:

   ```bash
   docker-compose down
   ```

## Troubleshooting

- **View Logs**: To check logs for a specific service, use:

  ```bash
  docker-compose logs <service-name>
  ```

  Replace `<service-name>` with the name of the service (e.g., `author-service`).

- **Check Container Status**: To see the status of all containers, run:

  ```bash
  docker-compose ps
  ```

- **Rebuild Images**: If you make changes to the Dockerfiles or `docker-compose.yml`, rebuild the images using:

  ```bash
  docker-compose build
  ```

For more information, refer to Docker's [official documentation](https://docs.docker.com/) or seek help from relevant community forums.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
```

Feel free to adjust any specifics or add more details based on your actual setup and requirements.