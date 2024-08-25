# API Documentation

## Comment Controller

**Base URL:** **`/api/comment`**

### Endpoints

#### **POST** `/api/comment`

- **Description:** Creates a new comment for a specified post.
- **Request Body:**
  - `postId` (String) - The ID of the post for which the comment is being created.
  - `author` (AUTHOR) - The author of the comment.
  - `content` (String) - The content of the comment.
  - `createdAt` (String, ISO8601 DateTime) - The creation date and time of the comment.
- **Response:**
  - **Body:** The created comment object.
  - **HTTP Status Code:** `201 Created`
- **Security:** `ADMIN` or `AUTHOR` Authority required.
- **Throws:** `BlogException` if there is an error creating the comment.

#### **GET** `/api/comment`

- **Description:** Retrieves all comments for a specific post.
- **Parameters:**
  - `postId` (String) - The ID of the post for which comments are to be retrieved.
- **Response:**
  - **Body:** List of comments for the specified post.
  - **HTTP Status Code:** `200 OK`
- **Security:** Publicly accessible.
- **Throws:** `BlogException` if an error occurs while retrieving comments.

#### **GET** `/api/comment/{cmtId}`

- **Description:** Retrieves a comment by its ID.
- **Parameters:**
  - `Path Variable:` `cmtId` (String) - The ID of the comment to be retrieved.
- **Response:**
  - **Body:** The comment object.
  - **HTTP Status Code:** `200 OK`
- **Security:** Publicly accessible.
- **Throws:** `BlogException` if the comment with the specified ID is not found.

#### **GET** `/api/comment/user/{userId}`

- **Description:** Retrieves all comments made by a specific user.
- **Parameters:**
  - `Path Variable:` `userId` (String) - The ID of the user whose comments are to be retrieved.
- **Response:**
  - **Body:** List of comments made by the specified user.
  - **HTTP Status Code:** `200 OK`
- **Security:** Publicly accessible.
- **Throws:** `BlogException` if an error occurs while retrieving comments.

#### **DELETE** `/api/comment/{cmtId}`

- **Description:** Deletes a comment by its ID.
- **Parameters:**
  - `Path Variable:` `cmtId` (String) - The ID of the comment to be deleted.
- **Response:**
  - **HTTP Status Code:** `204 No Content`
- **Security:** `ADMIN` role required.
- **Throws:** `BlogException` if the comment with the specified ID is not found.

#### **GET** `/api/comment/count`

- **Description:** Counts the number of comments for a specific post.
- **Parameters:**
  - `postId` (String) - The ID of the post for which the comment count is to be retrieved.
- **Response:**
  - **Body:** The count of comments for the specified post.
  - **HTTP Status Code:** `200 OK`
- **Security:** Publicly accessible.
- **Throws:** `BlogException` if an error occurs while counting comments.

---

## ImageUpload Controller

**Base URL:** **`/api/images`**

### Endpoints

#### **POST** `/api/images/upload`

- **Description:** Uploads images.
- **Request Body:**
  - `files` (MultipartFile[]) - Array of files to be uploaded.
- **Response:**
  - **Body:** List of IDs or URLs of the uploaded images.
  - **HTTP Status Code:** `200 OK`
- **Security:** `ADMIN` or `AUTHOR` Authority required.
- **Throws:** `IOException` if there is an error during file upload.

#### **GET** `/api/images/{fileId}`

- **Description:** Retrieves an image by its ID.
- **Parameters:**
  - `Path Variable:` `fileId` (String) - The ID of the image to be retrieved.
- **Response:**
  - **Body:** The image file.
  - **HTTP Status Code:** `200 OK`
- **Security:** Publicly accessible.
- **Throws:** `BlogException` if the image with the specified ID is not found.

#### **GET** `/api/images`

- **Description:** Lists all uploaded images.
- **Response:**
  - **Body:** List of all uploaded images.
  - **HTTP Status Code:** `200 OK`
- **Security:** `ADMIN` or `AUTHOR` Authority required.

#### **DELETE** `/api/images/{fileId}`

- **Description:** Deletes an image by its ID.
- **Parameters:**
  - `Path Variable:` `fileId` (String) - The ID of the image to be deleted.
- **Response:**
  - **HTTP Status Code:** `200 OK`
- **Security:** `ADMIN` or `AUTHOR` Authority required.
- **Throws:** `BlogException` if the image with the specified ID is not found.

---

## Like Controller

**Base URL:** **`/api/like`**

### Endpoints

#### **POST**

- **Description:** Toggles a like for a specific post by a user.
- **Request Body:**
  - `Like` (Object) - The Like Object.
- **Response:**
  - **Body:** The updated like status.
  - **HTTP Status Code:** `200 OK`
- **Security:** `ADMIN` or `AUTHOR` Authority required.
- **Throws:** `BlogException` if an error occurs while toggling the like.

#### **GET** `/api/like/count`

- **Description:** Counts the number of likes for a specific post.
- **Parameters:**
  - `postId` (String) - The ID of the post for which the like count is to be retrieved.
- **Response:**
  - **Body:** The count of likes for the specified post.
  - **HTTP Status Code:** `200 OK`
- **Security:** Publicly accessible.
- **Throws:** `BlogException` if an error occurs while counting likes.

---

## Post Controller

**Base URL:** **`/api/post`**

### Endpoints

#### **POST** `/api/post`

- **Description:** Creates a new post.
- **Request Body:**
  - `title` (String) - The title of the post.
  - `content` (String) - The content of the post.
  - `author` (AUTHOR) - The author of the post.
  - `createdAt` (String, ISO8601 DateTime) - The creation date and time of the post.
- **Response:**
  - **Body:** The created post object.
  - **HTTP Status Code:** `201 Created`
- **Security:** `ADMIN` or `AUTHOR` Authority required.
- **Throws:** `BlogException` if there is an error creating the post.

#### **GET** `/api/post/{postId}`

- **Description:** Retrieves a post by its ID.
- **Parameters:**
  - `Path Variable:` `postId` (String) - The ID of the post to be retrieved.
- **Response:**
  - **Body:** The post object.
  - **HTTP Status Code:** `200 OK`
- **Security:** Publicly accessible.
- **Throws:** `BlogException` if the post with the specified ID is not found.

#### **GET** `/api/post`

- **Description:** Retrieves all posts.
- **Response:**
  - **Body:** List of all posts.
  - **HTTP Status Code:** `200 OK`
- **Security:** Publicly accessible.

#### **PATCH** `/api/post/{postId}`

- **Description:** Updates an existing post by its ID.
- **Parameters:**
  - `Path Variable:` `postId` (String) - The ID of the post to be updated.
- **Request Body:**
  - `title` (String) - The new title of the post (optional).
  - `content` (String) - The new content of the post (optional).
  - `updatedAt` (String, ISO8601 DateTime) - The updated date and time of the post.
- **Response:**
  - **Body:** The updated post object.
  - **HTTP Status Code:** `200 OK`
- **Security:** `ADMIN` or `AUTHOR` Authority required.
- **Throws:** `BlogException` if the post with the specified ID is not found.

#### **DELETE** `/api/post/{postId}`

- **Description:** Deletes a post by its ID.
- **Parameters:**
  - `Path Variable:` `postId` (String) - The ID of the post to be deleted.
- **Response:**
  - **HTTP Status Code:** `204 No Content`
- **Security:** `ADMIN` or `AUTHOR` Authority required.
- **Throws:** `BlogException` if the post with the specified ID is not found.
