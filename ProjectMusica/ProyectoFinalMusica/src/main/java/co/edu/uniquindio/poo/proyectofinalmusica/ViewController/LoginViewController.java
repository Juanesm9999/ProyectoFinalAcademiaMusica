package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.controller.LoginController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginViewController {
    private App app;
    private LoginController loginController;

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private ComboBox<String> cmbTipoUsuario;
    @FXML private Button btnIngresar;
    @FXML private Label lblMensaje;

    @FXML
    void initialize() {
        cmbTipoUsuario.getItems().addAll("ESTUDIANTE", "PROFESOR", "ADMINISTRADOR");
    }

    public void setApp(App app) {
        this.app = app;
        loginController = new LoginController(app.getSistemaAcademia());
    }

    @FXML
    void onIngresar() {
        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();
        String tipoUsuario = cmbTipoUsuario.getValue();

        if (validarCampos()) {
            Persona persona = loginController.iniciarSesion(usuario, contrasena, tipoUsuario);

            if (persona != null) {
                lblMensaje.setText("¡Bienvenido!");
                lblMensaje.setStyle("-fx-text-fill: green;");

                // Redirigir según el tipo de usuario
                if (persona instanceof Estudiante) {
                    app.openEstudianteDashboard((Estudiante) persona);
                } else if (persona instanceof Profesor) {
                    app.openProfesorDashboard((Profesor) persona);
                } else if (persona instanceof Administrador) {
                    app.openAdministradorDashboard((Administrador) persona);
                }
            } else {
                mostrarAlerta("Error", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
                lblMensaje.setText("Credenciales incorrectas");
                lblMensaje.setStyle("-fx-text-fill: red;");
            }
        }
    }

    private boolean validarCampos() {
        if (txtUsuario.getText().isEmpty() || txtContrasena.getText().isEmpty() ||
                cmbTipoUsuario.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}