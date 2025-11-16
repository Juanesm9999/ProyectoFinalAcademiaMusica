package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.AulaController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Aula;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AulaViewController {
    private App app;
    private AulaController aulaController;
    private ObservableList<Aula> listAulas = FXCollections.observableArrayList();
    private Aula selectedAula;

    @FXML private TextField txtId;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtUbicacion;
    @FXML private TextField txtCapacidad;
    @FXML private CheckBox chkDisponible;

    @FXML private TableView<Aula> tblAulas;
    @FXML private TableColumn<Aula, String> tbcCodigo;
    @FXML private TableColumn<Aula, String> tbcNombre;
    @FXML private TableColumn<Aula, String> tbcUbicacion;
    @FXML private TableColumn<Aula, String> tbcCapacidad;
    @FXML private TableColumn<Aula, String> tbcEstado;

    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;

    @FXML
    void initialize() {
        chkDisponible.setSelected(true);
    }

    public void setApp(App app) {
        this.app = app;
        aulaController = new AulaController(app.getSistemaAcademia());
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerAulas();
        tblAulas.setItems(listAulas);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCodigo()));
        tbcNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcUbicacion.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUbicacion()));
        tbcCapacidad.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getCapacidad())));
        tbcEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isDisponible() ? "Disponible" : "No disponible"));
    }

    private void obtenerAulas() {
        listAulas.clear();
        listAulas.addAll(aulaController.obtenerListaAulas());
    }

    private void listenerSelection() {
        tblAulas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedAula = newSelection;
            mostrarInformacion(selectedAula);
        });
    }

    private void mostrarInformacion(Aula aula) {
        if (aula != null) {
            txtId.setText(aula.getId());
            txtCodigo.setText(aula.getCodigo());
            txtNombre.setText(aula.getNombre());
            txtUbicacion.setText(aula.getUbicacion());
            txtCapacidad.setText(String.valueOf(aula.getCapacidad()));
            chkDisponible.setSelected(aula.isDisponible());
        }
    }

    @FXML
    void onAgregar() {
        if (validarCampos()) {
            Aula aula = buildAula();
            if (aulaController.agregarAula(aula)) {
                listAulas.add(aula);
                limpiarCampos();
                mostrarAlerta("Éxito", "Aula agregada correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "El aula ya existe", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizar() {
        if (selectedAula != null && validarCampos()) {
            Aula actualizada = buildAula();
            if (aulaController.actualizarAula(selectedAula.getId(), actualizada)) {
                int index = listAulas.indexOf(selectedAula);
                if (index >= 0) {
                    listAulas.set(index, actualizada);
                }
                tblAulas.refresh();
                limpiarSeleccion();
                mostrarAlerta("Éxito", "Aula actualizada correctamente", Alert.AlertType.INFORMATION);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un aula de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminar() {
        if (selectedAula != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar esta aula?");
            confirmacion.setContentText(selectedAula.getNombre());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (aulaController.eliminarAula(selectedAula.getId())) {
                    listAulas.remove(selectedAula);
                    limpiarSeleccion();
                    mostrarAlerta("Éxito", "Aula eliminada correctamente", Alert.AlertType.INFORMATION);
                }
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un aula de la tabla", Alert.AlertType.WARNING);
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

    private Aula buildAula() {
        return new Aula(
                txtId.getText(),
                txtCodigo.getText(),
                txtNombre.getText(),
                txtUbicacion.getText(),
                Integer.parseInt(txtCapacidad.getText()),
                chkDisponible.isSelected()
        );
    }

    private boolean validarCampos() {
        if (txtId.getText().isEmpty() || txtCodigo.getText().isEmpty() ||
                txtNombre.getText().isEmpty() || txtUbicacion.getText().isEmpty() ||
                txtCapacidad.getText().isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }

        try {
            Integer.parseInt(txtCapacidad.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La capacidad debe ser un número válido", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void limpiarSeleccion() {
        tblAulas.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.clear();
        txtCodigo.clear();
        txtNombre.clear();
        txtUbicacion.clear();
        txtCapacidad.clear();
        chkDisponible.setSelected(true);
        selectedAula = null;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}