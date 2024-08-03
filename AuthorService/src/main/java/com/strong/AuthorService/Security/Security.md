# JWT Authentication Setup in Spring Security

## 1. Create a User Entity

- Implement `UserDetails` in `AUTHOR` entity.

## 2. Create a User Details Service

- Implement `UserDetailsService` in a `AuthorService` class to load user-specific data.

## 3. Configure Spring Security

- Create a `SecurityConfig` class.
- Add a method called `securityFilterChain` with `HttpSecurity http` parameter
- Use `userDetailsService()` method to configure `UserDetailsService` in `securityFilterChain` method.
- Add `JWTRequestFilter` using `addFilterBefore()` in the `SecurityFilterChain` method.
- Add `sessionManagement` to `Stateless` by using `SessionCreationPolicy` and return with `http.build()`.
- Add a method annotated with `@Bean` for `PasswordEncoder`.
- Add another method annotated with `@Bean` for `AuthenticationManager`.

## 4. Create JWTRequestFilter

- Extend `OncePerRequestFilter`.
- Implement JWT parsing, authentication, and authorization in `doFilterInternal()`.

## 5. Create JWT Utility Class

- Implement methods for:
  - JWT creation
  - JWT validation
  - JWT updates (e.g., refresh tokens)

## 6. Create Authentication Controller

- Implement an endpoint (e.g., `/api/authenticate`) to handle login.
- Authenticate user and generate JWT token using `JwtUtils`.

## 7. Integrate with Spring Security

- Ensure your security configuration uses the `JWTRequestFilter` for requests.
