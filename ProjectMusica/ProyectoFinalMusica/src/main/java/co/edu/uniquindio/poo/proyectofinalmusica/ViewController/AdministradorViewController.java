package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.controller.AdministradorController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Administrador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdministradorViewController {
    AdministradorController administradorController;
    ObservableList<Administrador> listAdministradores = FXCollections.observableArrayList();
    Administrador selectedAdministrador;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private TextField txtCargo;

    @FXML
    private TextField txtDepartamento;

    @FXML
    private TextField txtFechaIngreso;

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
    private TableView<Administrador> tblListAdministradores;

    @FXML
    private TableColumn<Administrador, String> tbcId;

    @FXML
    private TableColumn<Administrador, String> tbcNombre;

    @FXML
    private TableColumn<Administrador, String> tbcEmail;

    @FXML
    private TableColumn<Administrador, String> tbcTelefono;

    @FXML
    private TableColumn<Administrador, String> tbcDireccion;

    @FXML
    private TableColumn<Administrador, String> tbcCargo;

    @FXML
    private TableColumn<Administrador, String> tbcDepartamento;

    @FXML
    private TableColumn<Administrador, String> tbcFechaIngreso;

    private App app;

    @FXML
    void onRegresarMenu() {
        if (app != null) {
            app.openAdministradorDashboard(null); //
        } else {
            System.err.println("Error: La aplicación no ha sido inicializada correctamente");
        }
    }


    @FXML
    void onAgregar() {
        agregarAdministrador();
    }

    @FXML
    void onActualizar() {
        actualizarAdministrador();
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void onEliminar() {
        eliminarAdministrador();
    }

    @FXML
    void initialize() {

    }

    public void setApp(App app) {
        this.app = app;
        administradorController = new AdministradorController(app.sistema);
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerAdministradores();
        tblListAdministradores.getItems().clear();
        tblListAdministradores.setItems(listAdministradores);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        tbcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        tbcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        tbcDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        tbcCargo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCargo()));
        tbcDepartamento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartamento()));
        tbcFechaIngreso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaIngreso()));
    }

    private void obtenerAdministradores() {
        listAdministradores.addAll(administradorController.obtenerListaAdministradores());
    }

    private void listenerSelection() {
        tblListAdministradores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedAdministrador = newSelection;
            mostrarInformacionAdministrador(selectedAdministrador);
        });
    }

    private void mostrarInformacionAdministrador(Administrador administrador) {
        if (administrador != null) {
            txtId.setText(administrador.getId());
            txtNombre.setText(administrador.getNombre());
            txtEmail.setText(administrador.getEmail());
            txtTelefono.setText(administrador.getTelefono());
            txtDireccion.setText(administrador.getDireccion());
            dpFechaNacimiento.setValue(administrador.getFechaNacimiento());
            txtCargo.setText(administrador.getCargo());
            txtDepartamento.setText(administrador.getDepartamento());
            txtFechaIngreso.setText(administrador.getFechaIngreso());
            txtUsuario.setText(administrador.getUsuario());
            txtContrasenia.setText(administrador.getContrasenia());
        }
    }

    private void agregarAdministrador() {
        Administrador administrador = buildAdministrador();
        if (administrador != null && administradorController.agregarAdministrador(administrador)) {
            listAdministradores.add(administrador);
            limpiarCamposAdministrador();
        }
    }

    private Administrador buildAdministrador() {
        try {
            LocalDate fechaNacimiento = dpFechaNacimiento.getValue() != null ? dpFechaNacimiento.getValue() : LocalDate.now();
            
            return new Administrador(
                txtCargo.getText(),
                txtDepartamento.getText(),
                txtFechaIngreso.getText(),
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

    private void eliminarAdministrador() {
        if (selectedAdministrador != null && administradorController.eliminarAdministrador(selectedAdministrador.getId())) {
            listAdministradores.remove(selectedAdministrador);
            limpiarCamposAdministrador();
            limpiarSeleccion();
        }
    }

    private void actualizarAdministrador() {
        if (selectedAdministrador != null && administradorController.actualizarAdministrador(selectedAdministrador.getId(), buildAdministrador())) {
            int index = listAdministradores.indexOf(selectedAdministrador);
            if (index >= 0) {
                listAdministradores.set(index, buildAdministrador());
            }
            tblListAdministradores.refresh();
            limpiarSeleccion();
            limpiarCamposAdministrador();
        }
    }

    private void limpiarSeleccion() {
        tblListAdministradores.getSelectionModel().clearSelection();
        limpiarCamposAdministrador();
    }

    private void limpiarCamposAdministrador() {
        txtId.clear();
        txtNombre.clear();
        txtEmail.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        dpFechaNacimiento.setValue(null);
        txtCargo.clear();
        txtDepartamento.clear();
        txtFechaIngreso.clear();
        txtUsuario.clear();
        txtContrasenia.clear();
    }
}
