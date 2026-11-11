
package cl.duoc.vivero.controller;

import cl.duoc.vivero.model.Planta;
import cl.duoc.vivero.model.PlantaExterior;
import cl.duoc.vivero.model.PlantaInterior;
import cl.duoc.vivero.repository.PersistenciaException;
import cl.duoc.vivero.repository.PlantaRepository;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class MainController {

    // Constantes para tipoChoice
    public static final String TIPO_EXTERIOR = "Planta Exterior";
    public static final String TIPO_INTERIOR = "Planta Interior";

    // Constantes para alertaLabel
    public static final String ALERTA_DEFAULT = "Sin alerta";
    public static final String ALERTA_EXITO = "Registro Exitoso";
    public static final String ALERTA_NO_VALIDO = "Dato no valido";
    public static final String ALERTA_ELIMINADO = "Registro eliminado";
    public static final String ALERTA_NO_SELECCIONADO = "Falta de seleccion";
    public static final String ALERTA_LECTURA = "Error al leer archivo";
    public static final String ALERTA_ESCRITURA = "Error al escribir archivo";

    // Alertas
    @FXML private Label alertaLabel;

    // Formulario de planta
    @FXML private ChoiceBox<String> tipoChoice;
    @FXML private TextField nombreText;
    @FXML private TextField alturaText;
    @FXML private TextField cantidadText;

    // Campos planta interior
    @FXML private TextField volumenText;

    // Campos planta exterior
    @FXML private TextField especieText;
    @FXML private CheckBox resistenteCheck;
    @FXML private CheckBox disponibleCheck;

    // TableView
    @FXML private TableView<Planta> plantaTableView;
    @FXML private TableColumn<Planta, String> tipoColumn;
    @FXML private TableColumn<Planta, String> nombreColumn;
    @FXML private TableColumn<Planta, Integer> alturaColumn;
    @FXML private TableColumn<Planta, Integer> cantidadColumn;
    @FXML private TableColumn<Planta, String> precioColumn;

    // Repositorio
    private final PlantaRepository repository = new PlantaRepository();

    // Lista observable para actualizar el TableView
    private final ObservableList<Planta> plantas =
            FXCollections.observableArrayList();


    // INICIALIZAR
    @FXML
    public void initialize() {

        // Agregar tipos de plantas
        this.tipoChoice.getItems().add(TIPO_INTERIOR);
        this.tipoChoice.getItems().add(TIPO_EXTERIOR);

        // Bloquear campos inicialmente
        this.volumenText.setDisable(true);
        this.especieText.setDisable(true);

        this.resistenteCheck.setSelected(true);
        this.disponibleCheck.setSelected(true);

        // Conectar lista observable con TableView
        this.plantaTableView.setItems(this.plantas);

        // Configurar columnas
        configurarTableView();

        // Cargar información del repositorio
        try {

            this.repository.cargar();

            this.plantas.clear();

            this.plantas.addAll(this.repository.interiores);
            this.plantas.addAll(this.repository.exteriores);

        } catch (PersistenciaException e) {

            this.setAlertaLabelMensaje(
                    e.getMessage(),
                    "Error en carga inicial de datos"
            );
        }
    }


    // CONFIGURAR TABLA
    private void configurarTableView() {

        this.tipoColumn.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(
                        celda.getValue().getTipo()
                )
        );

        this.nombreColumn.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(
                        celda.getValue().getNombre()
                )
        );

        this.alturaColumn.setCellValueFactory(
                celda -> new ReadOnlyObjectWrapper<>(
                        celda.getValue().getAlturaCm()
                )
        );

        this.cantidadColumn.setCellValueFactory(
                celda -> new ReadOnlyObjectWrapper<>(
                        celda.getValue().getCantidad()
                )
        );
    }


    // SELECCIONAR TIPO DE PLANTA
    @FXML
    public void onSeleccionarTipo() {

        String tipoPlanta = this.tipoChoice.getValue();

        if (tipoPlanta == null) {
            return;
        }

        switch (tipoPlanta) {

            case TIPO_INTERIOR:

                this.volumenText.setDisable(false);

                this.especieText.setDisable(true);

                this.resistenteCheck.setSelected(true);
                this.disponibleCheck.setSelected(true);

                break;

            case TIPO_EXTERIOR:

                this.volumenText.setDisable(true);

                this.especieText.setDisable(false);

                this.resistenteCheck.setSelected(false);
                this.disponibleCheck.setSelected(false);

                break;

            default:

                this.volumenText.setDisable(true);
                this.especieText.setDisable(true);

                this.resistenteCheck.setSelected(true);
                this.disponibleCheck.setSelected(true);

                this.setAlertaLabelMensaje(
                        ALERTA_NO_SELECCIONADO,
                        "Campo Seleccionar Tipo"
                );
        }
    }


    // GUARDAR PLANTA
    @FXML
    public void onGuardar() {

        Planta planta;

        try {

            // Obtener datos generales
            String tipo = this.tipoChoice.getValue();
            String nombre = this.nombreText.getText().trim();

            if (tipo == null || tipo.isBlank()) {
                this.setAlertaLabelMensaje(
                        ALERTA_NO_SELECCIONADO,
                        "Debe seleccionar un tipo de planta"
                );
                return;
            }

            if (nombre.isBlank()) {
                this.setAlertaLabelMensaje(
                        ALERTA_NO_VALIDO,
                        "El nombre no puede estar vacio"
                );
                return;
            }

            int alturaCm = Integer.parseInt(
                    this.alturaText.getText()
            );

            int cantidad = Integer.parseInt(
                    this.cantidadText.getText()
            );

            if (alturaCm <= 0 || cantidad <= 0) {
                this.setAlertaLabelMensaje(
                        ALERTA_NO_VALIDO,
                        "La altura y cantidad deben ser mayores que cero"
                );
                return;
            }

            // Crear planta según su tipo
            switch (tipo) {

                case TIPO_INTERIOR:

                    double volumenMacetaLitros =
                            Double.parseDouble(this.volumenText.getText());

                    if (volumenMacetaLitros <= 0.0) {

                        this.setAlertaLabelMensaje(
                                ALERTA_NO_VALIDO,
                                "El volumen debe ser mayor que cero"
                        );

                        return;
                    }

                    planta = new PlantaInterior(
                            nombre,
                            alturaCm,
                            cantidad,
                            volumenMacetaLitros
                    );

                    break;


                case TIPO_EXTERIOR:

                    String especie = this.especieText.getText().trim();

                    if (especie.isBlank()) {

                        this.setAlertaLabelMensaje(
                                ALERTA_NO_VALIDO,
                                "La especie no puede estar vacia"
                        );

                        return;
                    }

                    boolean resistenteHeladas =
                            this.resistenteCheck.isSelected();

                    boolean listaParaDespacho =
                            this.disponibleCheck.isSelected();

                    planta = new PlantaExterior(
                            nombre,
                            alturaCm,
                            cantidad,
                            especie,
                            resistenteHeladas
                    );

                    if (listaParaDespacho) {
                        ((PlantaExterior) planta).marcarListaParaDespacho();
                    }

                    break;


                default:

                    this.setAlertaLabelMensaje(
                            ALERTA_NO_SELECCIONADO,
                            "Tipo de planta no valido"
                    );

                    return;
            }


            // Registrar planta en el repositorio
            try {

                this.repository.registrar(planta);

                // Actualizar TableView
                this.plantas.add(planta);

            } catch (PersistenciaException e) {

                this.setAlertaLabelMensaje(
                        e.getMessage(),
                        "Error guardando nuevo registro"
                );

                return;
            }


            // Mostrar mensaje de éxito
            this.setAlertaLabelMensaje(
                    ALERTA_EXITO,
                    "Planta - " + planta.getNombre()
            );

            // Limpiar formulario
            onLimpiar();

        } catch (NumberFormatException e) {

            this.setAlertaLabelMensaje(
                    ALERTA_NO_VALIDO,
                    "Altura, cantidad y volumen deben ser números validos"
            );

        } catch (IllegalArgumentException e) {

            this.setAlertaLabelMensaje(
                    ALERTA_NO_VALIDO,
                    e.getMessage()
            );
        }
    }


    // LIMPIAR FORMULARIO
    @FXML
    public void onLimpiar() {

        this.tipoChoice.setValue(null);

        this.nombreText.clear();
        this.alturaText.clear();
        this.cantidadText.clear();

        this.volumenText.clear();
        this.especieText.clear();

        this.resistenteCheck.setSelected(false);
        this.disponibleCheck.setSelected(false);

        this.volumenText.setDisable(true);
        this.especieText.setDisable(true);

        this.setAlertaLabelMensaje(ALERTA_DEFAULT, "");
    }


    // MOSTRAR ALERTAS
    private void setAlertaLabelMensaje(String tipo, String mensaje) {

        if (mensaje == null || mensaje.isBlank()) {
            mensaje = tipo;
        } else {
            mensaje = tipo + ": " + mensaje;
        }

        switch (tipo) {

            case ALERTA_EXITO:
            case ALERTA_ELIMINADO:

                this.alertaLabel.setVisible(true);
                this.alertaLabel.setTextFill(Color.GREEN);

                break;

            case ALERTA_NO_VALIDO:
            case ALERTA_NO_SELECCIONADO:
            case ALERTA_LECTURA:
            case ALERTA_ESCRITURA:

                this.alertaLabel.setVisible(true);
                this.alertaLabel.setTextFill(Color.RED);

                break;

            default:

                this.alertaLabel.setVisible(false);
                this.alertaLabel.setTextFill(Color.BLACK);
        }

        this.alertaLabel.setText(mensaje);
    }
}
