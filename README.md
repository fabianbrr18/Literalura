<h1 align="center"> 📚 LiterAlura - Catálogo de Libros </h1>

<p align="center">
  <b>Challenge Alura - Oracle Next Education (ONE)</b>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/PROGRAMA-ONE-orange?style=for-the-badge&logoColor=white" alt="Programa ONE">
  <img src="https://img.shields.io/badge/FORMACIÓN-ALURA-blue?style=for-the-badge&logoColor=white" alt="Formación Alura">
  <img src="https://img.shields.io/badge/JAVA-17-green?style=for-the-badge&logoColor=white" alt="Java 17">
  <img src="https://img.shields.io/badge/SPRING_BOOT-3.2.3-yellow?style=for-the-badge&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/STATUS-COMPLETADO-brightgreen?style=for-the-badge&logoColor=white" alt="Status Completado">
</p>

<p align="center">
  <a href="#-descripción">Descripción</a> • 
  <a href="#-características">Características</a> • 
  <a href="#-tecnologías">Tecnologías</a> • 
  <a href="#-demostración">Demostración</a> • 
  <a href="#-estructura-del-código">Estructura</a> • 
  <a href="#-instalación-y-ejecución">Instalación</a> • 
  <a href="#-autor">Autor</a>
</p>

---

## 📖 Descripción

Este proyecto es el **Catálogo de Libros LiterAlura**, una aplicación de consola desarrollada en **Java y Spring Boot**. La aplicación interactúa activamente con la [API Gutendex](https://gutendex.com/) para buscar libros de dominio público en un catálogo de más de 70.000 libros, proporcionando un entorno para administrar su persistencia en una base de datos local **PostgreSQL** mediante **Spring Data JPA**.

> *Este desafío es parte de la formación Back-End Java de Alura, enfocándose en el consumo de APIs remotos, mapeo y análisis de respuestas JSON asíncronas, modelado y persistencia orientada a objetos usando JPA.*

## ✨ Características

| Funcionalidad | Descripción | Status |
| :--- | :--- | :---: |
| **Búsqueda** | Búsqueda dinámica por título en la `API Gutendex`. | ✅ |
| **Consumo y Mapeo** | Extracción del JSON vía HttpClient y mapeo de datos con `Jackson`. | ✅ |
| **Persistencia** | Guardado de los libros y relaciones uno-a-muchos hacia autores mediante JPA/PostgreSQL. | ✅ |
| **Queries en BD** | Consultas a la BD como conteo por "Idioma" o "Autores vivos". | ✅ |
| **Menú Dinámico** | Interfaz amigable y continua en consola a la espera de instrucciones. | ✅ |

## 🛠️ Tecnologías

* **Java 17 (LTS)** - Lenguaje principal.
* **Spring Boot 3.2.3** - Framework base del esquema y ejecución en consola (`CommandLineRunner`).
* **Spring Data JPA** - Administrador de Entidades y Consultas de persistencia.
* **PostgreSQL** - Base de datos de persistencia relacional (`Driver pgJDBC`).
* **Jackson (jackson-databind)** - Serializador/Deserializador del Data Binding JSON.
* **HttpClient/HttpRequest** - Herramienta nativa para las peticiones API.

## 📸 Demostración

```text
Elija la opción a través de su número:
1 - Buscar libro por título
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado año
5 - Listar libros por idioma

0 - Salir
```

## 📂 Estructura del Código

El código sigue una arquitectura de capas bien distribuida:

* `Principal.java`: Gestiona la interacción con el usuario y orquesta el flujo en el menú.
* `ConsumoAPI.java / IConvierteDatos.java`: Servicios encargados de las peticiones HTTP y la deserialización JSON.
* `DatosAutor.java / Libro.java / Autor.java`: Modelos base (records para JSON y Clases para Entidades SQL).
* `LibroRepository.java / AutorRepository.java`: Interfaz puente con JPA para los Queries de PostgreSQL.
* `application.properties`: Ubicación principal de configuración de conexión DB y JPA/Hibernate Updates.

## 🚀 Instalación y Ejecución

**1. Clonar el repositorio:**
```bash
git clone https://github.com/fabianbrr18/Literalura.git
```

**2. Configurar la Base de Datos:**
* Asegúrate de tener PostgreSQL ejecutándose.
* Crea una DB llamada `literalura` utilizando pgAdmin o comando SQL.
* Si prefieres otro usuario o clave, cámbialos en tu `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
```

**3. Compilar y Ejecutar:**
* En entorno IntelliJ, corre la aplicación lanzando desde la clase raíz `LiteraluraApplication`.
* Desde consola compilando en Maven (con el wrapper):
```bash
./mvnw spring-boot:run
```

## 👤 Autor

<p align="center">
  <img src="https://avatars.githubusercontent.com/fabianbrr18" width="100px;" alt="Fabian Cifuentes Profile"/><br />
  <b>Fabian Felipe Cifuentes</b>
</p>

<p align="center">
  <a href="https://linkedin.com/in/fabian-felipe-cifuentes" target="_blank"><img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn"></a>
  <a href="https://github.com/fabianbrr18" target="_blank"><img src="https://img.shields.io/badge/-Github-%23181717?style=for-the-badge&logo=github&logoColor=white" alt="Github"></a>
</p>

<p align="center">
  Desarrollado con ❤️ para el Challenge Alura
</p>
