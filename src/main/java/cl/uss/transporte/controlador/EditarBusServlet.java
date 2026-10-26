package cl.uss.transporte.controlador;

import cl.uss.transporte.modelo.Bus;
import cl.uss.transporte.modelo.ExcepcionPersonalizada;
import cl.uss.transporte.servicio.BusServicio;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/buses/editar")
public class EditarBusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String VISTA = "/WEB-INF/vistas/editar-bus.jsp";

    private final transient BusServicio servicio = new BusServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Bus bus;
        try {
            bus = servicio.buscarBus(request.getParameter("id"));
        } catch (ExcepcionPersonalizada e) {
            Mensajes.error(request, e.getMessage());
            response.sendRedirect(request.getContextPath() + "/buses");
            return;
        }

        request.setAttribute("id", bus.getId());
        request.setAttribute("marca", bus.getMarca());
        request.setAttribute("modelo", bus.getModelo());
        request.setAttribute("patente", bus.getPatente());
        request.setAttribute("idViaje", String.valueOf(bus.getViaje().getId()));
        cargarViajes(request);
        request.getRequestDispatcher(VISTA).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String marca = request.getParameter("marca");
        String modelo = request.getParameter("modelo");
        String patente = request.getParameter("patente");
        String idViaje = request.getParameter("idViaje");

        // Si el ID no es válido o el bus no existe, no tiene sentido volver al formulario.
        try {
            servicio.buscarBus(id);
        } catch (ExcepcionPersonalizada e) {
            Mensajes.error(request, e.getMessage());
            response.sendRedirect(request.getContextPath() + "/buses");
            return;
        }

        try {
            Bus bus = servicio.actualizar(id, marca, modelo, patente, idViaje);
            Mensajes.exito(request, "Bus " + bus.getPatente() + " actualizado correctamente.");
            response.sendRedirect(request.getContextPath() + "/buses");
        } catch (ExcepcionPersonalizada e) {
            request.setAttribute("id", id);
            request.setAttribute("marca", marca);
            request.setAttribute("modelo", modelo);
            request.setAttribute("patente", patente);
            request.setAttribute("idViaje", idViaje);
            request.setAttribute("errores", e.getErrores());
            cargarViajes(request);
            request.getRequestDispatcher(VISTA).forward(request, response);
        }
    }

    private void cargarViajes(HttpServletRequest request) {
        try {
            request.setAttribute("viajes", servicio.listarViajes());
        } catch (ExcepcionPersonalizada e) {
            request.setAttribute("viajes", Collections.emptyList());
            request.setAttribute(Mensajes.ERROR, e.getMessage());
        }
    }
}
