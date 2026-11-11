package cl.duoc.vivero.repository;

import cl.duoc.vivero.model.Planta;
import cl.duoc.vivero.model.PlantaExterior;
import cl.duoc.vivero.model.PlantaInterior;

import java.util.ArrayList;
import java.util.List;

public class PlantaRepository {
    public static final  String ARCHIVO_INTERIOR = "interiores.json";
    public static final String ARCHIVO_EXTERIOR = "exteriores.json";

    private final PlantaDao<PlantaInterior> interiorDao;
    private final PlantaDao<PlantaExterior> exteriorDao;
    public final List<PlantaInterior> interiores = new ArrayList<>();
    public final List<PlantaExterior> exteriores = new ArrayList<>();

    public PlantaRepository(){
        this(new GsonPlantaDao<>(ARCHIVO_INTERIOR,PlantaInterior.class),new GsonPlantaDao<>(ARCHIVO_EXTERIOR,PlantaExterior.class));
    }
    public PlantaRepository(PlantaDao<PlantaInterior> interiorDao, PlantaDao<PlantaExterior> exteriorDao){
        this.interiorDao = interiorDao;
        this.exteriorDao = exteriorDao;
    }

    public void cargar(){
        List<PlantaInterior> leidosinterior = interiorDao.leer();
        List<PlantaExterior> leidosexterior = exteriorDao.leer();
        this.interiores.clear();
        this.exteriores.clear();
        this.interiores.addAll(leidosinterior);
        this.exteriores.addAll(leidosexterior);
    }
    public List<Planta> listar(){
        List<Planta> todas = new ArrayList<>();
        todas.addAll(this.exteriores);
        return todas;
    }

    public void  registrar(Planta planta){
        if(planta instanceof PlantaInterior interior){
            this.interiores.add(interior);
            try {
                interiorDao.guardar(this.interiores);
            } catch (PersistenciaException e){
                this.interiores.remove(interiores.size()-1);
                throw e;
            }
        } else if (planta instanceof PlantaInterior exterior) {
            this.interiores.add(exterior);
            try {
                exteriorDao.guardar(this.exteriores);
            } catch (PersistenciaException e){
                this.interiores.remove(exteriores.size()-1);
                throw e;
            }
        }else{
            throw new  IllegalArgumentException("Tipo de planta no soportada");
        }
    }

    public boolean eliminar(Planta planta){
        if(planta instanceof PlantaInterior){
            return this.eliminarDe(interiores,interiorDao,planta);
        } else if (planta instanceof PlantaExterior ){
            return this.eliminarDe(exteriores,exteriorDao,planta);
        }
        return false;
    }

    private <T extends Planta> boolean eliminarDe(List<T> lista,PlantaDao<T> dao,Planta planta){
        int posicion = indiceDe(lista, planta);
        if (posicion < 0){
            return false;
        }
        T eliminada = lista.remove(posicion);
        try {
            dao.guardar(lista);
        }catch (PersistenciaException e){
            lista.add(posicion, eliminada);
            throw e;
        }
        return true;
    }
    private  static  int indiceDe(List<? extends Planta> lista, Planta planta){
        for(int i = 0; i < lista.size(); i++){
            if(lista.get(i) == planta){
                return i;
            }
        }
        return -1;
    }
}