package cl.uss.transporte.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class ConexionBD {
    private ConexionBD() { }

    private static final class Contenedor {
        private static final ConexionBD INSTANCIA = new ConexionBD();
    }

    public static ConexionBD getInstancia() {
        return Contenedor.INSTANCIA;
    }

    public Connection getConexion() throws SQLException {
        String url = leerVariable("DB_URL");
        String usuario = leerVariable("DB_USER");
        String clave = leerVariable("DB_PASSWORD");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontro el driver JDBC de PostgreSQL.", e);
        }

        return DriverManager.getConnection(url, usuario, clave);
    }

    private String leerVariable(String nombre) throws SQLException {
        String valor = System.getenv(nombre);
        if (valor == null || valor.isBlank()) {
            throw new SQLException(
                "Falta configurar " + nombre + " en el proceso que ejecuta Tomcat."
            );
        }
        return valor;
    }
}
