package cl.uss.transporte.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Excepción para errores de validación y de negocio. */
public class ExcepcionPersonalizada extends Exception {

    private static final long serialVersionUID = 1L;

    private final ArrayList<String> errores;

    /** Un solo mensaje (negocio: bus inexistente, ID inválido, etc.). */
    public ExcepcionPersonalizada(String mensaje) {
        super(mensaje);
        this.errores = new ArrayList<>(Collections.singletonList(mensaje));
    }

    /** Varios errores de validación de un formulario. */
    public ExcepcionPersonalizada(List<String> errores) {
        super(String.join(" ", errores));
        this.errores = new ArrayList<>(errores);
    }

    /** Mensaje genérico para el usuario con la causa técnica guardada para el log. */
    public ExcepcionPersonalizada(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.errores = new ArrayList<>(Collections.singletonList(mensaje));
    }

    public List<String> getErrores() {
        return Collections.unmodifiableList(errores);
    }
}
