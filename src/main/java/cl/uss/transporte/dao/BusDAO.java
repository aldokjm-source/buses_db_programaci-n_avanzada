package cl.uss.transporte.dao;

import cl.uss.transporte.modelo.Bus;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface BusDAO {

    List<Bus> listar() throws SQLException;

    Optional<Bus> buscarPorId(int id) throws SQLException;

    /** Inserta el bus y devuelve el ID generado. */
    int crear(Bus bus) throws SQLException;

    /** Devuelve true si se actualizó un registro (false si el ID no existe). */
    boolean actualizar(Bus bus) throws SQLException;

    /** Devuelve true si se eliminó un registro (false si el ID no existe). */
    boolean eliminar(int id) throws SQLException;
}
