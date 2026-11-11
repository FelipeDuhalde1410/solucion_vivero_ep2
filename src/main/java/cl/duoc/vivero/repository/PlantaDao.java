package cl.duoc.vivero.repository;

import cl.duoc.vivero.model.Planta;
import java.util.List;

public interface PlantaDao<T extends Planta> {
    List<T> leer();
    void guardar(List<T> plantas);
}
