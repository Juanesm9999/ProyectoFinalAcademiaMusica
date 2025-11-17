module co.edu.uniquindio.poo.proyectofinalmusica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens co.edu.uniquindio.poo.proyectofinalmusica to javafx.fxml;
    exports co.edu.uniquindio.poo.proyectofinalmusica;


    // ViewController con V mayúscula (correcto)
    exports co.edu.uniquindio.poo.proyectofinalmusica.ViewController;
    opens co.edu.uniquindio.poo.proyectofinalmusica.ViewController to javafx.fxml;


    exports co.edu.uniquindio.poo.proyectofinalmusica.Controller;
    opens co.edu.uniquindio.poo.proyectofinalmusica.Controller to javafx.fxml;
    // Paquetes del modelo
    exports co.edu.uniquindio.poo.proyectofinalmusica.model;
    opens co.edu.uniquindio.poo.proyectofinalmusica.model to javafx.fxml;

    exports co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;
    opens co.edu.uniquindio.poo.proyectofinalmusica.model.gestion to javafx.fxml;
}