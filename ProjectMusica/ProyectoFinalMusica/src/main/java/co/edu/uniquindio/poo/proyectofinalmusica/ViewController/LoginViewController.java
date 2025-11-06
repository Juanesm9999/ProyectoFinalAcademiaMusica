package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginViewController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private ComboBox<String> cboTipoUsuario;
    @FXML private Button btnIngresar;
    @FXML private Button btnRegistrarse;

    private SistemaAcademia sistema;

    @FXML
    public void initialize() {
        // Inicializar sistema
        sistema = new SistemaAcademia("Academia UQ Música", "123456789");
        cargarDatosPrueba();
    }

    @FXML
    public void onIngresarClick() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText().trim();
        String tipoUsuario = cboTipoUsuario.getValue();

        // Validaciones
        if (usuario.isEmpty() || contrasena.isEmpty() || tipoUsuario == null) {
            mostrarAlerta("Error", "Por favor complete todos los campos", Alert.AlertType.ERROR);
            return;
        }

        // Intentar autenticación
        Persona personaAutenticada = sistema.iniciarSesion(usuario, contrasena, tipoUsuario);

        if (personaAutenticada != null) {
            mostrarAlerta("Éxito", "¡Bienvenido " + personaAutenticada.getNombre() + "!", Alert.AlertType.INFORMATION);

            // Abrir vista según el rol
            try {
                if (personaAutenticada instanceof Estudiante) {
                    abrirVistaEstudiante((Estudiante) personaAutenticada);
                } else if (personaAutenticada instanceof Profesor) {
                    abrirVistaProfesor((Profesor) personaAutenticada);
                } else if (personaAutenticada instanceof Administrador) {
                    abrirVistaAdministrador((Administrador) personaAutenticada);
                }

                // Cerrar ventana de login
                cerrarVentana();

            } catch (Exception e) {
                mostrarAlerta("Error", "Error al abrir la vista: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onRegistrarseClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegistroView.fxml"));
            Parent root = loader.load();

            RegistroViewController controller = loader.getController();
            controller.setSistema(sistema);

            Stage stage = new Stage();
            stage.setTitle("Registro de Usuario");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            mostrarAlerta("Error", "Error al abrir ventana de registro: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void abrirVistaEstudiante(Estudiante estudiante) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EstudianteView.fxml"));
        Parent root = loader.load();

        // EstudianteViewController controller = loader.getController();
        // controller.setEstudiante(estudiante);
        // controller.setSistema(sistema);

        Stage stage = new Stage();
        stage.setTitle("Vista Estudiante - " + estudiante.getNombre());
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }

    private void abrirVistaProfesor(Profesor profesor) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfesorView.fxml"));
        Parent root = loader.load();

        // ProfesorViewController controller = loader.getController();
        // controller.setProfesor(profesor);
        // controller.setSistema(sistema);

        Stage stage = new Stage();
        stage.setTitle("Vista Profesor - " + profesor.getNombre());
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }

    private void abrirVistaAdministrador(Administrador administrador) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdministradorView.fxml"));
        Parent root = loader.load();

        // AdministradorViewController controller = loader.getController();
        // controller.setAdministrador(administrador);
        // controller.setSistema(sistema);

        Stage stage = new Stage();
        stage.setTitle("Vista Administrador - " + administrador.getNombre());
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Datos de prueba para poder hacer login inmediatamente
    private void cargarDatosPrueba() {
        // Estudiante de prueba
        Estudiante estudiante1 = new Estudiante(
                "EST001",
                java.time.LocalDate.now(),
                true,
                "E001",
                "Victor Rodriguez",
                "victor@email.com",
                "3001234567",
                "Calle 1",
                java.time.LocalDate.of(2000, 1, 15),
                "victor123",
                "password123"
        );
        sistema.registrarEstudiante(estudiante1);

        // Profesor de prueba
        Profesor profesor1 = new Profesor(
                "PROF001",
                "Música Clásica",
                "2020-01-01",
                true,
                "P001",
                "Carlos López",
                "carlos@email.com",
                "3009876543",
                "Calle 3",
                java.time.LocalDate.of(1985, 3, 10),
                "carlos123",
                "prof123"
        );
        sistema.agregarProfesor(profesor1);

        // Administrador de prueba
        Administrador admin1 = new Administrador(
                "Director",
                "Administración",
                "2019-01-01",
                "A001",
                "Ana Martínez",
                "ana@email.com",
                "3001112233",
                "Calle 5",
                java.time.LocalDate.of(1980, 5, 20),
                "admin",
                "admin123"
        );
        sistema.getListAdministradores().add(admin1);

        System.out.println("=== DATOS DE PRUEBA CARGADOS ===");
        System.out.println("Estudiante: victor123 / password123");
        System.out.println("Profesor: carlos123 / prof123");
        System.out.println("Administrador: admin / admin123");
    }
}