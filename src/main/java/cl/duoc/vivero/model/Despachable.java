package cl.duoc.vivero.model;

/**
 * Contrato que cumplen las plantas que pueden despacharse a domicilio.
 * El estado no tiene setter: se cambia con marcarListaParaDespacho().
 */
public interface Despachable {

    /**
     * Indica si la planta está lista para ser despachada.
     *
     * @return true si está lista para despacho
     */
    boolean estaListaParaDespacho();

    /**
     * Registra que la planta está lista para despacho.
     */
    void marcarListaParaDespacho();
}
