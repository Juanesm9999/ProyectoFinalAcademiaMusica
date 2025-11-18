package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class RegistroViewController {

    @FXML private ComboBox<String> cboTipoUsuario;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasenia;
    @FXML private PasswordField txtConfirmarContrasena;
    @FXML private Button btnRegistrar;
    @FXML private Button btnCancelar;

    private SistemaAcademia sistema;

    @FXML
    public void initialize() {
        cboTipoUsuario.setItems(FXCollections.observableArrayList(
                "ESTUDIANTE", "PROFESOR"
        ));
    }

    public void setSistema(SistemaAcademia sistema) {
        this.sistema = sistema;
    }

    @FXML
    public void onRegistrarClick() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        String tipoUsuario = cboTipoUsuario.getValue();
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        String usuario = txtUsuario.getText().trim();
        String contrasenia = txtContrasenia.getText().trim();


        if (sistema.verificarUsuarioExiste(usuario)) {
            mostrarAlerta("Error", "El nombre de usuario ya está en uso", Alert.AlertType.ERROR);
            return;
        }


        boolean registrado = false;

        try {
            switch (tipoUsuario) {
                case "ESTUDIANTE":
                    Estudiante nuevoEstudiante = new Estudiante(
                            "EST-" + System.currentTimeMillis(),
                            LocalDate.now(),
                            true,
                            "E-" + System.currentTimeMillis(),
                            nombre,
                            email,
                            telefono,
                            direccion,
                            fechaNacimiento,
                            usuario,
                            contrasenia
                    );
                    registrado = sistema.registrarEstudiante(nuevoEstudiante);
                    break;

                case "PROFESOR":
                    Profesor nuevoProfesor = new Profesor(
                            "PROF-" + System.currentTimeMillis(),
                            "Por definir",
                            LocalDate.now().toString(),
                            true,
                            "P-" + System.currentTimeMillis(),
                            nombre,
                            email,
                            telefono,
                            direccion,
                            fechaNacimiento,
                            usuario,
                            contrasenia
                    );
                    registrado = sistema.agregarProfesor(nuevoProfesor);
                    break;
            }

            if (registrado) {
                mostrarAlerta("Éxito",
                        "¡Registro exitoso!\n\nUsuario: " + usuario + "\nYa puede iniciar sesión.",
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo registrar el usuario", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al registrar: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void onCancelarClick() {
        cerrarVentana();
    }

    private boolean validarCampos() {
        // Validar campos vacíos
        if (cboTipoUsuario.getValue() == null || txtNombre.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() || txtUsuario.getText().trim().isEmpty() ||
                txtContrasenia.getText().trim().isEmpty() || dpFechaNacimiento.getValue() == null) {

            mostrarAlerta("Error", "Por favor complete todos los campos obligatorios", Alert.AlertType.ERROR);
            return false;
        }

        // Validar contraseña
        if (txtContrasenia.getText().length() < 6) {
            mostrarAlerta("Error", "La contraseña debe tener al menos 6 caracteres", Alert.AlertType.ERROR);
            return false;
        }

        // Validar que las contraseñas coincidan
        if (!txtContrasenia.getText().equals(txtConfirmarContrasena.getText())) {
            mostrarAlerta("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
            return false;
        }

        // Validar email
        if (!txtEmail.getText().contains("@")) {
            mostrarAlerta("Error", "El email no es válido", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}