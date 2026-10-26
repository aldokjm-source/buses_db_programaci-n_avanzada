package cl.uss.transporte.servicio;

import cl.uss.transporte.dao.BusDAO;
import cl.uss.transporte.dao.BusDAOImpl;
import cl.uss.transporte.dao.ViajeDAO;
import cl.uss.transporte.dao.ViajeDAOImpl;
import cl.uss.transporte.modelo.Bus;
import cl.uss.transporte.modelo.ExcepcionPersonalizada;
import cl.uss.transporte.modelo.Viaje;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Lógica de negocio de buses: valida los datos y usa los DAO. */
public class BusServicio {

    private static final Logger LOG = Logger.getLogger(BusServicio.class.getName());

    private static final String MSG_ERROR_DATOS =
            "No fue posible acceder a los datos en este momento. Intente nuevamente más tarde.";
    private static final int MAX_MARCA = 100;
    private static final int MAX_MODELO = 150;
    private static final int MAX_PATENTE = 20;

    private final BusDAO busDAO;
    private final ViajeDAO viajeDAO;

    public BusServicio() {
        this(new BusDAOImpl(), new ViajeDAOImpl());
    }

    public BusServicio(BusDAO busDAO, ViajeDAO viajeDAO) {
        this.busDAO = busDAO;
        this.viajeDAO = viajeDAO;
    }

    // ------------------------------------------------------------ Consultas

    public List<Bus> listarBuses() throws ExcepcionPersonalizada {
        try {
            return busDAO.listar();
        } catch (SQLException e) {
            throw errorTecnico("listar buses", e);
        }
    }

    public List<Viaje> listarViajes() throws ExcepcionPersonalizada {
        try {
            return viajeDAO.listar();
        } catch (SQLException e) {
            throw errorTecnico("listar viajes", e);
        }
    }

    /** Busca un bus a partir del ID recibido como texto desde la petición. */
    public Bus buscarBus(String idTexto) throws ExcepcionPersonalizada {
        int id = validarId(idTexto);
        try {
            return busDAO.buscarPorId(id)
                    .orElseThrow(() -> new ExcepcionPersonalizada(
                            "El bus solicitado (ID " + id + ") no existe."));
        } catch (SQLException e) {
            throw errorTecnico("buscar bus " + id, e);
        }
    }

    // ----------------------------------------------------------- Operaciones

    public Bus crear(String marca, String modelo, String patente, String idViajeTexto)
            throws ExcepcionPersonalizada {
        Bus bus = validarDatos(marca, modelo, patente, idViajeTexto);
        try {
            busDAO.crear(bus);
            return bus;
        } catch (SQLException e) {
            throw errorTecnico("crear bus", e);
        }
    }

    public Bus actualizar(String idTexto, String marca, String modelo, String patente,
            String idViajeTexto) throws ExcepcionPersonalizada {
        Bus existente = buscarBus(idTexto);
        Bus bus = validarDatos(marca, modelo, patente, idViajeTexto);
        bus.setId(existente.getId());
        try {
            if (!busDAO.actualizar(bus)) {
                throw new ExcepcionPersonalizada(
                        "El bus solicitado (ID " + bus.getId() + ") ya no existe.");
            }
            return bus;
        } catch (SQLException e) {
            throw errorTecnico("actualizar bus " + bus.getId(), e);
        }
    }

    /** Elimina el bus y devuelve sus datos (para el mensaje de confirmación). */
    public Bus eliminar(String idTexto) throws ExcepcionPersonalizada {
        Bus existente = buscarBus(idTexto);
        try {
            if (!busDAO.eliminar(existente.getId())) {
                throw new ExcepcionPersonalizada(
                        "El bus solicitado (ID " + existente.getId() + ") ya no existe.");
            }
            return existente;
        } catch (SQLException e) {
            throw errorTecnico("eliminar bus " + existente.getId(), e);
        }
    }

    // ---------------------------------------------------------- Validaciones

    private Bus validarDatos(String marca, String modelo, String patente, String idViajeTexto)
            throws ExcepcionPersonalizada {
        List<String> errores = new ArrayList<>();

        String marcaLimpia = validarTexto(marca, "La marca", "obligatoria", MAX_MARCA, errores);
        String modeloLimpio = validarTexto(modelo, "El modelo", "obligatorio", MAX_MODELO, errores);
        String patenteLimpia = validarTexto(patente, "La patente", "obligatoria", MAX_PATENTE, errores);

        Viaje viaje = null;
        Integer idViaje = convertirEntero(idViajeTexto);
        if (esVacio(idViajeTexto)) {
            errores.add("Debe seleccionar un viaje.");
        } else if (idViaje == null || idViaje <= 0) {
            errores.add("El viaje seleccionado no es válido.");
        } else {
            try {
                viaje = viajeDAO.buscarPorId(idViaje).orElse(null);
            } catch (SQLException e) {
                throw errorTecnico("validar viaje " + idViaje, e);
            }
            if (viaje == null) {
                errores.add("El viaje seleccionado no existe.");
            }
        }

        if (!errores.isEmpty()) {
            throw new ExcepcionPersonalizada(errores);
        }
        return new Bus(0, marcaLimpia, modeloLimpio, patenteLimpia.toUpperCase(), viaje);
    }

    /** Obligatorio, no vacío ni solo espacios, y dentro del largo de la columna. */
    private String validarTexto(String valor, String campo, String obligatorio, int max,
            List<String> errores) {
        if (esVacio(valor)) {
            errores.add(campo + " es " + obligatorio
                    + "; no se aceptan valores vacíos ni compuestos solo por espacios.");
            return null;
        }
        String limpio = valor.trim();
        if (limpio.length() > max) {
            errores.add(campo + " no puede superar " + max + " caracteres.");
        }
        return limpio;
    }

    private int validarId(String idTexto) throws ExcepcionPersonalizada {
        if (esVacio(idTexto)) {
            throw new ExcepcionPersonalizada("Debe indicar el ID del bus.");
        }
        Integer id = convertirEntero(idTexto);
        if (id == null || id <= 0) {
            throw new ExcepcionPersonalizada("El ID de bus indicado no es válido.");
        }
        return id;
    }

    private static boolean esVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private static Integer convertirEntero(String texto) {
        if (texto == null) {
            return null;
        }
        try {
            return Integer.valueOf(texto.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private ExcepcionPersonalizada errorTecnico(String operacion, SQLException e) {
        LOG.log(Level.SEVERE, "Error de base de datos al " + operacion, e);
        return new ExcepcionPersonalizada(MSG_ERROR_DATOS, e);
    }
}
