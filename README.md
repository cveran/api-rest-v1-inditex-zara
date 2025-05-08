# api-rest-v1-inditex-zara

API REST desarrollada con Java 17 y Spring Boot 3.4 para la consulta del precio aplicable de un producto en función de la fecha, marca y lista de precios. Proyecto estructurado aplicando principios de **DDD (Domain-Driven Design)** y **Arquitectura Hexagonal**.

---

## 📦 Tecnologías

- Java 17
- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA
- H2 Database (memoria)
- Lombok
- Swagger / OpenAPI 
- JUnit 5

---

## 🧠 Arquitectura

La API está construida siguiendo los principios de **arquitectura hexagonal**, separando claramente:

```

├── application/            → puertos y servicios
├── domain/                 → Entidades y lógica del dominio
├── infrastructure/         → Adaptadores REST y JPA + configuración
│  
└── test/                   → Tests de integración
```

---

## 🚀 Cómo ejecutar

### Desde consola

```bash
mvn clean spring-boot:run
```

### Desde IntelliJ

Ejecutá la clase `ApiRestInditexZaraApplication.java` con botón ▶️

---

## 📘 Documentación OpenAPI

Una vez iniciada la app:

- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) 
- OpenAPI YAML: [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml) * Se descarga en el ordenador

---

## 🧪 Pruebas de integración Junit

Los siguientes 6 casos están cubiertos por pruebas de integración:

1. **14-Jun-2020 10:00** 
2. **14-Jun-2020 16:00** 
3. **14-Jun-2020 21:00** 
4. **15-Jun-2020 10:00** 
5. **16-Jun-2020 21:00**
6. ❌ **Fecha fuera de rango** → 404 Not Found

### Ejecutar tests:

```bash
mvn test
```

---

## 📂  Pruebas de integración Colección Postman

Se incluye archivo `zara-pricing-api.postman_collection.json` con 7 casos para importar directamente en Postman.

---

## ✅ Validaciones

- Parámetros requeridos (`applicationDate`, `productId`, `brandId`)
  - 400 → Parámetros inválidos
  - 404 → Precio no encontrado
  - 500 → Error interno

---

## 🔚 Licencia

Este proyecto fue desarrollado como prueba técnica. Puedes adaptarlo y reutilizarlo con fines académicos o profesionales.

