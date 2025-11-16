package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.EstudianteController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EstudianteDashboardViewController {
    private App app;
    private Estudiante estudiante;
    private EstudianteController estudianteController;

    @FXML private Label lblBienvenida;
    @FXML private Label lblMatricula;
    @FXML private Label lblEmail;
    @FXML private Label lblNumeroInscripciones;
    @FXML private Label lblPromedioGeneral;
    @FXML private ListView<String> lstInscripciones;
    @FXML private ListView<String> lstNivelesAprobados;
    @FXML private Button btnVerHorario;
    @FXML private Button btnVerAsistencia;
    @FXML private Button btnVerEvaluaciones;
    @FXML private Button btnCerrarSesion;

    public void setApp(App app, Estudiante estudiante) {
        this.app = app;
        this.estudiante = estudiante;
        this.estudianteController = new EstudianteController(app.getSistemaAcademia());

        if (estudiante != null) {
            cargarInformacion();
        }
    }

    private void cargarInformacion() {
        lblBienvenida.setText("Bienvenido, " + estudiante.getNombre());
        lblMatricula.setText("Matrícula: " + estudiante.getMatricula());
        lblEmail.setText("Email: " + estudiante.getEmail());

        // Cargar inscripciones activas
        int inscripcionesActivas = 0;
        lstInscripciones.getItems().clear();
        for (Inscripcion insc : estudiante.getTheInscripciones()) {
            if (insc.isActiva()) {
                inscripcionesActivas++;
                lstInscripciones.getItems().add(
                        insc.getTheCurso().getNombre() + " - " +
                                insc.getTheCurso().getInstrumento() + " Nivel " +
                                insc.getTheCurso().getNivel()
                );
            }
        }
        lblNumeroInscripciones.setText("Inscripciones activas: " + inscripcionesActivas);

        // Cargar niveles aprobados
        lstNivelesAprobados.getItems().clear();
        for (NivelAprobado nivel : estudiante.getTheNivelesAprobados()) {
            lstNivelesAprobados.getItems().add(
                    nivel.getInstrumento() + " Nivel " + nivel.getNivel() +
                            " - Calificación: " + String.format("%.2f", nivel.getCalificacion())
            );
        }

        // Calcular promedio
        double promedio = estudianteController.calcularPromedioEstudiante(estudiante.getId());
        lblPromedioGeneral.setText("Promedio General: " + String.format("%.2f", promedio));
    }

    @FXML
    void onVerHorario() {
        mostrarAlerta("Información", "Funcionalidad de horario en desarrollo", Alert.AlertType.INFORMATION);
    }

    @FXML
    void onVerAsistencia() {
        StringBuilder asistencia = new StringBuilder("HISTORIAL DE ASISTENCIA\n\n");

        for (Asistencia a : estudiante.getTheHistorialAsistencia()) {
            asistencia.append("Fecha: ").append(a.getFecha())
                    .append(" - ").append(a.isPresente() ? "Presente" : "Ausente")
                    .append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Historial de Asistencia");
        alert.setHeaderText(null);
        alert.setContentText(asistencia.toString());
        alert.showAndWait();
    }

    @FXML
    void onVerEvaluaciones() {
        StringBuilder evaluaciones = new StringBuilder("HISTORIAL DE EVALUACIONES\n\n");

        for (EvaluacionProgreso eval : estudiante.getTheEvaluaciones()) {
            evaluaciones.append("Calificación: ").append(String.format("%.2f", eval.getCalificacion()))
                    .append("\nComentarios: ").append(eval.getComentarios())
                    .append("\nFecha: ").append(eval.getFecha())
                    .append("\n\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Historial de Evaluaciones");
        alert.setHeaderText(null);
        alert.setContentText(evaluaciones.toString());
        alert.showAndWait();
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
