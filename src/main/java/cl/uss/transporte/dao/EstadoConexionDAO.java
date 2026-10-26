package cl.uss.transporte.dao;

import java.sql.SQLException;


public interface EstadoConexionDAO {
    boolean verificar() throws SQLException;
}
