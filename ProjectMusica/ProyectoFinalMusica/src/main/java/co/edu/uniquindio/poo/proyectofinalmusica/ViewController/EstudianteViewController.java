package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.EstudianteController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class EstudianteViewController {
    private App app;
    private EstudianteController estudianteController;
    private ObservableList<Estudiante> listEstudiantes = FXCollections.observableArrayList();
    private Estudiante selectedEstudiante;

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private TextField txtMatricula;
    @FXML private DatePicker dpFechaIngreso;
    @FXML private CheckBox chkActivo;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;

    @FXML private TableView<Estudiante> tblEstudiantes;
    @FXML private TableColumn<Estudiante, String> tbcId;
    @FXML private TableColumn<Estudiante, String> tbcNombre;
    @FXML private TableColumn<Estudiante, String> tbcEmail;
    @FXML private TableColumn<Estudiante, String> tbcTelefono;
    @FXML private TableColumn<Estudiante, String> tbcMatricula;
    @FXML private TableColumn<Estudiante, String> tbcFechaIngreso;
    @FXML private TableColumn<Estudiante, String> tbcEstado;

    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;

    @FXML
    void initialize() {
        chkActivo.setSelected(true);
    }

    public void setApp(App app) {
        this.app = app;
        estudianteController = new EstudianteController(app.getSistemaAcademia());
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerEstudiantes();
        tblEstudiantes.setItems(listEstudiantes);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcId.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId()));
        tbcNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcEmail.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail() != null ? 
                        cellData.getValue().getEmail() : "N/A"));
        tbcTelefono.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTelefono() != null ? 
                        cellData.getValue().getTelefono() : "N/A"));
        tbcMatricula.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMatricula()));
        tbcFechaIngreso.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaIngreso() != null ? 
                        cellData.getValue().getFechaIngreso().toString() : "N/A"));
        tbcEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getActivo() ? "Activo" : "Inactivo"));
    }

    private void obtenerEstudiantes() {
        listEstudiantes.clear();
        listEstudiantes.addAll(estudianteController.obtenerListaEstudiantes());
    }

    private void listenerSelection() {
        tblEstudiantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedEstudiante = newSelection;
            mostrarInformacion(selectedEstudiante);
        });
    }

    private void mostrarInformacion(Estudiante estudiante) {
        if (estudiante != null) {
            txtId.setText(estudiante.getId());
            txtNombre.setText(estudiante.getNombre());
            txtEmail.setText(estudiante.getEmail());
            txtTelefono.setText(estudiante.getTelefono());
            txtDireccion.setText(estudiante.getDireccion());
            dpFechaNacimiento.setValue(estudiante.getFechaNacimiento());
            txtMatricula.setText(estudiante.getMatricula());
            dpFechaIngreso.setValue(estudiante.getFechaIngreso());
            chkActivo.setSelected(estudiante.getActivo());
            txtUsuario.setText(estudiante.getUsuario());
        }
    }

    @FXML
    void onAgregar() {
        if (validarCampos()) {
            Estudiante estudiante = buildEstudiante();
            if (estudianteController.registrarEstudiante(estudiante)) {
                listEstudiantes.add(estudiante);
                limpiarCampos();
                mostrarAlerta("Éxito", "Estudiante registrado correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "El estudiante ya existe", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizar() {
        if (selectedEstudiante != null && validarCampos()) {
            Estudiante actualizado = buildEstudiante();
            if (estudianteController.actualizarEstudiante(selectedEstudiante.getId(), actualizado)) {
                int index = listEstudiantes.indexOf(selectedEstudiante);
                if (index >= 0) {
                    listEstudiantes.set(index, actualizado);
                }
                tblEstudiantes.refresh();
                limpiarSeleccion();
                mostrarAlerta("Éxito", "Estudiante actualizado correctamente", Alert.AlertType.INFORMATION);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un estudiante de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminar() {
        if (selectedEstudiante != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este estudiante?");
            confirmacion.setContentText(selectedEstudiante.getNombre());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (estudianteController.eliminarEstudiante(selectedEstudiante.getId())) {
                    listEstudiantes.remove(selectedEstudiante);
                    limpiarSeleccion();
                    mostrarAlerta("Éxito", "Estudiante eliminado correctamente", Alert.AlertType.INFORMATION);
                }
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un estudiante de la tabla", Alert.AlertType.WARNING);
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

    private Estudiante buildEstudiante() {
        return new Estudiante(
                txtMatricula.getText(),
                dpFechaIngreso.getValue() != null ? dpFechaIngreso.getValue() : LocalDate.now(),
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
    }

    private boolean validarCampos() {
        if (txtId.getText().isEmpty() || txtNombre.getText().isEmpty() ||
                txtEmail.getText().isEmpty() || txtMatricula.getText().isEmpty() ||
                dpFechaNacimiento.getValue() == null || dpFechaIngreso.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void limpiarSeleccion() {
        tblEstudiantes.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtEmail.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        dpFechaNacimiento.setValue(null);
        txtMatricula.clear();
        dpFechaIngreso.setValue(null);
        chkActivo.setSelected(true);
        txtUsuario.clear();
        txtContrasena.clear();
        selectedEstudiante = null;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}