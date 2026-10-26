package cl.uss.transporte.controlador;

import cl.uss.transporte.modelo.Bus;
import cl.uss.transporte.modelo.ExcepcionPersonalizada;
import cl.uss.transporte.servicio.BusServicio;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/buses/eliminar")
public class EliminarBusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final transient BusServicio servicio = new BusServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Bus bus = servicio.buscarBus(request.getParameter("id"));
            request.setAttribute("bus", bus);
            request.getRequestDispatcher("/WEB-INF/vistas/eliminar-bus.jsp").forward(request, response);
        } catch (ExcepcionPersonalizada e) {
            Mensajes.error(request, e.getMessage());
            response.sendRedirect(request.getContextPath() + "/buses");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            Bus eliminado = servicio.eliminar(request.getParameter("id"));
            Mensajes.exito(request, "Bus " + eliminado.getPatente() + " eliminado correctamente.");
        } catch (ExcepcionPersonalizada e) {
            Mensajes.error(request, e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/buses");
    }
}
