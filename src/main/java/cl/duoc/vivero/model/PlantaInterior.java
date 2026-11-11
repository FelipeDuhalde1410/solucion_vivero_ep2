package cl.duoc.vivero.model;

/**
 * Planta de interior. Precio unitario base de $8.000; si el volumen de la
 * maceta supera los 5 litros, el precio sube un 25 %.
 */
public class PlantaInterior extends Planta {

    public static final int PRECIO_BASE = 8_000;
    public static final int RECARGO_MACETA_GRANDE = 25;
    public static final double LITROS_MACETA_GRANDE = 5.0;

    private double volumenMacetaLitros;

    /** Constructor sin parámetros, requerido para reconstruir objetos. */
    public PlantaInterior() {
    }

    public PlantaInterior(String nombre, int alturaCm, int cantidad, double volumenMacetaLitros) {
        super(nombre, alturaCm, cantidad);
        setVolumenMacetaLitros(volumenMacetaLitros);
    }

    public double getVolumenMacetaLitros() {
        return volumenMacetaLitros;
    }

    public void setVolumenMacetaLitros(double volumenMacetaLitros) {
        this.volumenMacetaLitros = volumenMacetaLitros;
    }

    @Override
    public int calcularPrecio() {
        if (volumenMacetaLitros > LITROS_MACETA_GRANDE) {
            return PRECIO_BASE + PRECIO_BASE * RECARGO_MACETA_GRANDE / 100;
        }
        return PRECIO_BASE;
    }

    @Override
    public String getTipo() {
        return "Interior";
    }
}
