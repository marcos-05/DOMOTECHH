# DOMOTECH - Sistema de Gestión Domótica para Comunidades

**DOMOTECH** es una solución de software multiplataforma diseñada para la automatización y gestión integral de comunidades residenciales. Desarrollada en **Java** utilizando **JavaFX** para la interfaz gráfica, el sistema implementa una arquitectura modular que centraliza el control de accesos, iluminación inteligente, monitorización de sensores y comunicación vecinal.

El proyecto simula un entorno IoT (Internet of Things) mediante sensores virtuales y actuadores, permitiendo la gestión eficiente de recursos energéticos y la seguridad física del recinto.

---

## 🏗 Arquitectura del Software

El sistema ha sido diseñado bajo el patrón **MVC (Modelo-Vista-Controlador)** para garantizar la separación de responsabilidades, la escalabilidad y la mantenibilidad del código.

* **Vista:** Interfaces desarrolladas en **FXML** con estilizado mediante **CSS** para una experiencia de usuario adaptativa según el rol.
* **Controlador:** Gestión de eventos y lógica de presentación, actuando como mediador entre la interfaz y el modelo de negocio.
* **Modelo:** Lógica de negocio y persistencia de datos, encapsulando entidades como `Usuario`, `Sensor`, `Mensaje` y `RegistroAcceso`.

### Características Técnicas Destacadas
* **Modularidad:** El sistema se divide en bloques lógicos independientes (Iluminación, Accesos, Chat, Sensores).
* **Concurrencia (Multithreading):** Implementación de hilos en Java para el sistema de chat comunitario, permitiendo actualizaciones en tiempo real sin bloquear el hilo principal de la interfaz gráfica (EDT).
* **Persistencia Relacional:** Uso de **MySQL** con integridad referencial y normalización. La conexión se gestiona mediante JDBC a través de la clase `DatabaseConnection`, que centraliza y securiza las operaciones SQL.
* **Simulación de Hardware:** Sensores físicos (PIR, temperatura, luminosidad) programados con arduino con impacto directo en los actuadores (luces) y la aplicacion.

---

## 🛠 Stack Tecnológico

* **Lenguaje:** Java (JDK 17+)
* **Framework GUI:** JavaFX + FXML
* **Estilos:** CSS
* **Base de Datos:** MySQL 8.0
* **Gestión de Dependencias:** Maven
* **Driver JDBC:** MySQL Connector/J
* **Librerías Adicionales:**
    * `JFoenix` (Componentes Material Design)
    * `Gson` (Manejo de datos JSON en fases iniciales)
    * `Commons Codec` (Utilidades de encriptación)

---

## 🚀 Funcionalidades Principales

### 1. Gestión de Roles y Seguridad (RBAC)
El sistema implementa un control de acceso basado en roles:
* **Presidente:** Acceso total, visualización de estadísticas energéticas (ahorro y consumo), logs de seguridad y moderación.
* **Portero:** Control manual de dispositivos (luces/puertas), supervisión de sensores y gestión de incidencias.
* **Técnico:** Acceso a configuración de parámetros del sistema.
* **Residente:** Control de acceso personal e interacción social.

### 2. Iluminación Inteligente y Sensores
Automatización reactiva basada en condiciones ambientales:
* **Sensor de Movimiento:** Activa la iluminación por presencia.
* **Sensor Crepuscular (Luz):** Regula la intensidad de las farolas según la luz natural.
* **Sensor de Temperatura:** Ajusta la temperatura de color (Cálida/Fría) de la iluminación LED.

### 3. Control de Accesos y Trazabilidad
Sistema de apertura remota de puertas (Garaje, Gimnasio, Principal) con **logging automático** en base de datos (Timestamp + UserID) para auditorías de seguridad.

### 4. Comunicación Real-Time
Chat global persistente que utiliza `Threads` para polling de mensajes, asegurando sincronización instantánea entre múltiples clientes conectados.

---

## 💾 Modelo de Datos

El esquema relacional en MySQL incluye las siguientes entidades principales:
* `usuarios` & `roles`: Gestión de credenciales y permisos.
* `asignacion_sensor` & `registro`: Configuración de dispositivos IoT y logs de actividad.
* `mensajes`: Historial de comunicación del chat comunitario.
* `registros_acceso`: Auditoría de aperturas de puertas.

---

## ⚙️ Instalación y Ejecución

Este proyecto utiliza **Maven** para la gestión de dependencias y ciclo de vida.

### Prerrequisitos
* Java JDK 17 o superior.
* MySQL Server en ejecución.
* Maven (o usar el wrapper incluido `mvnw`).

### Pasos
1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/tu-usuario/pii24-domotech.git](https://github.com/tu-usuario/pii24-domotech.git)
    cd pii24-domotech
    ```

2.  **Configurar Base de Datos:**
    * Crear una base de datos en MySQL llamada `pii2_Domotech` (o configurar `DatabaseConnection.java`).
    * Importar el esquema SQL proporcionado en `/sql` (si está disponible).

3.  **Compilar y Ejecutar:**
    ```bash
    # Limpiar y descargar dependencias
    ./mvnw clean install

    # Ejecutar la aplicación JavaFX
    ./mvnw javafx:run
    ```

---

## 👥 Autores

Proyecto desarrollado como parte del Grado en Ingeniería Informática - Universidad Europea:

* **Pablo del Prado** - *Backend, Chat System & Database Architecture*
* **Juan de Frutos** - *Frontend Logic, Controllers & CRUD Operations*
* **Marcos Martín** - *Role Management,Sensor Control and Simulation, Access Control & Security Logs*
* **Germán Sierra** - *Sensor Simulation & Database Design*

---
© 2025 DOMOTECH - Todos los derechos reservados.

