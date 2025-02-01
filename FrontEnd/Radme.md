# Project Setup

## Root Project

This project uses Docker Compose to manage services.

### Prerequisites

- Docker
- Docker Compose

### Getting Started

1. Clone the repository:
  ```sh
  git clone <repository-url>
  cd <repository-directory>
  ```

2. Start the services using Docker Compose:
  ```sh
  docker-compose up
  ```

## SayeedBlog

The `sayeedblog` directory contains the frontend application.

### Prerequisites

- Node.js
- npm

### Getting Started

1. Navigate to the `sayeedblog` directory:
  ```sh
  cd sayeedblog
  ```

2. Install the dependencies:
  ```sh
  npm install
  ```

3. Create a `.env` file in the `sayeedblog` directory with the following content:
  ```env
  REACT_APP_AUTHOR="http://localhost:8080/api/authorservice"
  REACT_APP_POST="http://localhost:8080/api/postservice"
  ```

4. Start the application:
  ```sh
  npm start
  ```

Your application should now be running.