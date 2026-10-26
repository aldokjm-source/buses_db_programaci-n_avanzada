# Transporte web — Gestión de buses

Aplicación web Java (Servlets + JSP + JDBC) para listar, crear, editar y eliminar
buses. Cada bus se asocia a un viaje existente. Los viajes se cargan con el script SQL
y solo se consultan (no tienen CRUD).

## Requisitos y versiones

- JDK 17
- Apache Tomcat 9 (`javax.servlet`)
- Maven 3.9.x
- PostgreSQL + pgAdmin
- Dependencias: Servlet API 4.0.1, JSTL 1.2, PostgreSQL JDBC 42.7.4

## Base de datos

1. En pgAdmin, sobre la base `postgres`, ejecutar `sql/00_crear_base.sql`.
2. Sobre `transporte_db`, ejecutar `sql/01_tablas_datos.sql`
   (crea `viaje` y `bus`, con la FK `bus.id_viaje -> viaje.id`, y datos de prueba).

## Configuración

La conexión se lee de variables de entorno del proceso que ejecuta Tomcat
(datos de ejemplo, reemplazar por los reales):

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/transporte_db"
$env:DB_USER = "usuario_ejemplo"
$env:DB_PASSWORD = "clave_ejemplo"
```

## Ejecución

```powershell
mvn clean package
Copy-Item .\target\transporte-web.war "$env:CATALINA_HOME\webapps\" -Force
& "$env:CATALINA_HOME\bin\catalina.bat" run
```

- Verificar conexión: http://localhost:8080/transporte-web/inicio
- Gestión de buses: http://localhost:8080/transporte-web/buses

## Pruebas

1. `/buses` muestra los 3 buses con su viaje.
2. Crear un bus válido: vuelve al listado con mensaje de éxito.
3. Crear con marca de solo espacios o sin viaje: muestra los errores y conserva lo ingresado.
4. Editar: el formulario carga los datos y el viaje actual seleccionado.
5. Eliminar: muestra los datos; probar Cancelar y Confirmar.
6. `/buses/editar?id=abc` y `?id=999`: mensaje claro, sin errores sin controlar.

## Flujo y arquitectura

Vista (JSP) -> Controlador (Servlet) -> Lógica (`BusServicio`) -> Persistencia (DAO) -> PostgreSQL

- **Servlets** (`controlador`): reciben la petición, llaman al servicio y eligen la JSP. No tienen SQL.
- **BusServicio** (`servicio`): valida los datos y lanza `ExcepcionPersonalizada`.
- **DAO** (`dao`): `BusDAO` y `ViajeDAO`, con consultas parametrizadas y recursos cerrados.
- **JSP** (`WEB-INF/vistas`): solo muestran los datos y mensajes recibidos.

**Singleton:** `ConexionBD` mantiene una instancia única que centraliza la configuración
de acceso a datos y entrega una conexión nueva por operación, que cada DAO cierra.
