# 🃏 Videojuego de Cartas - Trabajo Final de Programación

Este repositorio contiene el código fuente de una aplicación de escritorio desarrollada en **Java** con interfaz gráfica (**Swing**) y conexión a una base de datos **MySQL**. El proyecto simula la gestión de un videojuego de cartas coleccionables, permitiendo la administración de jugadores, la visualización de un catálogo de cartas y la simulación de batallas.

---

## 🚀 Funcionalidades Principales

El sistema cuenta con un panel de control interactivo que permite realizar las siguientes acciones:

* **Catálogo de Cartas:** Visualización de todas las cartas disponibles en la base de datos.
* **Filtros Avanzados:** Búsqueda de cartas específicas filtrando por **Tipo** (Personajes o Hechizos) o por **Rareza** (Común, Rara, Épica, Legendaria).
* **Gestión de Jugadores:**
    * Ver la lista de jugadores registrados (con su nivel, monedas y fecha de creación).
    * Dar de alta a nuevos usuarios.
    * Dar de baja (eliminar) a jugadores del sistema.
    * Añadir monedas ficticias al monedero de un jugador específico.
* **Simulador de Batallas:** Un sistema de cálculo asíncrono (con barra de progreso) que enfrenta a dos jugadores registrados. Calcula el poder total de sus mazos basándose en los puntos de salud y velocidad de los personajes, y el poder estático de los hechizos, determinando un ganador o un empate épico.
* **Ambientación:** Reproducción de música de fondo durante la ejecución de la aplicación.

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java (POO, Herencia, Polimorfismo)
* **Interfaz Gráfica:** Java Swing (Paneles, Tablas, JOptionPane, SwingWorker para procesos asíncronos)
* **Base de Datos:** MySQL
* **Conexión:** JDBC (Java Database Connectivity)

---

## 📂 Estructura del Proyecto

El código está estructurado bajo el paquete `tarea_final` y sigue un modelo de separación de responsabilidades:

* **Modelos/Clases Base:** `Carta.java`, `CartaPersonaje.java`, `CartaHechizo.java`, `Jugador.java`.
* **Base de Datos:** `MySQLConnection.java` (gestiona la conexión), `Consultas.java` (contiene las sentencias SQL y la lógica de negocio).
* **Interfaz y Controladores:** `Principal.java` (punto de entrada), `VentanaPrincipal.java` (GUI), `Escuchador.java` (gestión de eventos y botones).
* **Utilidades:** `MetodosComunes.java` (validaciones).

---

## ⚙️ Instalación y Configuración

Para poder ejecutar este proyecto en tu máquina local, sigue estos pasos:

1.  **Clona el repositorio:**
    ```bash
    git clone [https://github.com/TU_USUARIO/Videojuego-Cartas---Trabajo-Final-Programaci-n.git](https://github.com/TU_USUARIO/Videojuego-Cartas---Trabajo-Final-Programaci-n.git)
    ```
2.  **Configura la Base de Datos:**
    * Asegúrate de tener instalado un servidor MySQL (por ejemplo, a través de XAMPP, WAMP o MySQL Server).
    * Crea una base de datos llamada `cartas`.
    * Importa el script SQL de creación de tablas y datos (si está disponible) para poblar la base de datos con las cartas, regiones y rarezas.
3.  **Configura las credenciales (Opcional):**
    Si tu servidor MySQL tiene contraseña, abre el archivo `MySQLConnection.java` y modifica las variables `usuario` y `password` con tus credenciales locales.
    ```java
    String url = "jdbc:mysql://localhost:3306/cartas";
    String usuario = "root"; // Tu usuario
    String password = ""; // Tu contraseña
    ```
4.  **Ejecución:**
    Compila y ejecuta el archivo `Principal.java` desde tu IDE favorito (Eclipse, IntelliJ IDEA, VS Code). Asegúrate de tener el driver JDBC de MySQL (`mysql-connector-j`) añadido al *classpath* o a las librerías de tu proyecto.

---

*Desarrollado por Sandra Berná Zaplana y Laura Gonzalo Salinas*
