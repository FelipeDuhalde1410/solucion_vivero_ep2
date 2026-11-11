package cl.duoc.vivero.model;

/**
 * Planta de exterior. Participa en el despacho a domicilio.
 * Precio unitario base de $5.500; si resiste las heladas, el precio sube un 20 %.
 */
public class PlantaExterior extends Planta implements Despachable {

    public static final int PRECIO_BASE = 5_500;
    public static final int RECARGO_HELADAS = 20;

    private String especie;
    private boolean resistenteHeladas;
    private boolean listaParaDespacho;

    /** Constructor sin parámetros, requerido para reconstruir objetos. */
    public PlantaExterior() {
    }

    public PlantaExterior(String nombre, int alturaCm, int cantidad, String especie, boolean resistenteHeladas) {
        super(nombre, alturaCm, cantidad);
        setEspecie(especie);
        setResistenteHeladas(resistenteHeladas);
        this.listaParaDespacho = false;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public boolean isResistenteHeladas() {
        return resistenteHeladas;
    }

    public void setResistenteHeladas(boolean resistenteHeladas) {
        this.resistenteHeladas = resistenteHeladas;
    }

    @Override
    public boolean estaListaParaDespacho() {
        return listaParaDespacho;
    }

    @Override
    public void marcarListaParaDespacho() {
        this.listaParaDespacho = true;
    }

    @Override
    public int calcularPrecio() {
        if (resistenteHeladas) {
            return PRECIO_BASE + PRECIO_BASE * RECARGO_HELADAS / 100;
        }
        return PRECIO_BASE;
    }

    @Override
    public String getTipo() {
        return "Exterior";
    }
}
