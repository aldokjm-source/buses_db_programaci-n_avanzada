package cl.uss.transporte.dao;

import cl.uss.transporte.modelo.Viaje;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface ViajeDAO {

    List<Viaje> listar() throws SQLException;

    Optional<Viaje> buscarPorId(int id) throws SQLException;
}
