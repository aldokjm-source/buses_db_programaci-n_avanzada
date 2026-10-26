package cl.uss.transporte.dao;

import cl.uss.transporte.db.ConexionBD;
import cl.uss.transporte.modelo.Viaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ViajeDAOImpl implements ViajeDAO {

    private static final String SQL_LISTAR =
            "SELECT id, nombre, codigo FROM viaje ORDER BY nombre";

    private static final String SQL_BUSCAR_POR_ID =
            "SELECT id, nombre, codigo FROM viaje WHERE id = ?";

    @Override
    public List<Viaje> listar() throws SQLException {
        List<Viaje> viajes = new ArrayList<>();
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                viajes.add(mapear(rs));
            }
        }
        return viajes;
    }

    @Override
    public Optional<Viaje> buscarPorId(int id) throws SQLException {
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

    private Viaje mapear(ResultSet rs) throws SQLException {
        return new Viaje(rs.getInt("id"), rs.getString("nombre"), rs.getString("codigo"));
    }
}
