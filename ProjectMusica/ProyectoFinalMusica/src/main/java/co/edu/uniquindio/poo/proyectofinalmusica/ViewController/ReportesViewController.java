package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.AdministradorController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.TipoInstrumento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ReportesViewController {
    private App app;
    private AdministradorController administradorController;

    @FXML private ComboBox<String> cmbTipoReporte;
    @FXML private TextField txtCursoId;
    @FXML private TextField txtEstudianteId;
    @FXML private ComboBox<TipoInstrumento> cmbInstrumento;
    @FXML private Spinner<Integer> spnNivel;
    @FXML private TextArea txtReporte;
    @FXML private Button btnGenerar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;

    @FXML
    void initialize() {
        cmbTipoReporte.setItems(FXCollections.observableArrayList(
                "Asistencia por Curso",
                "Progreso por Instrumento/Nivel",
                "Ocupación de Aulas",
                "Carga Docente",
                "Reporte de Estudiante"
        ));

        cmbInstrumento.setItems(FXCollections.observableArrayList(TipoInstrumento.values()));

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        spnNivel.setValueFactory(valueFactory);

        cmbTipoReporte.valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarCamposSegunReporte(newVal);
        });
    }

    public void setApp(App app) {
        this.app = app;
        administradorController = new AdministradorController(app.getSistemaAcademia());
    }

    private void actualizarCamposSegunReporte(String tipoReporte) {
        txtCursoId.setDisable(true);
        txtEstudianteId.setDisable(true);
        cmbInstrumento.setDisable(true);
        spnNivel.setDisable(true);

        if ("Asistencia por Curso".equals(tipoReporte)) {
            txtCursoId.setDisable(false);
        } else if ("Progreso por Instrumento/Nivel".equals(tipoReporte)) {
            cmbInstrumento.setDisable(false);
            spnNivel.setDisable(false);
        } else if ("Reporte de Estudiante".equals(tipoReporte)) {
            txtEstudianteId.setDisable(false);
        }
    }

    @FXML
    void onGenerar() {
        String tipoReporte = cmbTipoReporte.getValue();

        if (tipoReporte == null) {
            mostrarAlerta("Advertencia", "Seleccione un tipo de reporte", Alert.AlertType.WARNING);
            return;
        }

        String reporte = "";

        switch (tipoReporte) {
            case "Asistencia por Curso":
                if (txtCursoId.getText().isEmpty()) {
                    mostrarAlerta("Error", "Ingrese el ID del curso", Alert.AlertType.ERROR);
                    return;
                }
                reporte = administradorController.generarReporteAsistenciaPorCurso(txtCursoId.getText());
                break;

            case "Progreso por Instrumento/Nivel":
                if (cmbInstrumento.getValue() == null) {
                    mostrarAlerta("Error", "Seleccione un instrumento", Alert.AlertType.ERROR);
                    return;
                }
                reporte = administradorController.generarReporteProgresoPorInstrumento(
                        cmbInstrumento.getValue(),
                        spnNivel.getValue()
                );
                break;

            case "Ocupación de Aulas":
                reporte = administradorController.generarReporteOcupacionAulas();
                break;

            case "Carga Docente":
                reporte = administradorController.generarReporteCargaDocente();
                break;

            case "Reporte de Estudiante":
                if (txtEstudianteId.getText().isEmpty()) {
                    mostrarAlerta("Error", "Ingrese el ID del estudiante", Alert.AlertType.ERROR);
                    return;
                }
                reporte = administradorController.generarReporteEstudiante(txtEstudianteId.getText());
                break;
        }

        txtReporte.setText(reporte);
    }

    @FXML
    void onLimpiar() {
        txtCursoId.clear();
        txtEstudianteId.clear();
        cmbInstrumento.setValue(null);
        spnNivel.getValueFactory().setValue(1);
        cmbTipoReporte.setValue(null);
        txtReporte.clear();
    }

    @FXML
    void onRegresar() {
        app.openAdministradorDashboard(null);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
