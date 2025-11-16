package co.edu.uniquindio.poo.proyectofinalmusica;

import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Aplicación Principal de la Academia de Música
 * Interfaz Gráfica con JavaFX
 */
public class App extends Application {

    // Sistema principal
    public SistemaAcademia sistema;

    // Stage principal
    private Stage primaryStage;

    // Usuario actual logueado
    private Persona usuarioActual;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        // Inicializar el sistema
        inicializarSistema();
        cargarDatosIniciales();

        // Abrir vista de login
        openLoginView();

        // Configurar stage principal
        primaryStage.setTitle("Academia de Música UQ - Sistema de Gestión");
        primaryStage.show();
    }

    // ==================== INICIALIZACIÓN ====================

    private void inicializarSistema() {
        sistema = new SistemaAcademia("Academia de Música UQ", "NIT-123456789");
        System.out.println("✓ Sistema inicializado correctamente");
    }

    private void cargarDatosIniciales() {
        // Administrador por defecto
        Administrador admin = new Administrador(
                "Director",
                "Administración",
                LocalDate.now().toString(),
                "ADM001",
                "Carlos Rodríguez",
                "admin@academia.com",
                "3001234567",
                "Calle Principal #123",
                LocalDate.of(1975, 5, 15),
                "admin",
                "admin123"
        );
        sistema.agregarAdministrador(admin);

        // Profesor de ejemplo
        Profesor prof1 = new Profesor(
                "PROF001",
                "Piano",
                LocalDate.now().toString(),
                true,
                "P001",
                "María García",
                "maria@academia.com",
                "3009876543",
                "Avenida Sur #45",
                LocalDate.of(1985, 3, 20),
                "maria",
                "maria123"
        );
        prof1.getTheInstrumentosImpartidos().add(TipoInstrumento.PIANO);
        sistema.agregarProfesor(prof1);

        // Estudiante de ejemplo
        Estudiante est1 = new Estudiante(
                "EST2024001",
                LocalDate.now(),
                true,
                "E001",
                "Ana López",
                "ana@email.com",
                "3101111111",
                "Calle 5 #10-15",
                LocalDate.of(2005, 6, 25),
                "ana",
                "ana123"
        );
        sistema.registrarEstudiante(est1);

        // Aulas de ejemplo
        Aula aula1 = new Aula("A001", "A-101", "Aula Principal", "Piso 1", 20, true);
        Aula aula2 = new Aula("A002", "A-102", "Aula Práctica 1", "Piso 1", 10, true);
        sistema.getListAulas().add(aula1);
        sistema.getListAulas().add(aula2);

        // Curso de ejemplo
        Curso curso1 = new Curso(
                "C001",
                "PIANO-1",
                "Piano Básico",
                TipoInstrumento.PIANO,
                1,
                "Introducción al piano",
                15,
                0,
                EstadoCurso.ACTIVO,
                LocalDate.now().toString(),
                LocalDate.now().plusMonths(6).toString(),
                24
        );
        sistema.crearCurso(curso1);

        System.out.println("✓ Datos iniciales cargados");
        System.out.println("  Usuarios de prueba:");
        System.out.println("  - Admin: admin/admin123");
        System.out.println("  - Profesor: maria/maria123");
        System.out.println("  - Estudiante: ana/ana123");
    }

    // ==================== NAVEGACIÓN ENTRE VISTAS ====================

    // Login
    public void openLoginView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/loginView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.LoginViewController) controller).setApp(this));
    }

    // Dashboards
    public void openAdministradorDashboard(Administrador admin) {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/administradorDashboardView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.AdministradorDashboardViewController) controller)
                        .setApp(this, admin != null ? admin : (Administrador) usuarioActual));
    }

    public void openProfesorDashboard(Profesor profesor) {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/profesorDashboardView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.ProfesorDashboardViewController) controller)
                        .setApp(this, profesor != null ? profesor : (Profesor) usuarioActual));
    }

    

    // Gestión - Administrador
    public void openEstudianteView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/estudianteView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.EstudianteViewController) controller).setApp(this));
    }

    public void openProfesorView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/profesorView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.ProfesorViewController) controller).setApp(this));
    }

    public void openCursoView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/cursoView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.CursoViewController) controller).setApp(this));
    }

    public void openClaseView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/claseView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.ClaseViewController) controller).setApp(this));
    }

    public void openAulaView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/aulaView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.AulaViewController) controller).setApp(this));
    }

    public void openReportesView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/reportesView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.ReportesViewController) controller).setApp(this));
    }

    // Método auxiliar genérico para cargar vistas
    private void loadView(String fxmlPath, java.util.function.Consumer<Object> controllerSetup) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller != null && controllerSetup != null) {
                controllerSetup.accept(controller);
            }

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar vista " + fxmlPath + ": " + e.getMessage());
        }
    }

    // Getter para sistema (usado en algunos ViewControllers)
    public SistemaAcademia getSistemaAcademia() {
        return sistema;
    }

    // ==================== GESTIÓN DE USUARIO ACTUAL ====================

    public Persona getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Persona usuario) {
        this.usuarioActual = usuario;
    }

    public void cerrarSesion() {
        usuarioActual = null;
        openLoginView();
    }

    // ==================== MAIN ====================

    public static void main(String[] args) {
        launch(args);
    }
}