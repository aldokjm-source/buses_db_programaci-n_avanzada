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


@WebServlet("/buses/crear")
public class CrearBusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String VISTA = "/WEB-INF/vistas/crear-bus.jsp";

    private final transient BusServicio servicio = new BusServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarViajes(request);
        request.getRequestDispatcher(VISTA).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String marca = request.getParameter("marca");
        String modelo = request.getParameter("modelo");
        String patente = request.getParameter("patente");
        String idViaje = request.getParameter("idViaje");

        try {
            Bus bus = servicio.crear(marca, modelo, patente, idViaje);
            Mensajes.exito(request, "Bus " + bus.getPatente() + " creado correctamente.");
            response.sendRedirect(request.getContextPath() + "/buses");
        } catch (ExcepcionPersonalizada e) {
            
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
