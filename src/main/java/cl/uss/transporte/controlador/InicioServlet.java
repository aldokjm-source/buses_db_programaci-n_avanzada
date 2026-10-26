package cl.uss.transporte.controlador;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cl.uss.transporte.dao.EstadoConexionDAO;
import cl.uss.transporte.dao.EstadoConexionDAOImpl;

@WebServlet("/inicio")
public class InicioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final EstadoConexionDAO estadoDAO = new EstadoConexionDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        boolean conectado = false;

        try {
            conectado = estadoDAO.verificar();
        } catch (SQLException e) {
            getServletContext().log("No fue posible verificar PostgreSQL.", e);
        }

        if (!conectado) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }

        request.setAttribute("conectado", conectado);
        request.setAttribute("mensaje", conectado
            ? "Conexión con PostgreSQL verificada."
            : "No fue posible conectar. Revise la configuración y los logs de Tomcat.");

        request.getRequestDispatcher("/WEB-INF/vistas/inicio.jsp")
            .forward(request, response);
    }
}
