module co.edu.uniquindio.poo.proyectofinalmusica {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.poo.proyectofinalmusica to javafx.fxml;
    exports co.edu.uniquindio.poo.proyectofinalmusica;
}