package co.edu.uniquindio.poo.proyectofinalmusica.viewController;

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


public class EstudianteViewController{
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

    private ToggleGroup toggleGroupActivo;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableView<Estudiante> tblListEstudiantes;

    @FXML
    private TableColumn<Estudiante, String> tbcNombre;

    @FXML
    private TableColumn<Estudiante, String> tbcMatricula;

    @FXML
    private TableColumn<Estudiante, String> tbcFechaNacimiento;

    @FXML
    private TableColumn<Estudiante, String> tbcFechaIngreso;

    @FXML
    private TableColumn<Estudiante, String> tbcActivo;

    @FXML
    private TableColumn<Estudiante, String> tbcId;

    @FXML
    private TableColumn<Estudiante, String> tbcEmail;

    @FXML
    private TableColumn<Estudiante, String> tbcTelefono;

    @FXML
    private TableColumn<Estudiante, String> tbcDireccion;

    @FXML
    private Button btnRegresarMenu;

    @FXML
    void onRegresarMenu() {
        app.openViewPrincipal();
    }

    private App app;


    @FXML
    void onAgregarPropietario() {
        agregarEstudiante();
    }


    @FXML
    void onActualizarPropietario() {
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
    }

    public void setApp(App app) {
        this.app = app;
        estudianteController = new EstudianteController(app.sistemaAcademia);
        initView();
    }


    private void initView() {
        initDataBinding();


        obtenerEstudiante();


        tblListEstudiantes.getItems().clear();


        tblListEstudiantes.setItems(listEstudiantes);


        listenerSelection();
    }

// String matricula, LocalDate fechaIngreso , boolean activo, String  id, String nombre, String email, String telefono, String direccion,  LocalDate fechaNacimiento

    private void initDataBinding() {
        tbcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        tbcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        tbcActivo.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getActivo()));
        tbcMatricula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricula()));
        tbcEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        tbcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        tbcFechaNacimiento.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getFechaNacimiento()));
        tbcFechaIngreso.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getFechaIngreso()));



    }


    private void obtenerEstudiante() {
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
            txtDireccion.setText(estudiante.getDireccion());
            txtEmail.setText(estudiante.getEmail());
            txtNombre.setText(estudiante.getNombre());
            txtMatricula.setText(estudiante.getMatricula());
            txtTelefono.setText(estudiante.getTelefono());
            if (estudiante.getActivo()) {
                rbActivoSi.setSelected(true);
            } else {
                rbActivoNo.setSelected(false);
            }
            dpFechaIngreso.setValue(estudiante.getFechaIngreso());
            dpFechaNacimiento.setValue(estudiante.getFechaNacimiento());
        }
    }


    private void agregarEstudiante() {
        Estudiante estudiante = buildEstudiante();
        if (estudianteController.crearEstudiante(estudiante)) {
            listEstudiantes.add(estudiante);
            limpiarCamposEstudiante();
        }
    }


    private Estudiante buildEstudiante() {

        LocalDate fechaIngreso = LocalDate.now();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        boolean activo = rbActivoSi.isSelected();

        Estudiante estudiante = new Estudiante(
                txtMatricula.getText(),
                fechaIngreso,
                activo,
                txtId.getText(),
                txtNombre.getText(),
                txtEmail.getText(),
                txtTelefono.getText(),
                txtDireccion.getText(),
                fechaNacimiento);
        return estudiante;
    }


    private void eliminarEstudiante() {
        if (estudianteController.eliminarEstudiante(txtId.getText())) {
            listEstudiantes.remove(selectedEstudiante);
            limpiarCamposEstudiante();
            limpiarSeleccion();
        }
    }


    private void actualizarEstudiante() {


        if (selectedEstudiante != null &&
                estudianteController.actualizarEstudiante(selectedEstudiante.getId(), buildEstudiante())) {


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
        txtDireccion.clear();
        txtTelefono.clear();
        txtEmail.clear();
        txtMatricula.clear();
        rbActivoSi.setSelected(false);
        rbActivoNo.setSelected(false);
        dpFechaIngreso.setValue(null);
        dpFechaNacimiento.setValue(null);
    }




    public EstudianteController getEstudianteController() {

        return null;
    }
}
