# API Documentation

## Actuator Controller

**Base URL:** `/api`

### Endpoints

#### GET /actuator/health

- **Description:** Checks the health status of the service.
- **Method:** `GET`
- **Response:**
  - **Body:** `{"status": "UP"}`
  - **HTTP Status Code:** `200 OK`

---

## Author Controller

**Base URL:** `/api/author`

### Endpoints

#### GET /all

- **Description:** Retrieves a list of all authors.
- **Method:** `GET`
- **Response:**
  - **Body:** List of authors
  - **HTTP Status Code:** `200 OK`
- **Access:** Restricted to users with the `'ADMIN'` authority.

#### GET /{id}

- **Description:** Retrieves an author by their ID.
- **Method:** `GET`
- **Parameters:**
  - `Path Variable:` `id` (String) - The unique identifier of the author.
- **Response:**
  - **Body:** Author object
  - **HTTP Status Code:** `200 OK`
- **Access:** Restricted to users with the `'AUTHOR'` or `'ADMIN'` authority.
- **Throws:** `AuthorException` if the author with the specified ID is not found.

#### POST /signup

- **Description:** Creates a new author.
- **Method:** `POST`
- **Parameters:**
  - `Request Parameters:`
    - `name` (String) - The name of the author.
    - `email` (String) - The email of the author.
    - `bio` (String) - The biography of the author.
    - `password` (String) - The password for the author account.
    - `profilePicture` (MultipartFile) - The profile picture of the author.
- **Response:**
  - **Body:** Success message
  - **HTTP Status Code:** `201 Created`
- **Access:** Accessible to everyone (`permitAll()`).
- **Throws:** `AuthorException` if there is an error during author creation.

#### POST /login

- **Description:** Authenticates an author and logs them in.
- **Method:** `POST`
- **Parameters:**
  - **Request Body:** Author object containing login credentials.
- **Response:**
  - **Body:** Authentication token
  - **HTTP Status Code:** `200 OK`
- **Access:** Accessible to everyone (`permitAll()`).
- **Throws:** `AuthorException` if there is an error during authentication.

#### PUT /{id}

- **Description:** Updates an existing author's details.
- **Method:** `PUT`
- **Parameters:**
  - `Path Variable:` `id` (String) - The unique identifier of the author to be updated.
  - **Request Body:** Author object containing updated details.
- **Response:**
  - **Body:** Updated author object
  - **HTTP Status Code:** `200 OK` if successful, `404 Not Found` if the author is not found.
- **Access:** Restricted to users with the `'AUTHOR'` authority.

#### DELETE /{id}

- **Description:** Deletes an author by their ID.
- **Method:** `DELETE`
- **Parameters:**
  - `Path Variable:` `id` (String) - The unique identifier of the author to be deleted.
- **Response:**
  - **HTTP Status Code:** `204 No Content`
- **Access:** Restricted to users with the `'ADMIN'` authority.

#### POST /refresh-token

- **Description:** Refreshes an authentication token.
- **Method:** `POST`
- **Parameters:**
  - **Request:** The HTTP request containing the current token information.
- **Response:**
  - **Body:** New authentication token
  - **HTTP Status Code:** `200 OK`
- **Access:** Restricted to users with the `'AUTHOR'` or `'ADMIN'` authority.

---

## Image Controller

**Base URL:** `/api/images`

### Endpoints

#### GET /{fileId}

- **Description:** Retrieves an image by its file ID.
- **Method:** `GET`
- **Parameters:**
  - **Path Variable:** `fileId` (`String`) - The unique identifier of the image to be retrieved.
- **Response:**
  - **Body:** Image data as `InputStreamResource`
  - **HTTP Status Code:** `200 OK`
- **Access:** Accessible to everyone (`permitAll()`).
- **Throws:**
  - **IOException** if there is an error reading the image file.
  - **AuthorException** if the image cannot be found or accessed.

#### DELETE /{fieldId}

- **Description:** Removes an image by its file ID.
- **Method:** `DELETE`
- **Parameters:**
  - **Path Variable:** `fieldId` (`String`) - The unique identifier of the image to be deleted.
- **Response:**
  - **HTTP Status Code:** `200 OK`
- **Access:** Restricted to users with the `'AUTHOR'` or `'ADMIN'` authority.
- **Throws:** `AuthorException` if there is an error during image deletion.

---
