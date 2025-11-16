package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.ProfesorController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProfesorDashboardViewController {
    private App app;
    private Profesor profesor;
    private ProfesorController profesorController;

    @FXML private Label lblBienvenida;
    @FXML private Label lblCodigo;
    @FXML private Label lblEspecialidad;
    @FXML private Label lblNumeroClases;
    @FXML private ListView<String> lstInstrumentos;
    @FXML private ListView<String> lstClasesAsignadas;
    @FXML private Button btnGestionarDisponibilidad;
    @FXML private Button btnRegistrarAsistencia;
    @FXML private Button btnEvaluarEstudiantes;
    @FXML private Button btnCerrarSesion;

    public void setApp(App app, Profesor profesor) {
        this.app = app;
        this.profesor = profesor;
        this.profesorController = new ProfesorController(app.getSistemaAcademia());

        if (profesor != null) {
            cargarInformacion();
        }
    }

    private void cargarInformacion() {
        lblBienvenida.setText("Bienvenido, " + profesor.getNombre());
        lblCodigo.setText("Código: " + profesor.getCodigo());
        lblEspecialidad.setText("Especialidad: " + profesor.getEspecialidad());

        // Cargar instrumentos
        lstInstrumentos.getItems().clear();
        for (TipoInstrumento inst : profesor.getTheInstrumentosImpartidos()) {
            lstInstrumentos.getItems().add("🎵 " + inst.toString());
        }

        // Cargar clases asignadas
        int numeroClases = profesorController.contarClasesProfesor(profesor.getId());
        lblNumeroClases.setText("Clases asignadas: " + numeroClases);

        lstClasesAsignadas.getItems().clear();
        for (Clase clase : profesor.getTheClasesAsignadas()) {
            String tipoClase = clase instanceof co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.ClaseGrupal ?
                    "Grupal" : "Individual";
            lstClasesAsignadas.getItems().add(
                    tipoClase + " - " +
                            clase.getInstrumento() + " Nivel " + clase.getNivel() +
                            " - " + clase.getDiaSemana() + " " + clase.getHoraInicio()
            );
        }
    }

    @FXML
    void onGestionarDisponibilidad() {
        mostrarAlerta("Información",
                "Funcionalidad de gestión de disponibilidad en desarrollo",
                Alert.AlertType.INFORMATION);
    }

    @FXML
    void onRegistrarAsistencia() {
        mostrarAlerta("Información",
                "Funcionalidad de registro de asistencia en desarrollo",
                Alert.AlertType.INFORMATION);
    }

    @FXML
    void onEvaluarEstudiantes() {
        mostrarAlerta("Información",
                "Funcionalidad de evaluación de estudiantes en desarrollo",
                Alert.AlertType.INFORMATION);
    }

    @FXML
    void onCerrarSesion() {
        app.openLoginView();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}