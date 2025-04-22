# 🎶 TuneHub Backend

A music streaming web application backend built with **Java, Spring Boot**, and **MySQL**, providing user management, song management, playlist management, and artist support via a RESTful API. This backend serves as the core for the **TuneHub Frontend** React application.

---

## 📦 Tech Stack

- ⚙️ Java 21
- ☕ Spring Boot 3.x
- 🗄️ MySQL
- 🌐 Spring Security (basic configuration)
- ☁️ Cloudinary (for song and image uploads)
- 📦 Spring Data JPA (ORM)
- 🔌 RESTful API

---
## 📚 Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- MySQL Connector
- Cloudinary Java SDK (v1.38.0)
- Razorpay Java SDK (v1.4.8)
- Spring Boot DevTools
- JSON Web Token (planned, using jjwt)
- Spring Boot Starter Test

---

## 📑 Project Structure
```bash
src/
 └── main/
     ├── java/
     │   └── com/
     │       └── tunehub/
     │           ├── config/          // Currently using for cloudinary and webconfig for cross-mapping
     │           ├── controller/      // REST Controllers
     │           ├── dto/             // Data Transfer Objects
     │           ├── entity/          // JPA Entities
     │           ├── repository/      // JPA Repositories
     │           ├── service/         // Business logic services
     │           └── TunehubApplication.java  // Main Spring Boot application class
     │
     └── resources/
         ├── application.yml   // App configuration
         └── static/                  // Static resources (if any)
```

---


## ⚙️ Features

- 🎵 Song upload, update, delete, and streaming APIs
- 🎨 Artist management APIs
- 🎶 Playlist creation, update, and management APIs
- 📄 File uploads (song files and images) to **Cloudinary**
- 📈 RESTful API design
- 🔒 Secure password encryption with **BCrypt** (if used)

---

## 📡 API Endpoints

### 🎶 Songs
- `POST /songs` → Add a new song
- `GET /songs` → Get all songs
- `PUT /songs/{id}` → Update song by ID
- `DELETE /songs/{id}` → Delete song by ID

### 👤 Users
- `POST /users/register` → Register new user
- `PUT /users/{id}` → Update user
- `DELETE /users/{id}` → Delete user

### 📃 Playlists
- `POST /playlists` → Create playlist
- `GET /playlists` → Get all playlists
- `DELETE /playlists/{id}` → Delete playlist

### 📤 Media Upload
- `POST /upload/image` → Upload image to Cloudinary
- `POST /upload/audio` → Upload song to Cloudinary

### 💳 Payments
- `POST /payment/create` → Create Razorpay order
- `POST /payment/verify` → Verify payment signature


---

## 🗄️ Database

Using **MySQL** database `tunehub_db`

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
- MySQL 8+
- Cloudinary Account (for file uploads)

### 🚀 Installation Steps

1️⃣ Clone the project:
```bash
git clone https://github.com/shyam0880/TuneHub-Backend.git
cd TuneHub-Backend

```
2️⃣ Configure your database credentials in src/main/resources/application.yml
## applications.yml
```shell
spring:
  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 100MB

  datasource:
    url: ${DB_TH_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

  jpa:
    hibernate:
      ddl-auto: update

CLOUDINARY_CLOUD_NAME: ${CLOUDINARY_CLOUD_NAME}
CLOUDINARY_API_KEY: ${CLOUDINARY_API_KEY}
CLOUDINARY_API_SECRET: ${CLOUDINARY_API_SECRET}

razorpay:
  key:
    id: ${RAZORPAY_KEY_ID}
    secret: ${RAZORPAY_SECRET}

```
3️⃣ Run the application via
```shell
./mvnw spring-boot:run
```
or  
Click "Run" on **Spring Suite Tool 4** or **IntelliJ** etc.

### Set Environment Variable
```bash
Tool Bar > Run > Run Configuration > Environment > Add > (add variable and value) > Apply > Run
```





