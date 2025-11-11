package co.edu.uniquindio.poo.proyectofinalmusica;

import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.ViewController.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private Stage primaryStage;
    private SistemaAcademia sistemaAcademia;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.sistemaAcademia = new SistemaAcademia("Academia UQ Música", "900123456-7");

        // Inicializar datos de prueba
        inicializarDatosPrueba();

        primaryStage.setTitle("Academia UQ Música - Sistema de Gestión");
        primaryStage.setMaximized(true);
        openLoginView();
    }

    private void inicializarDatosPrueba() {
        // Crear administrador de prueba
        Administrador admin = new Administrador(
                "Director Académico",
                "Administración",
                "2024-01-01",
                "ADM001",
                "Carlos Rodríguez",
                "carlos@academia.com",
                "3001234567",
                "Calle 15 #10-20",
                java.time.LocalDate.of(1980, 5, 15),
                "admin",
                "admin123"
        );
        sistemaAcademia.agregarAdministrador(admin);

        // Crear estudiante de prueba
        Estudiante est = new Estudiante(
                "EST2024001",
                java.time.LocalDate.now(),
                true,
                "EST001",
                "María García",
                "maria@email.com",
                "3109876543",
                "Carrera 10 #5-30",
                java.time.LocalDate.of(2005, 3, 10),
                "maria",
                "maria123"
        );
        sistemaAcademia.registrarEstudiante(est);

        // Crear profesor de prueba
        Profesor prof = new Profesor(
                "PROF001",
                "Piano Clásico",
                "2023-01-15",
                true,
                "PROF001",
                "Juan Pérez",
                "juan@academia.com",
                "3157654321",
                "Avenida 7 #8-15",
                java.time.LocalDate.of(1985, 8, 20),
                "juan",
                "juan123"
        );
        prof.getTheInstrumentosImpartidos().add(TipoInstrumento.PIANO);
        sistemaAcademia.agregarProfesor(prof);
    }

    // ============= VISTAS DE AUTENTICACIÓN =============
    public void openLoginView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/LoginView.fxml",
                controller -> ((LoginViewController) controller).setApp(this),
                800, 600);
    }

    // ============= DASHBOARDS =============
    public void openAdministradorDashboard(Administrador administrador) {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/AdministradorDashboardView.fxml",
                controller -> ((AdministradorDashboardViewController) controller).setApp(this, administrador),
                1000, 700);
    }

    public void openEstudianteDashboard(Estudiante estudiante) {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/EstudianteDashboardView.fxml",
                controller -> ((EstudianteDashboardViewController) controller).setApp(this, estudiante),
                1000, 700);
    }

    public void openProfesorDashboard(Profesor profesor) {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/ProfesorDashboardView.fxml",
                controller -> ((ProfesorDashboardViewController) controller).setApp(this, profesor),
                1000, 700);
    }

    // ============= VISTAS DE GESTIÓN =============
    public void openEstudianteView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/EstudianteView.fxml",
                controller -> ((EstudianteViewController) controller).setApp(this),
                1200, 800);
    }

    public void openProfesorView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/ProfesorView.fxml",
                controller -> ((ProfesorViewController) controller).setApp(this),
                1200, 800);
    }

    public void openCursoView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/CursoView.fxml",
                controller -> ((CursoViewController) controller).setApp(this),
                1200, 800);
    }

    public void openClaseView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/ClaseView.fxml",
                controller -> ((ClaseViewController) controller).setApp(this),
                1200, 800);
    }

    public void openAulaView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/AulaView.fxml",
                controller -> ((AulaViewController) controller).setApp(this),
                1000, 700);
    }

    public void openReportesView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/ReportesView.fxml",
                controller -> ((ReportesViewController) controller).setApp(this),
                1200, 800);
    }

    // ============= MÉTODO AUXILIAR PARA CARGAR VISTAS =============
    private void loadView(String fxmlPath, java.util.function.Consumer<Object> controllerSetup,
                          int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controllerSetup != null) {
                controllerSetup.accept(controller);
            }

            Scene scene = new Scene(root, width, height);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + fxmlPath);
        }
    }

    // ============= GETTERS =============
    public SistemaAcademia getSistemaAcademia() {
        return sistemaAcademia;
    }

    public static void main(String[] args) {
        launch(args);
    }
}