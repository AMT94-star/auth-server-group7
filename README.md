# Auth service - Webshop group 7
Authentication and authorization service for a webshop application built using a microservices architecture.

## Responsibilities
The auth service is responsible for new user registration, login, authentication and JWT generation. Securely storing hashed passwords, managing user roles, exposing public RSA keys for token verification.

## Authentication flow
User registers or logs in through frontend -> Frontend sends request to the API gateway -> Gateway forwards the authentication request to auth service -> Auth server authenticates and validates the user -> JWT token gets created and signed with private RSA key -> Auth service sends gateway the information back -> Gateway sets the JWT as a HTTPOnly cookie in the user's browser -> the browser sends the cookie on further requests to the gateway. Gateway extracts JWT and forwards to relevant micro service -> micro services verify JWT signature using auth services public key available at /auth/jwks

The private RSA key never leaves auth service and should never be shared or published. The JWT expires after 3600seconds.

## Tech tree
- Java 21
- Spring Boot
- Spring Security
- NIMBUS and JWT
- PostgreSQL
- H2 Database
- Maven
- Docker
- Docker Hub
- Render
- GitHub Actions
- Swagger / OpenAPI

## Features
- User registration and login
- JWT authentication
- RSA signed access tokens
- JWKS endpoint for public key verification
- Role based authorization (user and admin)
- BCrypt password hashing
- Database persistence
- Swagger API
- Docker container
- Render deployment
- CI with Github Actions

## Databases
The application uses various databases depending on the enivornment. H2 for local environment and PostgreSQL for production. Their connection details are configured through environment variables.

### Local H2
- spring.datasource.url=jdbc:h2:file:./data/authdb
- spring.datasource.username=sa
- spring.datasource.password=

The H2 database allows a developer to run the service without postgresql. The database stores id, username, BCrypt password and user role.

## API endpoints
| Method | Endpoint | Functionality
|---|---|---|
| POST | `/auth/register` | registers a new user |
| POST | `/auth/login` | authenticates a user and generates JWT |
| GET | `/auth/jwks` | retrieves public RSA keys for JWT verification |
| POST | `/appusers/admins` | creates new admin accounts, admin accessibility only |
| GET | `/appusers` | retrieves registered accounts, admin accessibility only |

## Environment variables
The application uses environment variables to configure database connections, RSA keys and adminstrate credentials.
| Variable name | Functionality |
|---|---|
| `PORT` | application port(default is 9000) |
| `RENDER_URL` | postgresql jdbc connection url |
| `RENDER_USER` | postgresql username |
| `RENDER_PWD` | pastgresql password |
| `JWT_PUBLIC_KEY` | public RSA key used for JWT verification |
| `JWT_PRIVATE_KEY` | private RSA key used for signing JWTs |
| `ADMIN_USERNAME` | administrator username |
| `ADMIN_PASSWORD` | administrator password |
| `SPRING_PROFILES_ACTIVE` | active spring profile |

## JWT Config
| Property name | Production | Local |
|---|---|---|
| `app.jwt.issuer` | https://auth-server-group7.onrender.com  | http://localhost:9000 |
| `app.jwt.expiration-minutes` | 60 | 60 |
| `app.jwt.key-id` | webshop-auth-key-1 | webshop-auth-key-1 |

Do not commit password, keys or other sensitive information directly to the repository.

## Run locally
- Clone the repository
- Configure environment variables: ADMIN_PASSWORD, ADMIN_USERNAME, SPRING_PROFILES_ACTIVE, JWT_PUBLIC_KEY, JWT_PRIVATE_KEY
- Generate RSA key pairs for signing and verifying JWTs. Do not commit private key to GitHub
- Start the application using the local Spring profile.
- Windows: mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local  macOS/Linux: ./mvnw spring-boot:run -Dspring-boot.run.profiles=local

## Local access to the application
- Local base url: http://localhost:9000
- Swagger: http://localhost:9000/swagger-ui/index.html
- JWKS endpoint: http://localhost:9000/auth/jwks
- H2 database accessibility: http://localhost:9000/h2-console

## Production access to the application
- Base Url: https://auth-server-group7.onrender.com
- Swagger: https://auth-server-group7.onrender.com/swagger-ui/index.html
- JWKS endpoint: https://auth-server-group7.onrender.com/auth/jwks
- Docker hub: https://hub.docker.com/r/94059102/auth-server-group7

## Relevant repositories
The webshop consists of several repositories connected in a loose scalable way.
- Auth Service: https://github.com/AMT94-star/auth-server-group7
- Product Service: https://github.com/johan969/product-service
- Order Service: https://github.com/eritho91/order-service
- Email Service: https://github.com/elquinox/email-microservice
- API Gateway Service: https://github.com/eritho91/gateway
- Frontend: https://github.com/johan969/webshop-frontend

## Project information
This service has been developed by me as part of a group project in the Java developer program at ITHS in Stockholm, Sweden.
