package cl.uss.transporte.controlador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/** Mensajes que sobreviven a una redirección (éxito o error). */
final class Mensajes {

    static final String EXITO = "mensajeExito";
    static final String ERROR = "mensajeError";

    private Mensajes() {
    }

    static void exito(HttpServletRequest request, String texto) {
        request.getSession().setAttribute(EXITO, texto);
    }

    static void error(HttpServletRequest request, String texto) {
        request.getSession().setAttribute(ERROR, texto);
    }

    /** Mueve los mensajes pendientes de la sesión al request actual. */
    static void moverAlRequest(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion == null) {
            return;
        }
        for (String clave : new String[]{EXITO, ERROR}) {
            Object valor = sesion.getAttribute(clave);
            if (valor != null) {
                request.setAttribute(clave, valor);
                sesion.removeAttribute(clave);
            }
        }
    }
}
