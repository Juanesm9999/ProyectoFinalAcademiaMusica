package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.controller.EstudianteController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EstudianteViewController {
    EstudianteController estudianteController;
    ObservableList<Estudiante> listEstudiantes = FXCollections.observableArrayList();
    Estudiante selectedEstudiante;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtDireccion;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private DatePicker dpFechaIngreso;

    @FXML
    private RadioButton rbActivoSi;

    @FXML
    private RadioButton rbActivoNo;

    @FXML
    private ToggleGroup toggleGroupActivo;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnRegresarMenu;

    @FXML
    private TableView<Estudiante> tblListEstudiantes;

    @FXML
    private TableColumn<Estudiante, String> tbcId;

    @FXML
    private TableColumn<Estudiante, String> tbcNombre;

    @FXML
    private TableColumn<Estudiante, String> tbcMatricula;

    @FXML
    private TableColumn<Estudiante, String> tbcEmail;

    @FXML
    private TableColumn<Estudiante, String> tbcTelefono;

    @FXML
    private TableColumn<Estudiante, String> tbcDireccion;

    @FXML
    private TableColumn<Estudiante, String> tbcFechaNacimiento;

    @FXML
    private TableColumn<Estudiante, String> tbcFechaIngreso;

    @FXML
    private TableColumn<Estudiante, Boolean> tbcActivo;

    private App app;

    @FXML
    void onRegresarMenu() {
        app.openViewPrincipal();
    }

    @FXML
    void onAgregar() {
        agregarEstudiante();
    }

    @FXML
    void onActualizar() {
        actualizarEstudiante();
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void onEliminar() {
        eliminarEstudiante();
    }

    @FXML
    void initialize() {
        toggleGroupActivo = new ToggleGroup();
        rbActivoSi.setToggleGroup(toggleGroupActivo);
        rbActivoNo.setToggleGroup(toggleGroupActivo);
        rbActivoSi.setSelected(true);
    }

    public void setApp(App app) {
        this.app = app;
        estudianteController = new EstudianteController(app.sistemaAcademia);
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerEstudiantes();
        tblListEstudiantes.getItems().clear();
        tblListEstudiantes.setItems(listEstudiantes);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        tbcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcMatricula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricula()));
        tbcEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        tbcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        tbcDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        tbcFechaNacimiento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaNacimiento() != null ? cellData.getValue().getFechaNacimiento().toString() : ""));
        tbcFechaIngreso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaIngreso() != null ? cellData.getValue().getFechaIngreso().toString() : ""));
        tbcActivo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getActivo()));
    }

    private void obtenerEstudiantes() {
        listEstudiantes.addAll(estudianteController.obtenerListaEstudiantes());
    }

    private void listenerSelection() {
        tblListEstudiantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedEstudiante = newSelection;
            mostrarInformacionEstudiante(selectedEstudiante);
        });
    }

    private void mostrarInformacionEstudiante(Estudiante estudiante) {
        if (estudiante != null) {
            txtId.setText(estudiante.getId());
            txtNombre.setText(estudiante.getNombre());
            txtEmail.setText(estudiante.getEmail());
            txtTelefono.setText(estudiante.getTelefono());
            txtDireccion.setText(estudiante.getDireccion());
            txtMatricula.setText(estudiante.getMatricula());
            txtUsuario.setText(estudiante.getUsuario());
            txtContrasenia.setText(estudiante.getContrasenia());
            dpFechaNacimiento.setValue(estudiante.getFechaNacimiento());
            dpFechaIngreso.setValue(estudiante.getFechaIngreso());
            if (estudiante.getActivo()) {
                rbActivoSi.setSelected(true);
            } else {
                rbActivoNo.setSelected(true);
            }
        }
    }

    private void agregarEstudiante() {
        Estudiante estudiante = buildEstudiante();
        if (estudiante != null && estudianteController.crearEstudiante(estudiante)) {
            listEstudiantes.add(estudiante);
            limpiarCamposEstudiante();
        }
    }

    private Estudiante buildEstudiante() {
        try {
            LocalDate fechaIngreso = dpFechaIngreso.getValue() != null ? dpFechaIngreso.getValue() : LocalDate.now();
            LocalDate fechaNacimiento = dpFechaNacimiento.getValue() != null ? dpFechaNacimiento.getValue() : LocalDate.now();
            boolean activo = rbActivoSi.isSelected();

            return new Estudiante(
                txtMatricula.getText(),
                fechaIngreso,
                activo,
                txtId.getText(),
                txtNombre.getText(),
                txtEmail.getText(),
                txtTelefono.getText(),
                txtDireccion.getText(),
                fechaNacimiento,
                txtUsuario.getText(),
                txtContrasenia.getText()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void eliminarEstudiante() {
        if (selectedEstudiante != null && estudianteController.eliminarEstudiante(selectedEstudiante.getId())) {
            listEstudiantes.remove(selectedEstudiante);
            limpiarCamposEstudiante();
            limpiarSeleccion();
        }
    }

    private void actualizarEstudiante() {
        if (selectedEstudiante != null && estudianteController.actualizarEstudiante(selectedEstudiante.getId(), buildEstudiante())) {
            int index = listEstudiantes.indexOf(selectedEstudiante);
            if (index >= 0) {
                listEstudiantes.set(index, buildEstudiante());
            }
            tblListEstudiantes.refresh();
            limpiarSeleccion();
            limpiarCamposEstudiante();
        }
    }

    private void limpiarSeleccion() {
        tblListEstudiantes.getSelectionModel().clearSelection();
        limpiarCamposEstudiante();
    }

    private void limpiarCamposEstudiante() {
        txtId.clear();
        txtNombre.clear();
        txtEmail.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtMatricula.clear();
        txtUsuario.clear();
        txtContrasenia.clear();
        dpFechaNacimiento.setValue(null);
        dpFechaIngreso.setValue(null);
        rbActivoSi.setSelected(true);
        rbActivoNo.setSelected(false);
    }
}
