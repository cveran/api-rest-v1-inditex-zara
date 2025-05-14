
# api-rest-v1-inditex-zara

API REST desarrollada con Java 17 y Spring Boot 3.4 para la consulta del precio aplicable de un producto en función de la fecha, marca y lista de precios. Proyecto estructurado aplicando principios de **DDD (Domain-Driven Design)** y **Arquitectura Hexagonal**.

---

## 📦 Tecnologías

- Java 17
- Maven
- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA
- H2 Database (memoria)
- Lombok / MapStruct
- Swagger / OpenAPI
- JUnit 5
- Docker / Make
- Cloud run de GCP

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

## 🚀 Cómo ejecutar en local - ambiente de desarrollo

### Desde consola

```bash
mvn clean spring-boot:run
```
## 🧪 Pruebas de integración Junit

Algunos de los casos cubiertos por las pruebas son:

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
### Desde IntelliJ

Ejecutá la clase `ApiRestInditexZaraApplication.java` con botón ▶️

---

# 🧵 Docker

Se incluye un entorno Docker multi-stage con imágenes diferenciadas para desarrollo y producción, siguiendo buenas prácticas de seguridad, portabilidad y eficiencia.

---

## 🚀 Ejecutar con Docker (sin Make)

### 🔧 Modo Desarrollo

```bash
docker build -t api-rest-inditex-zara-dev --target=dev .
docker run --rm -p 8080:8080 api-rest-inditex-zara-dev
```

- Imagen basada en Alpine con JDK completo.
- Ideal para pruebas, debug y desarrollo.

---

### 🛡️ Modo Producción

```bash
docker build -t api-rest-inditex-zara-prod --target=prod .
docker run --rm -p 8080:8080 api-rest-inditex-zara-prod
```

- Imagen optimizada basada en **Distroless**.
- Sin herramientas de sistema ni root.
- Máxima seguridad y rendimiento.

---

## 🔁 Automatizar con Makefile


### 🔨 Construcción

```bash
make build-dev    # Construye imagen de desarrollo
make build-prod   # Construye imagen de producción
```

### ▶️ Ejecución

```bash
make run-dev      # Ejecuta en modo desarrollo (puerto 8080)
make run-prod     # Ejecuta en modo producción (puerto 8080)
```

### 🧹 Limpieza

```bash
make clean        # Elimina imágenes Docker generadas
```



## 📘 Documentación local OpenAPI

Una vez iniciada la app en local:

- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- OpenAPI YAML: [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml) * Se descarga en el ordenador, sirve para importarlo en postman

---

## ✅ Validaciones

- Parámetros requeridos (`applicationDate`, `productId`, `brandId`)
  - 400 → Parámetros inválidos
  - 404 → Precio no encontrado
  - 500 → Error interno

---

## 🚀 Cómo visualizar la API en tiempo real en un entorno simulado de producción en la nube de GCP


## 📘 Desde el navegador acceder a los siguientes link para ver la documentación OpenAPI del ambiente Prod

- Swagger UI: [https://api-rest-inditex-zara-prod-279278482933.europe-southwest1.run.app/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- OpenAPI JSON: [https://api-rest-inditex-zara-prod-279278482933.europe-southwest1.run.app/v3/api-docs](http://localhost:8080/v3/api-docs)
- OpenAPI YAML: [https://api-rest-inditex-zara-prod-279278482933.europe-southwest1.run.app/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml) * Se descarga en el ordenador, sirve para importarlo en postman



## 📂  Pruebas de integración Colección Postman ambientes DEV y PROD

Se incluye en la raíz del proyecto archivo `Inditex Zara Pricing API v1.postman_collection` con 7 casos para importar directamente en Postman ambientes dev y prod.

---


## 🔚 Licencia

Este proyecto fue desarrollado como prueba técnica. Puedes adaptarlo y reutilizarlo con fines académicos o profesionales.










