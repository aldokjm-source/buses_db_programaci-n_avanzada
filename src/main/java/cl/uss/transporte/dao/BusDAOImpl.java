package cl.uss.transporte.dao;

import cl.uss.transporte.db.ConexionBD;
import cl.uss.transporte.modelo.Bus;
import cl.uss.transporte.modelo.Viaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BusDAOImpl implements BusDAO {

    private static final String SQL_SELECT_BASE =
            "SELECT b.id, b.marca, b.modelo, b.patente, "
            + "v.id AS viaje_id, v.nombre AS viaje_nombre, v.codigo AS viaje_codigo "
            + "FROM bus b JOIN viaje v ON v.id = b.id_viaje ";

    private static final String SQL_LISTAR = SQL_SELECT_BASE + "ORDER BY b.id";

    private static final String SQL_BUSCAR_POR_ID = SQL_SELECT_BASE + "WHERE b.id = ?";

    private static final String SQL_CREAR =
            "INSERT INTO bus (marca, modelo, patente, id_viaje) VALUES (?, ?, ?, ?)";

    private static final String SQL_ACTUALIZAR =
            "UPDATE bus SET marca = ?, modelo = ?, patente = ?, id_viaje = ? WHERE id = ?";

    private static final String SQL_ELIMINAR = "DELETE FROM bus WHERE id = ?";

    @Override
    public List<Bus> listar() throws SQLException {
        List<Bus> buses = new ArrayList<>();
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                buses.add(mapear(rs));
            }
        }
        return buses;
    }

    @Override
    public Optional<Bus> buscarPorId(int id) throws SQLException {
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_BUSCAR_POR_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public int crear(Bus bus) throws SQLException {
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_CREAR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, bus.getMarca());
            ps.setString(2, bus.getModelo());
            ps.setString(3, bus.getPatente());
            ps.setInt(4, bus.getViaje().getId());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    int id = claves.getInt(1);
                    bus.setId(id);
                    return id;
                }
            }
        }
        throw new SQLException("No se obtuvo el ID generado para el bus.");
    }

    @Override
    public boolean actualizar(Bus bus) throws SQLException {
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setString(1, bus.getMarca());
            ps.setString(2, bus.getModelo());
            ps.setString(3, bus.getPatente());
            ps.setInt(4, bus.getViaje().getId());
            ps.setInt(5, bus.getId());
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    private Bus mapear(ResultSet rs) throws SQLException {
        Viaje viaje = new Viaje(
                rs.getInt("viaje_id"),
                rs.getString("viaje_nombre"),
                rs.getString("viaje_codigo"));
        return new Bus(
                rs.getInt("id"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getString("patente"),
                viaje);
    }
}
