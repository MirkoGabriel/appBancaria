# Microservicio Bancario - CRUD REST con Spring Boot

Este proyecto implementa un **microservicio REST** desarrollado en **Spring Boot**, que expone un **CRUD completo** sobre una entidad bancaria (`BankAccount`).  
Incluye un **endpoint adicional** que **consume otro endpoint del mismo microservicio**, demostrando comunicación interna.

---

## Tecnologías utilizadas
- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- H2 Database (en memoria)
- Spring Validation
- JUnit 5 + MockMvc
- Gradle

---

##  Requisitos previos

- Java 17 o superior
- Gradle (opcional, podés usar el wrapper ./gradlew)

---

## Instalación y ejecución

1. Clonar el repositorio:

    `git clone https://github.com/usuario/microservicio-bancario.git`
   `cd microservicio-bancario`
    
2. Compilar y correr la aplicación:

   `./gradlew bootRun`
---

## Tests
Ejecutar los tests

`./gradlew test`

---

## Importar postman
Filename - AppBancaria.postman_collection.json


