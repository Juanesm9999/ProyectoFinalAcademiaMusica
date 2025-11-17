package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.AulaController;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.ClaseController;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.CursoController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ClaseViewController {
    private App app;
    private ClaseController claseController;
    private ObservableList<Clase> listClases = FXCollections.observableArrayList();
    private Clase selectedClase;

    @FXML private TextField txtId;
    @FXML private TextField txtHorario;
    @FXML private ComboBox<String> cmbDiaSemana;
    @FXML private TextField txtHoraInicio;
    @FXML private TextField txtHoraFin;
    @FXML private ComboBox<TipoInstrumento> cmbInstrumento;
    @FXML private Spinner<Integer> spnNivel;
    @FXML private ComboBox<String> cmbTipoClase;
    @FXML private ComboBox<Curso> cmbCurso;
    @FXML private ComboBox<Aula> cmbAula;
    @FXML private CheckBox chkActiva;

    // Campos específicos para clase grupal
    @FXML private TextField txtCapacidadMaxima;
    @FXML private TextArea txtDescripcion;

    // Campos específicos para clase individual
    @FXML private TextField txtTemaEspecifico;
    @FXML private TextField txtObjetivos;
    @FXML private TextArea txtObservaciones;

    @FXML private TableView<Clase> tblClases;
    @FXML private TableColumn<Clase, String> tbcId;
    @FXML private TableColumn<Clase, String> tbcTipo;
    @FXML private TableColumn<Clase, String> tbcInstrumento;
    @FXML private TableColumn<Clase, String> tbcCurso;
    @FXML private TableColumn<Clase, String> tbcNivel;
    @FXML private TableColumn<Clase, String> tbcDia;
    @FXML private TableColumn<Clase, String> tbcHorario;
    @FXML private TableColumn<Clase, String> tbcAula;
    @FXML private TableColumn<Clase, String> tbcEstado;

    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;

    @FXML
    void initialize() {
        cmbDiaSemana.setItems(FXCollections.observableArrayList(
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        ));

        cmbInstrumento.setItems(FXCollections.observableArrayList(TipoInstrumento.values()));

        cmbTipoClase.setItems(FXCollections.observableArrayList("Grupal", "Individual"));

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        spnNivel.setValueFactory(valueFactory);

        chkActiva.setSelected(true);

        // Listener para mostrar/ocultar campos según tipo de clase
        cmbTipoClase.valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarCamposSegunTipo(newVal);
        });
    }
    
    private void cargarCombos() {
        // Cargar cursos
        if (cmbCurso != null) {
            CursoController cursoController = new CursoController(app.getSistemaAcademia());
            cmbCurso.setItems(FXCollections.observableArrayList(cursoController.obtenerListaCursos()));
            cmbCurso.setCellFactory(param -> new ListCell<Curso>() {
                @Override
                protected void updateItem(Curso item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getCodigo() + " - " + item.getNombre());
                    }
                }
            });
        }
        
        // Cargar aulas
        if (cmbAula != null) {
            AulaController aulaController = new AulaController(app.getSistemaAcademia());
            cmbAula.setItems(FXCollections.observableArrayList(aulaController.obtenerListaAulas()));
            cmbAula.setCellFactory(param -> new ListCell<Aula>() {
                @Override
                protected void updateItem(Aula item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getCodigo() + " - " + item.getNombre());
                    }
                }
            });
        }
    }

    public void setApp(App app) {
        this.app = app;
        claseController = new ClaseController(app.getSistemaAcademia());
        cargarCombos();
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerClases();
        tblClases.setItems(listClases);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcId.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId()));
        tbcTipo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue() instanceof ClaseGrupal ? "Grupal" : "Individual"));
        tbcInstrumento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getInstrumento() != null ? 
                        cellData.getValue().getInstrumento().toString() : "N/A"));
        tbcCurso.setCellValueFactory(cellData -> {
            Curso curso = buscarCursoDeClase(cellData.getValue());
            return new SimpleStringProperty(curso != null ? curso.getCodigo() + " - " + curso.getNombre() : "Sin asignar");
        });
        tbcNivel.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getNivel())));
        tbcDia.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDiaSemana() != null ? 
                        cellData.getValue().getDiaSemana() : "N/A"));
        tbcHorario.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        (cellData.getValue().getHoraInicio() != null ? cellData.getValue().getHoraInicio() : "") + 
                        " - " + 
                        (cellData.getValue().getHoraFin() != null ? cellData.getValue().getHoraFin() : "")));
        tbcAula.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTheAula() != null ? 
                        cellData.getValue().getTheAula().getCodigo() : "Sin asignar"));
        tbcEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isActiva() ? "Activa" : "Inactiva"));
    }
    
    private Curso buscarCursoDeClase(Clase clase) {
        for (Curso curso : app.getSistemaAcademia().getListCursos()) {
            if (clase instanceof ClaseGrupal && curso.getTheClases().contains(clase)) {
                return curso;
            }
        }
        return null;
    }

    private void obtenerClases() {
        listClases.clear();
        listClases.addAll(claseController.obtenerListaClases());
    }

    private void listenerSelection() {
        tblClases.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedClase = newSelection;
            mostrarInformacion(selectedClase);
        });
    }

    private void mostrarInformacion(Clase clase) {
        if (clase != null) {
            txtId.setText(clase.getId());
            txtHorario.setText(clase.getHorario());
            cmbDiaSemana.setValue(clase.getDiaSemana());
            txtHoraInicio.setText(clase.getHoraInicio());
            txtHoraFin.setText(clase.getHoraFin());
            cmbInstrumento.setValue(clase.getInstrumento());
            spnNivel.getValueFactory().setValue(clase.getNivel());
            chkActiva.setSelected(clase.isActiva());
            
            // Asignar aula
            if (clase.getTheAula() != null && cmbAula != null) {
                cmbAula.setValue(clase.getTheAula());
            }
            
            // Asignar curso
            Curso curso = buscarCursoDeClase(clase);
            if (curso != null && cmbCurso != null) {
                cmbCurso.setValue(curso);
            }

            if (clase instanceof ClaseGrupal) {
                cmbTipoClase.setValue("Grupal");
                ClaseGrupal grupal = (ClaseGrupal) clase;
                txtCapacidadMaxima.setText(String.valueOf(grupal.getCapacidadMaxima()));
                txtDescripcion.setText(grupal.getDescripcion());
            } else if (clase instanceof ClaseIndividual) {
                cmbTipoClase.setValue("Individual");
                ClaseIndividual individual = (ClaseIndividual) clase;
                txtTemaEspecifico.setText(individual.getTemaEspecifico());
                txtObjetivos.setText(individual.getObjetivos());
                txtObservaciones.setText(individual.getObservaciones());
            }
        }
    }

    private void actualizarCamposSegunTipo(String tipo) {
        boolean esGrupal = "Grupal".equals(tipo);

        // Campos para clase grupal
        txtCapacidadMaxima.setDisable(!esGrupal);
        txtDescripcion.setDisable(!esGrupal);

        // Campos para clase individual
        txtTemaEspecifico.setDisable(esGrupal);
        txtObjetivos.setDisable(esGrupal);
        txtObservaciones.setDisable(esGrupal);
    }

    @FXML
    void onAgregar() {
        if (!validarCampos()) {
            return;
        }

        // Verificar conflicto de aula, día y horario
        if (cmbAula.getValue() != null) {
            boolean hayConflicto = claseController.verificarConflictoAulaHorario(
                    cmbAula.getValue(),
                    cmbDiaSemana.getValue(),
                    txtHoraInicio.getText(),
                    txtHoraFin.getText()
            );

            if (hayConflicto) {
                mostrarAlerta("Error", 
                        "Ya existe una clase activa en el mismo aula, día y horario.\n" +
                        "Aula: " + cmbAula.getValue().getCodigo() + "\n" +
                        "Día: " + cmbDiaSemana.getValue() + "\n" +
                        "Horario: " + txtHoraInicio.getText() + " - " + txtHoraFin.getText(),
                        Alert.AlertType.ERROR);
                return;
            }
        }

        String tipoClase = cmbTipoClase.getValue();
        boolean exito = false;

        if ("Grupal".equals(tipoClase)) {
            ClaseGrupal claseGrupal = buildClaseGrupal();
            exito = claseController.crearClaseGrupal(claseGrupal);
            if (exito) {
                listClases.add(claseGrupal);
            }
        } else {
            ClaseIndividual claseIndividual = buildClaseIndividual();
            exito = claseController.crearClaseIndividual(claseIndividual);
            if (exito) {
                listClases.add(claseIndividual);
            }
        }

        if (exito) {
            limpiarCampos();
            mostrarAlerta("Éxito", "Clase creada correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No se pudo crear la clase. Verifique que el ID no esté duplicado", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onActualizar() {
        if (selectedClase == null) {
            mostrarAlerta("Advertencia", "Seleccione una clase de la tabla", Alert.AlertType.WARNING);
            return;
        }

        if (!validarCampos()) {
            return;
        }

        // Verificar conflicto de aula, día y horario (excluyendo la clase actual)
        if (cmbAula.getValue() != null) {
            boolean hayConflicto = claseController.verificarConflictoAulaHorario(
                    cmbAula.getValue(),
                    cmbDiaSemana.getValue(),
                    txtHoraInicio.getText(),
                    txtHoraFin.getText(),
                    selectedClase.getId() // Excluir la clase actual
            );

            if (hayConflicto) {
                mostrarAlerta("Error", 
                        "Ya existe otra clase activa en el mismo aula, día y horario.\n" +
                        "Aula: " + cmbAula.getValue().getCodigo() + "\n" +
                        "Día: " + cmbDiaSemana.getValue() + "\n" +
                        "Horario: " + txtHoraInicio.getText() + " - " + txtHoraFin.getText(),
                        Alert.AlertType.ERROR);
                return;
            }
        }

        String tipoClase = cmbTipoClase.getValue();
        boolean exito = false;

        if ("Grupal".equals(tipoClase) && selectedClase instanceof ClaseGrupal) {
            ClaseGrupal original = (ClaseGrupal) selectedClase;
            
            // Actualizar campos básicos directamente
            original.setHorario(txtHorario.getText());
            original.setDiaSemana(cmbDiaSemana.getValue());
            original.setHoraInicio(txtHoraInicio.getText());
            original.setHoraFin(txtHoraFin.getText());
            original.setInstrumento(cmbInstrumento.getValue());
            original.setNivel(spnNivel.getValue());
            original.setActiva(chkActiva.isSelected());
            
            // Actualizar campos específicos de ClaseGrupal
            if (!txtCapacidadMaxima.getText().isEmpty()) {
                int nuevaCapacidad = Integer.parseInt(txtCapacidadMaxima.getText());
                int diferencia = nuevaCapacidad - original.getCapacidadMaxima();
                original.setCapacidadMaxima(nuevaCapacidad);
                // Ajustar cupos disponibles
                original.setCuposDisponibles(original.getCuposDisponibles() + diferencia);
            }
            original.setDescripcion(txtDescripcion.getText());
            
            // Actualizar aula
            if (cmbAula.getValue() != null) {
                original.setTheAula(cmbAula.getValue());
            } else {
                original.setTheAula(null);
            }
            
            // Actualizar curso si cambió
            Curso cursoAnterior = buscarCursoDeClase(original);
            if (cursoAnterior != null && (cmbCurso.getValue() == null || cmbCurso.getValue() != cursoAnterior)) {
                cursoAnterior.getTheClases().remove(original);
            }
            if (cmbCurso.getValue() != null && !cmbCurso.getValue().getTheClases().contains(original)) {
                cmbCurso.getValue().getTheClases().add(original);
            }
            
            exito = true;
            
        } else if ("Individual".equals(tipoClase) && selectedClase instanceof ClaseIndividual) {
            ClaseIndividual original = (ClaseIndividual) selectedClase;
            
            // Actualizar campos básicos directamente
            original.setHorario(txtHorario.getText());
            original.setDiaSemana(cmbDiaSemana.getValue());
            original.setHoraInicio(txtHoraInicio.getText());
            original.setHoraFin(txtHoraFin.getText());
            original.setInstrumento(cmbInstrumento.getValue());
            original.setNivel(spnNivel.getValue());
            original.setActiva(chkActiva.isSelected());
            
            // Actualizar campos específicos de ClaseIndividual
            original.setTemaEspecifico(txtTemaEspecifico.getText());
            original.setObjetivos(txtObjetivos.getText());
            original.setObservaciones(txtObservaciones.getText());
            
            // Actualizar aula
            if (cmbAula.getValue() != null) {
                original.setTheAula(cmbAula.getValue());
            } else {
                original.setTheAula(null);
            }
            
            exito = true;
            
        } else {
            mostrarAlerta("Error", "El tipo de clase seleccionado no coincide con la clase original", Alert.AlertType.ERROR);
            return;
        }

        if (exito) {
            // Actualizar en la lista observable y refrescar tabla
            tblClases.refresh();
            limpiarSeleccion();
            mostrarAlerta("Éxito", "Clase actualizada correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No se pudo actualizar la clase", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEliminar() {
        if (selectedClase != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar esta aula?");
            confirmacion.setContentText(selectedClase.getId());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (claseController.eliminarClase(selectedClase.getId())) {
                    listClases.remove(selectedClase);
                    limpiarSeleccion();
                    mostrarAlerta("Éxito", "Clase eliminada correctamente", Alert.AlertType.INFORMATION);
                }
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione una clase de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void onRegresar() {
        app.openAdministradorDashboard(null);
    }

    private ClaseGrupal buildClaseGrupal() {
        int capacidad = Integer.parseInt(txtCapacidadMaxima.getText());

        ClaseGrupal claseGrupal = new ClaseGrupal(
                capacidad,
                0, // capacidad actual
                capacidad, // cupos disponibles
                txtDescripcion.getText(),
                txtId.getText(),
                txtHorario.getText(),
                cmbDiaSemana.getValue(),
                txtHoraInicio.getText(),
                txtHoraFin.getText(),
                cmbInstrumento.getValue(),
                spnNivel.getValue(),
                chkActiva.isSelected()
        );
        
        // Asignar aula si está seleccionada
        if (cmbAula.getValue() != null) {
            claseGrupal.setTheAula(cmbAula.getValue());
        }
        
        // Asignar curso si está seleccionado
        if (cmbCurso.getValue() != null) {
            cmbCurso.getValue().getTheClases().add(claseGrupal);
        }
        
        return claseGrupal;
    }

    private ClaseIndividual buildClaseIndividual() {
        ClaseIndividual claseIndividual = new ClaseIndividual(
                txtTemaEspecifico.getText(),
                txtObjetivos.getText(),
                txtObservaciones.getText(),
                txtId.getText(),
                txtHorario.getText(),
                cmbDiaSemana.getValue(),
                txtHoraInicio.getText(),
                txtHoraFin.getText(),
                cmbInstrumento.getValue(),
                spnNivel.getValue(),
                chkActiva.isSelected()
        );
        
        // Asignar aula si está seleccionada
        if (cmbAula.getValue() != null) {
            claseIndividual.setTheAula(cmbAula.getValue());
        }
        
        return claseIndividual;
    }

    private boolean validarCampos() {
        if (txtId.getText().isEmpty() || cmbDiaSemana.getValue() == null ||
                txtHoraInicio.getText().isEmpty() || txtHoraFin.getText().isEmpty() ||
                cmbInstrumento.getValue() == null || cmbTipoClase.getValue() == null) {
            mostrarAlerta("Error", "Complete todos los campos obligatorios", Alert.AlertType.ERROR);
            return false;
        }

        if ("Grupal".equals(cmbTipoClase.getValue())) {
            if (txtCapacidadMaxima.getText().isEmpty()) {
                mostrarAlerta("Error", "Ingrese la capacidad máxima", Alert.AlertType.ERROR);
                return false;
            }
            try {
                Integer.parseInt(txtCapacidadMaxima.getText());
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "La capacidad debe ser un número válido", Alert.AlertType.ERROR);
                return false;
            }
        }

        return true;
    }

    private void limpiarSeleccion() {
        tblClases.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.clear();
        txtHorario.clear();
        cmbDiaSemana.setValue(null);
        txtHoraInicio.clear();
        txtHoraFin.clear();
        cmbInstrumento.setValue(null);
        spnNivel.getValueFactory().setValue(1);
        cmbTipoClase.setValue(null);
        if (cmbCurso != null) cmbCurso.setValue(null);
        if (cmbAula != null) cmbAula.setValue(null);
        chkActiva.setSelected(true);
        txtCapacidadMaxima.clear();
        txtDescripcion.clear();
        txtTemaEspecifico.clear();
        txtObjetivos.clear();
        txtObservaciones.clear();
        selectedClase = null;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}