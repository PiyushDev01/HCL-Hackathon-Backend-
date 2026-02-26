# 🛒 Retail Ordering System

## 📌 Overview

The **Retail Ordering System** is a full-stack web application that enables customers to browse products (Pizza, Cold Drinks, Breads), add items to their cart, and place orders securely. It ensures real-time inventory management and efficient backend operations.

The system is designed using clean architecture principles with secure APIs and transactional consistency.

---

## 🚀 Tech Stack

### 🔹 Backend
- **Java 17+**
- **Spring Boot**
- **Spring Data JPA (Hibernate)**
- **Spring Security (JWT Authentication)**
- **MySQL**
- **Lombok**
- **Swagger** (API Documentation)

### 🔹 Frontend
- **React (Vite)**
- **Tailwind CSS**
- **Axios**
- **React Router**
- **React Hook Form**

---

## 🏗️ Architecture

The application follows a layered architecture:
`Controller` → `Service` → `Repository` → `Database`

### 🔹 Frontend
- Communicates with the backend using REST APIs.
- Uses JWT for secured requests.
- Implements protected routes.

### 🔹 Backend
- RESTful APIs.
- DTO-based communication.
- Transaction management for order placement.
- Role-based access control.

---

## ✨ Features

### 🎯 Core Features
- 🛍️ Product browsing by category
- 🛒 Cart management
- 📦 Order placement
- 🔄 Automatic inventory update
- 📜 Order history
- 🛠️ Admin product management
- 📖 Swagger API documentation

### 🚀 Stretch Features
- 📧 Email confirmation after order placement
- 🎁 Promotions (coupons, loyalty points)
- 📊 Admin dashboard (Revenue, Top Products, Low Stock)
- 📄 Pagination & Sorting
- 🚦 Rate limiting (optional)
