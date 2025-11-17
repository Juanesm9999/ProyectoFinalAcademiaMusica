package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.ProfesorController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class ProfesorViewController {
    private App app;
    private ProfesorController profesorController;
    private ObservableList<Profesor> listProfesores = FXCollections.observableArrayList();
    private Profesor selectedProfesor;

    @FXML private TextField txtId;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private TextField txtEspecialidad;
    @FXML private DatePicker dpFechaContratacion;
    @FXML private CheckBox chkActivo;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private ListView<TipoInstrumento> lstInstrumentosDisponibles;
    @FXML private ListView<TipoInstrumento> lstInstrumentosAsignados;

    @FXML private TableView<Profesor> tblProfesores;
    @FXML private TableColumn<Profesor, String> tbcCodigo;
    @FXML private TableColumn<Profesor, String> tbcNombre;
    @FXML private TableColumn<Profesor, String> tbcEmail;
    @FXML private TableColumn<Profesor, String> tbcTelefono;
    @FXML private TableColumn<Profesor, String> tbcEspecialidad;
    @FXML private TableColumn<Profesor, String> tbcInstrumentos;
    @FXML private TableColumn<Profesor, String> tbcFechaContratacion;
    @FXML private TableColumn<Profesor, String> tbcEstado;

    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnAgregarInstrumento;
    @FXML private Button btnQuitarInstrumento;
    @FXML private Button btnRegresar;

    @FXML
    void initialize() {
        chkActivo.setSelected(true);
        lstInstrumentosDisponibles.setItems(FXCollections.observableArrayList(TipoInstrumento.values()));
        lstInstrumentosAsignados.setItems(FXCollections.observableArrayList());
    }

    public void setApp(App app) {
        this.app = app;
        profesorController = new ProfesorController(app.getSistemaAcademia());
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerProfesores();
        tblProfesores.setItems(listProfesores);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCodigo()));
        tbcNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcEmail.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail() != null ? 
                        cellData.getValue().getEmail() : "N/A"));
        tbcTelefono.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTelefono() != null ? 
                        cellData.getValue().getTelefono() : "N/A"));
        tbcEspecialidad.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEspecialidad() != null ? 
                        cellData.getValue().getEspecialidad() : "N/A"));
        tbcInstrumentos.setCellValueFactory(cellData -> {
            List<TipoInstrumento> instrumentos = cellData.getValue().getTheInstrumentosImpartidos();
            if (instrumentos == null || instrumentos.isEmpty()) {
                return new SimpleStringProperty("Sin asignar");
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < instrumentos.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(instrumentos.get(i).toString());
            }
            return new SimpleStringProperty(sb.toString());
        });
        tbcFechaContratacion.setCellValueFactory(cellData -> {
            String fecha = cellData.getValue().getFechaContratacion();
            return new SimpleStringProperty(fecha != null && !fecha.isEmpty() ? fecha : "N/A");
        });
        tbcEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isActivo() ? "Activo" : "Inactivo"));
    }

    private void obtenerProfesores() {
        listProfesores.clear();
        listProfesores.addAll(profesorController.obtenerListaProfesores());
    }

    private void listenerSelection() {
        tblProfesores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedProfesor = newSelection;
            mostrarInformacion(selectedProfesor);
        });
    }

    private void mostrarInformacion(Profesor profesor) {
        if (profesor != null) {
            txtId.setText(profesor.getId());
            txtCodigo.setText(profesor.getCodigo());
            txtNombre.setText(profesor.getNombre());
            txtEmail.setText(profesor.getEmail());
            txtTelefono.setText(profesor.getTelefono());
            txtDireccion.setText(profesor.getDireccion());
            dpFechaNacimiento.setValue(profesor.getFechaNacimiento());
            txtEspecialidad.setText(profesor.getEspecialidad());

            if (profesor.getFechaContratacion() != null && !profesor.getFechaContratacion().isEmpty()) {
                dpFechaContratacion.setValue(LocalDate.parse(profesor.getFechaContratacion()));
            }

            chkActivo.setSelected(profesor.isActivo());
            txtUsuario.setText(profesor.getUsuario());

            lstInstrumentosAsignados.setItems(FXCollections.observableArrayList(profesor.getTheInstrumentosImpartidos()));
        }
    }

    @FXML
    void onAgregar() {
        if (validarCampos()) {
            Profesor profesor = buildProfesor();
            if (profesorController.agregarProfesor(profesor)) {
                listProfesores.add(profesor);
                limpiarCampos();
                mostrarAlerta("Éxito", "Profesor agregado correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "El profesor ya existe", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizar() {
        if (selectedProfesor != null && validarCampos()) {
            Profesor actualizado = buildProfesor();
            if (profesorController.actualizarProfesor(selectedProfesor.getId(), actualizado)) {
                int index = listProfesores.indexOf(selectedProfesor);
                if (index >= 0) {
                    listProfesores.set(index, actualizado);
                }
                tblProfesores.refresh();
                limpiarSeleccion();
                mostrarAlerta("Éxito", "Profesor actualizado correctamente", Alert.AlertType.INFORMATION);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un profesor de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminar() {
        if (selectedProfesor != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este profesor?");
            confirmacion.setContentText(selectedProfesor.getNombre());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (profesorController.eliminarProfesor(selectedProfesor.getId())) {
                    listProfesores.remove(selectedProfesor);
                    limpiarSeleccion();
                    mostrarAlerta("Éxito", "Profesor eliminado correctamente", Alert.AlertType.INFORMATION);
                }
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un profesor de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onAgregarInstrumento() {
        TipoInstrumento instrumento = lstInstrumentosDisponibles.getSelectionModel().getSelectedItem();
        if (instrumento != null && !lstInstrumentosAsignados.getItems().contains(instrumento)) {
            lstInstrumentosAsignados.getItems().add(instrumento);
        }
    }

    @FXML
    void onQuitarInstrumento() {
        TipoInstrumento instrumento = lstInstrumentosAsignados.getSelectionModel().getSelectedItem();
        if (instrumento != null) {
            lstInstrumentosAsignados.getItems().remove(instrumento);
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

    private Profesor buildProfesor() {
        String fechaContratacion = dpFechaContratacion.getValue() != null ?
                dpFechaContratacion.getValue().toString() : "";

        Profesor profesor = new Profesor(
                txtCodigo.getText(),
                txtEspecialidad.getText(),
                fechaContratacion,
                chkActivo.isSelected(),
                txtId.getText(),
                txtNombre.getText(),
                txtEmail.getText(),
                txtTelefono.getText(),
                txtDireccion.getText(),
                dpFechaNacimiento.getValue() != null ? dpFechaNacimiento.getValue() : LocalDate.now(),
                txtUsuario.getText(),
                txtContrasena.getText()
        );

        profesor.getTheInstrumentosImpartidos().addAll(lstInstrumentosAsignados.getItems());
        return profesor;
    }

    private boolean validarCampos() {
        if (txtId.getText().isEmpty() || txtCodigo.getText().isEmpty() ||
                txtNombre.getText().isEmpty() || txtEmail.getText().isEmpty() ||
                txtEspecialidad.getText().isEmpty() || dpFechaNacimiento.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos obligatorios deben estar llenos", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void limpiarSeleccion() {
        tblProfesores.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.clear();
        txtCodigo.clear();
        txtNombre.clear();
        txtEmail.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        dpFechaNacimiento.setValue(null);
        txtEspecialidad.clear();
        dpFechaContratacion.setValue(null);
        chkActivo.setSelected(true);
        txtUsuario.clear();
        txtContrasena.clear();
        lstInstrumentosAsignados.getItems().clear();
        selectedProfesor = null;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}