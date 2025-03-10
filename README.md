Sistema de Autenticación con Spring Boot y Docker
Este proyecto es una aplicación web desarrollada con Spring Boot que implementa un sistema de autenticación de usuarios con una interfaz moderna. La aplicación está Dockerizada para facilitar su despliegue y utiliza MySQL como base de datos.
Características

Sistema de autenticación con Spring Security
Gestión de roles (usuario/administrador)
Interfaz de login estilizada
Registro de nuevos usuarios
Panel de administración
Edición de perfiles de usuario
Dockerización completa (aplicación y base de datos)

Tecnologías Utilizadas

Backend: Java 17, Spring Boot 3.4.2
Frontend: Thymeleaf, Bootstrap 5
Seguridad: Spring Security
Base de Datos: MySQL
Containerización: Docker, Docker Compose
Gestión de Dependencias: Maven

Estructura del Proyecto
CopyProyectoAutenticacion/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── config/              # Configuración de seguridad y aplicación
│   │   │   ├── controller/          # Controladores REST y MVC
│   │   │   ├── entity/              # Entidades JPA (Usuario, Rol)
│   │   │   ├── repository/          # Repositorios JPA
│   │   │   ├── service/             # Lógica de negocio
│   │   │   ├── SistemaAutenticacion/ # Componentes específicos de autenticación
│   │   │   └── DemoApplication.java # Clase principal
│   │   └── resources/
│   │       ├── static/              # Recursos estáticos (CSS, JS)
│   │       ├── templates/           # Plantillas Thymeleaf (login, admin, etc.)
│   │       └── application.properties # Configuración de la aplicación
├── Dockerfile                         # Configuración de imagen Docker
├── docker-compose.yml                 # Configuración de Docker Compose
└── pom.xml                            # Dependencias de Maven
Requisitos Previos

Docker y Docker Compose
JDK 17 (solo para desarrollo local)
Maven (solo para desarrollo local)

Instalación y Ejecución
Usando Docker (Recomendado)

Clona este repositorio:
bashCopygit clone https://github.com/tu-usuario/proyecto-autenticacion.git
cd proyecto-autenticacion

Ejecuta la aplicación con Docker Compose:
bashCopydocker-compose up -d

Accede a la aplicación en tu navegador:
Copyhttp://localhost:8080


Desarrollo Local (Sin Docker)

Clona el repositorio:
bashCopygit clone https://github.com/tu-usuario/proyecto-autenticacion.git
cd proyecto-autenticacion

Configura una base de datos MySQL:

Crea una base de datos llamada tarea2
Actualiza las credenciales en src/main/resources/application.properties


Compila y ejecuta la aplicación:
bashCopy./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar

Accede a la aplicación en tu navegador:
Copyhttp://localhost:8080


Configuración de Docker
Dockerfile
dockerfileCopyFROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
docker-compose.yml
yamlCopyversion: '3.8'

services:
  app:
    container_name: springboot-app
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - mysqldb
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysqldb:3306/tarea2?allowPublicKeyRetrieval=true
      - SPRING_DATASOURCE_USERNAME=admin
      - SPRING_DATASOURCE_PASSWORD=admin

  mysqldb:
    container_name: mysql-db
    image: mysql:8.0
    ports:
      - "3307:3306"
    environment:
      - MYSQL_ROOT_PASSWORD=root
      - MYSQL_DATABASE=tarea2
      - MYSQL_USER=admin
      - MYSQL_PASSWORD=admin
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
Credenciales por Defecto
Una vez que la aplicación esté en funcionamiento, puedes acceder con las siguientes credenciales:

Usuario Administrador:

Usuario: admin
Contraseña: admin123


Usuario Regular (puedes crear uno desde la página de registro)

Solución de Problemas
Puerto en uso
Si encuentras un error como "Ports are not available" al ejecutar Docker Compose:

Verifica qué proceso está usando el puerto (por ejemplo, 3307 para MySQL):
bashCopynetstat -ano | findstr :3307   # Windows
sudo lsof -i :3307             # Linux/Mac

Detén ese proceso o cambia el puerto en el archivo docker-compose.yml.

Errores de conexión a la base de datos
Si la aplicación no puede conectarse a la base de datos:

Verifica que el contenedor de MySQL esté en ejecución:
bashCopydocker ps

Comprueba los logs del contenedor de MySQL:
bashCopydocker logs mysql-db


Capturas de Pantalla
Aplicación ejecutándose con Docker
![Ejecuciondocker](https://github.com/user-attachments/assets/208c84e6-914b-44c5-a278-7a4a188b1e2f)

Pantalla de Login
![login](https://github.com/user-attachments/assets/5d2de34e-c50b-4df8-af23-fe0e0d9c4153)

Prueba de Conexión a la Base de Datos
![conexionbase](https://github.com/user-attachments/assets/b7e2e001-b099-4c8e-8cde-3e63cdbd6301)
