module co.edu.uniquindio.poo.proyectofinalmusica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens co.edu.uniquindio.poo.proyectofinalmusica to javafx.fxml;
    exports co.edu.uniquindio.poo.proyectofinalmusica;


    exports co.edu.uniquindio.poo.proyectofinalmusica.viewController;
    opens co.edu.uniquindio.poo.proyectofinalmusica.viewController to javafx.fxml;


    exports co.edu.uniquindio.poo.proyectofinalmusica.controller;
    opens co.edu.uniquindio.poo.proyectofinalmusica.controller to javafx.fxml;
}