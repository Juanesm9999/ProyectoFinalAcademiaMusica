package co.edu.uniquindio.poo.proyectofinalmusica.viewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.controller.AdministradorController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Administrador;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


import java.net.URL;
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
    private TextField txtNombre;

    @FXML
    private TextField txtDireccion;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableView<Propietario> tblListPropietarios;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnActualizarPropietario;

    @FXML
    private TableColumn<Propietario, String> tbcNombre;

    @FXML
    private TableColumn<Propietario, String> tbcDireccion;

    @FXML
    private TableColumn<Propietario, String> tbcPuntajeFidelidad;

    @FXML
    private TextField txtPuntajeFidelidad;

    @FXML
    private Button btbAgregarPropietario;

    @FXML
    private TableColumn<Propietario, String> tbcId;

    @FXML
    private TextField txtId;

    @FXML
    private Button btnRegresarMenu;

    @FXML
    void onRegresarMenu() {
        app.openViewPrincipal();
    }

    private App app;


    @FXML
    void onAgregarPropietario() {
        agregarPropietario();
    }


    @FXML
    void onActualizarPropietario() {
        actualizarPropietario();
    }


    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }


    @FXML
    void onEliminar() {
        eliminarPropietario();
    }


    @FXML
    void initialize() {
    }

    public void setApp(App app) {
        this.app = app;
        administradorController = new AdministradorController(app.sistemaAcademia);
        initView();
    }


    private void initView() {
        initDataBinding();


        obtenerPropietario();


        tblListPropietarios.getItems().clear();


        tblListPropietarios.setItems(listPropietarios);


        listenerSelection();
    }


    private void initDataBinding() {
        tbcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        tbcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        tbcPuntajeFidelidad.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getPuntajeFidelidad()));


    }


    private void obtenerPropietario() {
        listPropietarios.addAll(propietarioController.obtenerListaPropietarios());
    }


    private void listenerSelection() {
        tblListPropietarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedPropietario = newSelection;
            mostrarInformacionPropietario(selectedPropietario);
        });
    }


    private void mostrarInformacionPropietario(Propietario propietario) {
        if (propietario != null) {
            txtId.setText(propietario.getId());
            txtNombre.setText(propietario.getNombre());
            txtDireccion.setText(propietario.getDireccion());
            txtPuntajeFidelidad.setText(String.valueOf(propietario.getPuntajeFidelidad()));
        }
    }


    private void agregarPropietario() {
        Propietario propietario = buildPropietario();
        if (propietarioController.crearPropietario(propietario)) {
            listPropietarios.add(propietario);
            limpiarCamposPropietario();
        }
    }


    private Propietario buildPropietario() {

        Propietario propietario = new Propietario(
                txtNombre.getText(),
                txtId.getText(),
                txtDireccion.getText(),
                Double.parseDouble(txtPuntajeFidelidad.getText())
        );
        return propietario;
    }


    private void eliminarPropietario() {
        if (propietarioController.eliminarPropietario(txtId.getText())) {
            listPropietarios.remove(selectedPropietario);
            limpiarCamposPropietario();
            limpiarSeleccion();
        }
    }


    private void actualizarPropietario() {


        if (selectedPropietario != null &&
                propietarioController.actualizarPropietario(selectedPropietario.getId(), buildPropietario())) {


            int index = listPropietarios.indexOf(selectedPropietario);
            if (index >= 0) {
                listPropietarios.set(index, buildPropietario());
            }


            tblListPropietarios.refresh();
            limpiarSeleccion();
            limpiarCamposPropietario();
        }
    }


    private void limpiarSeleccion() {
        tblListPropietarios.getSelectionModel().clearSelection();
        limpiarCamposPropietario();
    }


    private void limpiarCamposPropietario() {
        txtId.clear();
        txtNombre.clear();
        txtDireccion.clear();
        txtPuntajeFidelidad.clear();
    }




    public PropietarioController getPropietarioController() {

        return null;
    }
}
