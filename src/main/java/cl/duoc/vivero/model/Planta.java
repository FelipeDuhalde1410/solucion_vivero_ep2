package cl.duoc.vivero.model;

/**
 * Planta a la venta en el vivero. Clase abstracta: se registra siempre como
 * PlantaInterior o PlantaExterior.
 *
 * Reglas comunes: nombre no vacio, altura entre 1 y 300 cm y cantidad en
 * stock entre 1 y 500 unidades.
 */
public abstract class Planta {

    public static final int ALTURA_MINIMA = 1;
    public static final int ALTURA_MAXIMA = 300;
    public static final int CANTIDAD_MINIMA = 1;
    public static final int CANTIDAD_MAXIMA = 500;

    private String nombre;
    private int alturaCm;
    private int cantidad;

    /** Constructor sin parámetros, requerido para reconstruir objetos. */
    protected Planta() {
    }

    protected Planta(String nombre, int alturaCm, int cantidad) {
        setNombre(nombre);
        setAlturaCm(alturaCm);
        setCantidad(cantidad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = validarNombre(nombre);
    }

    public int getAlturaCm() {
        return alturaCm;
    }

    public void setAlturaCm(int alturaCm) {
        this.alturaCm = validarAltura(alturaCm);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = validarCantidad(cantidad);
    }

    /** Precio de una unidad, en pesos, segun las reglas de cada tipo de planta. */
    public abstract int calcularPrecio();

    /** "Interior" o "Exterior": se muestra en la tabla sin preguntar por la clase concreta. */
    public abstract String getTipo();

    protected static String validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        return nombre.trim();
    }

    protected static int validarAltura(int alturaCm) {
        if (alturaCm < ALTURA_MINIMA || alturaCm > ALTURA_MAXIMA) {
            throw new IllegalArgumentException("La altura debe estar entre " + ALTURA_MINIMA + " y "
                    + ALTURA_MAXIMA + " cm.");
        }
        return alturaCm;
    }

    protected static int validarCantidad(int cantidad) {
        if (cantidad < CANTIDAD_MINIMA || cantidad > CANTIDAD_MAXIMA) {
            throw new IllegalArgumentException("La cantidad debe estar entre " + CANTIDAD_MINIMA + " y "
                    + CANTIDAD_MAXIMA + " unidades.");
        }
        return cantidad;
    }

    /** Formato de pesos chilenos: $10.800. */
    public static String pesos(int monto) {
        return String.format("$%,d", monto).replace(',', '.');
    }

    @Override
    public String toString() {
        return getTipo() + " | " + nombre + " | " + alturaCm + " cm | " + cantidad + " u. | "
                + pesos(calcularPrecio());
    }
}
