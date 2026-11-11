package cl.duoc.vivero.repository;

public class PersistenciaException extends RuntimeException{
    public final static String ALERTA_LECTURA="Error al leer archivo";
    public final static String ALERTA_ESCRITURA="Error al escribir archivo";

    public PersistenciaException(String message, Throwable cause){super(message, cause);};
}
