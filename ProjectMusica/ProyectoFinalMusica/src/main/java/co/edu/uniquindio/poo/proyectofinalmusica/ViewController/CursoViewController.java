package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.CursoController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Curso;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CursoViewController {
    private App app;
    private CursoController cursoController;
    private ObservableList<Curso> listCursos = FXCollections.observableArrayList();
    private Curso selectedCurso;

    @FXML private TextField txtId;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<TipoInstrumento> cmbInstrumento;
    @FXML private Spinner<Integer> spnNivel;
    @FXML private ComboBox<EstadoCurso> cmbEstado;
    @FXML private TextField txtCapacidadMaxima;
    @FXML private TextField txtDuracionSemanas;
    @FXML private DatePicker dpFechaInicio;
    @FXML private DatePicker dpFechaFin;
    @FXML private TextArea txtDescripcion;

    @FXML private TableView<Curso> tblCursos;
    @FXML private TableColumn<Curso, String> tbcCodigo;
    @FXML private TableColumn<Curso, String> tbcNombre;
    @FXML private TableColumn<Curso, String> tbcInstrumento;
    @FXML private TableColumn<Curso, String> tbcNivel;
    @FXML private TableColumn<Curso, String> tbcEstado;
    @FXML private TableColumn<Curso, String> tbcCapacidad;
    @FXML private TableColumn<Curso, String> tbcFechaInicio;
    @FXML private TableColumn<Curso, String> tbcFechaFin;
    @FXML private TableColumn<Curso, String> tbcDuracion;

    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;

    @FXML
    void initialize() {
        cmbInstrumento.setItems(FXCollections.observableArrayList(TipoInstrumento.values()));
        cmbEstado.setItems(FXCollections.observableArrayList(EstadoCurso.values()));

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        spnNivel.setValueFactory(valueFactory);
    }

    public void setApp(App app) {
        this.app = app;
        cursoController = new CursoController(app.getSistemaAcademia());
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerCursos();
        tblCursos.setItems(listCursos);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCodigo()));
        tbcNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcInstrumento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getInstrumento() != null ? 
                        cellData.getValue().getInstrumento().toString() : "N/A"));
        tbcNivel.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getNivel())));
        tbcEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstado() != null ? 
                        cellData.getValue().getEstado().toString() : "N/A"));
        tbcCapacidad.setCellValueFactory(cellData -> {
            int actual = cellData.getValue().getCapacidadActual();
            int maxima = cellData.getValue().getCapacidadMaxima();
            return new SimpleStringProperty(actual + "/" + maxima);
        });
        tbcFechaInicio.setCellValueFactory(cellData -> {
            String fecha = cellData.getValue().getFechaInicio();
            return new SimpleStringProperty(fecha != null && !fecha.isEmpty() ? fecha : "No definida");
        });
        tbcFechaFin.setCellValueFactory(cellData -> {
            String fecha = cellData.getValue().getFechaFin();
            return new SimpleStringProperty(fecha != null && !fecha.isEmpty() ? fecha : "No definida");
        });
        tbcDuracion.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDuracionSemanas() + " semanas"));
    }

    private void obtenerCursos() {
        listCursos.clear();
        listCursos.addAll(cursoController.obtenerListaCursos());
    }

    private void listenerSelection() {
        tblCursos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedCurso = newSelection;
            mostrarInformacion(selectedCurso);
        });
    }

    private void mostrarInformacion(Curso curso) {
        if (curso != null) {
            txtId.setText(curso.getId());
            txtCodigo.setText(curso.getCodigo());
            txtNombre.setText(curso.getNombre());
            cmbInstrumento.setValue(curso.getInstrumento());
            spnNivel.getValueFactory().setValue(curso.getNivel());
            cmbEstado.setValue(curso.getEstado());
            txtCapacidadMaxima.setText(String.valueOf(curso.getCapacidadMaxima()));
            txtDuracionSemanas.setText(String.valueOf(curso.getDuracionSemanas()));
            txtDescripcion.setText(curso.getDescripcion());

            if (curso.getFechaInicio() != null && !curso.getFechaInicio().isEmpty()) {
                dpFechaInicio.setValue(java.time.LocalDate.parse(curso.getFechaInicio()));
            }
            if (curso.getFechaFin() != null && !curso.getFechaFin().isEmpty()) {
                dpFechaFin.setValue(java.time.LocalDate.parse(curso.getFechaFin()));
            }
        }
    }

    @FXML
    void onAgregar() {
        if (validarCampos()) {
            Curso curso = buildCurso();
            if (cursoController.crearCurso(curso)) {
                listCursos.add(curso);
                limpiarCampos();
                mostrarAlerta("Éxito", "Curso creado correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "El curso ya existe", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizar() {
        if (selectedCurso != null && validarCampos()) {
            Curso actualizado = buildCurso();
            if (cursoController.modificarCurso(selectedCurso.getId(), actualizado)) {
                int index = listCursos.indexOf(selectedCurso);
                if (index >= 0) {
                    listCursos.set(index, actualizado);
                }
                tblCursos.refresh();
                limpiarSeleccion();
                mostrarAlerta("Éxito", "Curso actualizado correctamente", Alert.AlertType.INFORMATION);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un curso de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminar() {
        if (selectedCurso != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este curso?");
            confirmacion.setContentText(selectedCurso.getNombre());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (cursoController.eliminarCurso(selectedCurso.getId())) {
                    listCursos.remove(selectedCurso);
                    limpiarSeleccion();
                    mostrarAlerta("Éxito", "Curso eliminado correctamente", Alert.AlertType.INFORMATION);
                }
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un curso de la tabla", Alert.AlertType.WARNING);
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

    private Curso buildCurso() {
        String fechaInicio = dpFechaInicio.getValue() != null ? dpFechaInicio.getValue().toString() : "";
        String fechaFin = dpFechaFin.getValue() != null ? dpFechaFin.getValue().toString() : "";

        return new Curso(
                txtId.getText(),
                txtCodigo.getText(),
                txtNombre.getText(),
                cmbInstrumento.getValue(),
                spnNivel.getValue(),
                txtDescripcion.getText(),
                Integer.parseInt(txtCapacidadMaxima.getText()),
                0, // capacidad actual inicia en 0
                cmbEstado.getValue(),
                fechaInicio,
                fechaFin,
                Integer.parseInt(txtDuracionSemanas.getText())
        );
    }

    private boolean validarCampos() {
        if (txtId.getText().isEmpty() || txtCodigo.getText().isEmpty() ||
                txtNombre.getText().isEmpty() || cmbInstrumento.getValue() == null ||
                cmbEstado.getValue() == null || txtCapacidadMaxima.getText().isEmpty() ||
                txtDuracionSemanas.getText().isEmpty()) {
            mostrarAlerta("Error", "Todos los campos obligatorios deben estar llenos", Alert.AlertType.ERROR);
            return false;
        }

        try {
            Integer.parseInt(txtCapacidadMaxima.getText());
            Integer.parseInt(txtDuracionSemanas.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Los valores numéricos deben ser válidos", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void limpiarSeleccion() {
        tblCursos.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.clear();
        txtCodigo.clear();
        txtNombre.clear();
        cmbInstrumento.setValue(null);
        spnNivel.getValueFactory().setValue(1);
        cmbEstado.setValue(null);
        txtCapacidadMaxima.clear();
        txtDuracionSemanas.clear();
        dpFechaInicio.setValue(null);
        dpFechaFin.setValue(null);
        txtDescripcion.clear();
        selectedCurso = null;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}