package cl.uss.transporte.controlador;

import cl.uss.transporte.modelo.Bus;
import cl.uss.transporte.modelo.ExcepcionPersonalizada;
import cl.uss.transporte.servicio.BusServicio;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Lista los buses con su viaje asociado. URL: /buses */
@WebServlet("/buses")
public class ListarBusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final transient BusServicio servicio = new BusServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Mensajes.moverAlRequest(request);

        List<Bus> buses;
        try {
            buses = servicio.listarBuses();
        } catch (ExcepcionPersonalizada e) {
            buses = Collections.emptyList();
            request.setAttribute(Mensajes.ERROR, e.getMessage());
        }

        request.setAttribute("buses", buses);
        request.getRequestDispatcher("/WEB-INF/vistas/listar-bus.jsp").forward(request, response);
    }
}
