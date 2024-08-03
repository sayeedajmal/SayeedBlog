# HTTP Request Processing Flow

1. **Request Received**

   - **Class Involved:** `JWTRequestFilter`

2. **JWT Request Filter Activated**

   - **Class Involved:** `JWTRequestFilter`
   - **Action:** Extracts JWT from the request header.

3. **JWT Validation**

   - **Class Involved:** `JWTUtil`
   - **Action:** Validates the JWT for authenticity and expiration.

4. **User Authentication**

   - **Class Involved:** `AuthorService`
   - **Action:** Loads user details based on JWT claims.

5. **Set Authentication in Security Context**

   - **Class Involved:** `JWTRequestFilter`
   - **Action:** Sets authentication details in the Spring Security context.

6. **Authorization Check**

   - **Class Involved:** `SecurityConfig`
   - **Action:** Checks if the authenticated user has the required permissions to access the resource.

7. **Controller Method Execution**
   - **Class Involved:** Controller (e.g., `AuthorController`)
   - **Action:** Processes the request and generates the response.
