package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Administrador;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AdministradorDashboardViewController {
    private App app;
    private Administrador administrador;

    @FXML private Label lblBienvenida;
    @FXML private Button btnGestionEstudiantes;
    @FXML private Button btnGestionProfesores;
    @FXML private Button btnGestionCursos;
    @FXML private Button btnGestionClases;
    @FXML private Button btnGestionAulas;
    @FXML private Button btnReportes;
    @FXML private Button btnCerrarSesion;

    public void setApp(App app, Administrador administrador) {
        this.app = app;
        this.administrador = administrador;

        if (administrador != null) {
            lblBienvenida.setText("Bienvenido, " + administrador.getNombre());
        }
    }

    @FXML
    void onGestionEstudiantes() {
        app.openEstudianteView();
    }

    @FXML
    void onGestionProfesores() {
        app.openProfesorView();
    }

    @FXML
    void onGestionCursos() {
        app.openCursoView();
    }

    @FXML
    void onGestionClases() {
        app.openClaseView();
    }

    @FXML
    void onGestionAulas() {
        app.openAulaView();
    }

    @FXML
    void onReportes() {
        app.openReportesView();
    }

    @FXML
    void onCerrarSesion() {
        app.openLoginView();
    }
}