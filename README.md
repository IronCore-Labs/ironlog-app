# Iron Log - Modular Fitness Management System
> Developed By **Iron Core Labs**

[English Version](#english) | [Versión en Español](#espanol)

---

<a name="english"></a>
## English Version
### 📌 Overview

**IronLog** is a high-performance fitness management backend built with **Spring Boot 3**. Unlike traditional monolithic applications, IronLog utilizes a **Multi-Module Architecture** to ensure strict separation of concerns, scalability, and maintainability.

### 🏗️ Architecture & Design
The project is structured into independent modules to decouple business logic from infrastructure, following **Clean Code** principles:

* **`ironlog-api`**: The main entry point. It bootstraps the Spring Boot application and coordinates between security and business modules.
* **`ironlog-security`**: Handles JWT generation, RBAC logic, and user persistence.
* **`ironlog-core`**: Defines the shared domain and global exceptions.
* **`ironlog-app`**: Orchestration layer that binds the modules together.
* **`ironlog-training` (Upcoming)**: This module is currently under development. It will contain the core fitness logic, including routines, exercises, and progress tracking.

### 🛡️ Key Features
* **Modular RBAC Security**: Custom security implementation that decouples roles and permissions from the database using structured configuration files.
* **Clean Domain Model**: Use of JPA inheritance to separate "who the user is" from "how the person authenticates".
* **Semantic Commits**: Professional development history following the `feature(scope): message` standard.

### 🛠️ Tech Stack
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-blue?style=for-the-badge&logo=java&logoColor=white)
![OpenAPI](https://img.shields.io/badge/OpenAPI-6BA539?style=for-the-badge&logo=openapi-initiative&logoColor=white)
![Stoplight](https://img.shields.io/badge/Stoplight-4c00e8?style=for-the-badge&logo=stoplight&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)

### 🚀 Quick Start (API Usage)
**1. Authentication:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"email": "admin@ironlog.com", "password": "System"}'
```

1. **Local Credentials**: Create a file named `credentials.yaml` in the root directory (this file is ignored by Git for security).
2. **Template**: You can use `credentials-example.yaml` as a reference.
3. **Properties**:
    - `DB_HOST`, `DB_PORT`, `DB_NAME`: Your PostgreSQL connection details.
    - `TOKEN_EXPIRATION`: JWT validity period in minutes.

> **Note:** Never commit your actual `credentials.yaml` to the repository.

### 🔑 Environment Setup
To run this project locally, you must configure your environment variables. We use a decoupled configuration approach:

<a name="espanol"></a>
## Versión en Español
### 📌 Vista General

**IronLog** es un backend de alto rendimiento para la gestión de fitness, construido con **Spring Boot 3**. A diferencia de las aplicaciones monolíticas tradicionales, IronLog utiliza una **Arquitectura Multi-módulo** para garantizar una estricta separación de responsabilidades, escalabilidad y mantenibilidad.

### 🏗️ Arquitectura y Diseño
El proyecto está estructurado en módulos independientes para desacoplar la lógica de negocio de la infraestructura, siguiendo los principios de **Clean Code**:

* **`ironlog-api`**: El punto de entrada principal. Arranca la aplicación Spring Boot y coordina entre los módulos de seguridad y negocio.
* **`ironlog-security`**: Gestiona la generación de JWT, la lógica RBAC y la persistencia de usuarios.
* **`ironlog-core`**: Define el dominio compartido y las excepciones globales.
* **`ironlog-app`**: Capa de orquestación que une todos los módulos.
* **`ironlog-training` (Próximamente)**: Este módulo está actualmente en desarrollo. Contendrá la lógica central de fitness, incluyendo rutinas, ejercicios y seguimiento de progreso.

### 🛡️ Características Clave
* **Seguridad RBAC Modular**: Implementación personalizada de seguridad que desacopla roles y permisos de la base de datos utilizando archivos de configuración estructurados.
* **Modelo de Dominio Limpio**: Uso de herencia JPA para separar "quién es el usuario" de "cómo se autentica la persona".
* **Commits Semánticos**: Historial de desarrollo profesional siguiendo el estándar `feature(scope): message`.

### 🛠️ Stack Tecnológico
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-blue?style=for-the-badge&logo=java&logoColor=white)
![OpenAPI](https://img.shields.io/badge/OpenAPI-6BA539?style=for-the-badge&logo=openapi-initiative&logoColor=white)
![Stoplight](https://img.shields.io/badge/Stoplight-4c00e8?style=for-the-badge&logo=stoplight&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)

### 🚀 Inicio Rápido (Uso de la API)
**1. Autenticación:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"email": "admin@ironlog.com", "password": "System"}'
```
1. **Credenciales Locales**: Crea un archivo llamado `credentials.yaml` en el directorio raíz (este archivo es ignorado por Git por seguridad).
2. **Plantilla**: Puedes usar `credentials-example.yaml` como referencia.
3. **Propiedades**:
   - `DB_HOST`, `DB_PORT`, `DB_NAME`: Detalles de conexión de tu PostgreSQL.
   - `TOKEN_EXPIRATION`: Período de validez de JWT en minutos.

> **Nota:** Nunca subas tu archivo `credentials.yaml` real al repositorio.
