package cl.uss.transporte.dao;

import java.sql.Connection;
import java.sql.SQLException;
import cl.uss.transporte.db.ConexionBD;

public class EstadoConexionDAOImpl implements EstadoConexionDAO {
    @Override
    public boolean verificar() throws SQLException {
        try (Connection conexion = ConexionBD.getInstancia().getConexion()) {
            return conexion.isValid(3);
        }
    }
}
