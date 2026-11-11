package cl.duoc.vivero.repository;

import cl.duoc.vivero.controller.MainController;
import cl.duoc.vivero.model.Planta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.sun.tools.javac.Main;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonPlantaDao<T extends Planta> implements  PlantaDao<T> {
    private final Path archivo;
    private final Type tipoLista;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GsonPlantaDao(String archivo,Class<T> clase){
        //iMPLEMENTAER CARPETA PARA DATOS
        this.archivo = Path.of(archivo);
        this.tipoLista = TypeToken.getParameterized(List.class, clase).getType();
    }
    @Override
    public List<T> leer() {
        if (Files.notExists(this.archivo)){
            return  new ArrayList<>();
        }
        try(Reader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)){
            List<T> plantas = gson.fromJson(lector, tipoLista);
            if (plantas == null){
                return new ArrayList<>();
            }
            List<T> validas = new ArrayList<>();
            for(T planta : plantas){
                if (planta != null && planta.getNombre() != null) {
                    validas.add(planta);
                }
            }
            return validas;
        }catch (IOException e){
            throw new PersistenciaException(PersistenciaException.ALERTA_LECTURA,e);
        }
    }

    @Override
    public void guardar(List<T> plantas) {
        try(Writer escritor = Files.newBufferedWriter(this.archivo,StandardCharsets.UTF_8)){
            this.gson.toJson(plantas,this.tipoLista,escritor);
        }catch (IOException | JsonParseException e){
            throw new PersistenciaException(PersistenciaException.ALERTA_ESCRITURA,e);
        }

    }
}