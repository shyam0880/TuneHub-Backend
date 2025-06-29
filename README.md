# 🎶 TuneHub Backend

A music streaming web application backend built with **Java, Spring Boot**, and **PostgreSQL**, providing user and admin management, song and playlist operations, artist support, and secure authentication via a RESTful API. This backend powers the [**TuneHub Frontend**](https://github.com/shyam0880/TuneHub-Frontend) React application.
  
👉 Explore the [TuneHub Frontend](https://github.com/shyam0880/TuneHub-Frontend) for the UI and client-side logic.

---

## 📦 Tech Stack

- ⚙️ Java 21 (with Virtual Threads)
- ☕ Spring Boot 3.x
- 🗄️ PostgreSQL
- 🌐 Spring Security + JWT (Cookie-based)
- ☁️ Cloudinary (song/image uploads)
- 🔐 Role-based access (USER / ADMIN)
- 🧵 HikariCP (connection pooling)
- 📦 Spring Data JPA (ORM)
- 📑 Swagger / OpenAPI 3.0

---
## 📚 Dependencies

- Spring Boot Starter Web  
- Spring Boot Starter Data JPA  
- PostgreSQL Driver  
- Spring Security  
- JSON Web Token (`jjwt`)  
- Cloudinary Java SDK (v1.38.0)  
- Razorpay Java SDK (v1.4.8)  
- Spring Boot DevTools  
- Spring Boot Starter Test  
- springdoc-openapi (Swagger v2)

---

## 📑 Project Structure
```bash
src/
 └── main/
     ├── java/
     │   └── com/
     │       └── tunehub/
     │           ├── config/          # Security, Swagger, Cloudinary config
     │           ├── controller/      # REST Controllers
     │           ├── dto/             # DTOs
     │           ├── entity/          # JPA Entities
     │           ├── repository/      # JPA Repositories
     │           ├── service/         # Business logic
     │           ├── util/            # JwtUtil, CloudinaryUtil, etc.
     │           ├── filter/          # JWT filters, CORS filters
     │           └── TunehubApplication.java
     └── resources/
         ├── application.yml
         └── static/                 # Static resources (if any)
```

---


## ⚙️ Features

- 🎵 Song upload, update, delete, and streaming APIs
- 🎶 Playlist creation, update, and management APIs
- 🧑‍🎤 Artist management APIs
- 🔐 Cookie-based JWT login with Spring Security
- 🔑 Role-based authentication (USER & ADMIN)
- 🧾 Razorpay payment integration
- 🔄 Admin playlist view + User playlist view
- ☁️ File uploads (audio/image) to Cloudinary
- 📖 API documentation via Swagger UI
- 🧵 Virtual threads + HikariCP for optimised request handling

---

## 📡 API Endpoints

### 🎶 Songs
- `POST /api/songs` → Add new song  
- `GET /api/songs` → Get all songs  
- `PUT /api/songs/{id}` → Update song by ID  
- `DELETE /api/songs/{id}` → Delete song by ID  

### 👤 Users
- `POST /api/auth/register` → Register user  
- `POST /api/auth/login` → User login (JWT cookie)  
- `GET /api/auth/me` → Current logged-in user  
- `PUT /api/users/{id}` → Update user  
- `DELETE /api/users/{id}` → Delete user  
- `PUT /api/users/{id}/update-photo` → Upload profile photo  
- `DELETE /api/users/{id}/remove-photo` → Remove profile photo  

### 📃 Playlists
- `POST /api/playlists` → Create playlist  
- `GET /api/playlists` → Get all playlists  
- `GET /api/playlists/user/{userId}` → Get playlists by user  
- `GET /api/playlists/admin` → Admin: get all playlists  
- `PUT /api/playlists/{playlistId}/songs/{songId}` → Add song to playlist  
- `DELETE /api/playlists/{playlistId}/songs/{songId}` → Remove song from playlist  
- `PUT /api/playlists/updatePlaylist/{id}` → Update playlist  
- `DELETE /api/playlists/{id}` → Delete playlist

### 🎨 Artists
- `POST /artists` → Add new artist  
- `GET /artists` → Get all artists  
- `GET /artists/{id}` → Get artist by ID  
- `PUT /artists/{id}` → Update artist  
- `DELETE /artists/{id}` → Delete artist 

### 📤 Media Upload
- `POST /api/upload/image` → Upload image to Cloudinary  
- `POST /api/upload/audio` → Upload audio to Cloudinary  

### 💳 Payments
- `POST /api/payment/create-order` → Create Razorpay order  
- `PUT /api/payment/make-premium` → Make user premium manually  
- `GET /api/payment/razorpay-key` → Get Razorpay public key  
- `POST /api/payment/webhook` → Handle Razorpay webhook  


---

## 🗄️ Database

Using **PostgreSQL** database `tunehub_db`

### Example Tables:
- `users`  
- `songs`  
- `playlists`  
- `artists`

---

## 🛠️ Setup & Installation

### 📦 Requirements
- Java 21  
- Maven 3.9+  
- PostgreSQL 13+  
- Cloudinary Account (for file uploads) 
- Razorpay Account 

### 🚀 Installation Steps

1️⃣ Clone the project:
```bash
git clone https://github.com/shyam0880/TuneHub-Backend.git
cd TuneHub-Backend

```
2️⃣ Configure your database credentials in src/main/resources/application.yml

> ℹ️ **Note:** This project does **not** use Spring Profiles (`application-dev.yml`, `application-prod.yml`) by default.  
> However, you can easily refactor the configuration using `spring.profiles.active` for better environment separation in production-ready deployments.

## applications.yml
```shell
jwt:
  secret: ${JWT_TOKEN}

spring:
  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 100MB

  datasource:
    url: ${DB_POSTGRE_URL}
    username: ${DB_POSTGRE_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 3
      idle-timeout: 600000
      max-lifetime: 1800000
      connection-timeout: 30000

  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

CLOUDINARY_CLOUD_NAME: ${CLOUDINARY_CLOUD_NAME}
CLOUDINARY_API_KEY: ${CLOUDINARY_API_KEY}
CLOUDINARY_API_SECRET: ${CLOUDINARY_API_SECRET}

razorpay:
  key:
    id: ${RAZORPAY_KEY_ID}
    secret: ${RAZORPAY_SECRET}

server:
  virtual-threads:
    enabled: true

```
3️⃣ Run the application via
```shell
./mvnw spring-boot:run
```
or  
Click "Run" on **Spring Suite Tool 4** or **IntelliJ** etc.

### Set Environment Variable
```bash
Tool Bar > Run > Run Configuration > Environment > Add
→ Add your variables (DB, JWT, Cloudinary, Razorpay)
→ Apply → Run
```





