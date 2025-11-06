package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.veterinariajfx.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


import java.net.URL;
import java.util.ResourceBundle;


public class PrimaryViewController {
    @FXML
    private ResourceBundle resources;


    App app;
    @FXML
    private URL location;


    @FXML
    private Button primaryButton;

    @FXML
    void onOpenCrudPerro() {
        app.openCrudPerro();
    }

    @FXML
    void onOpenCrudConsulta() {
        app.openCrudConsulta();
    }

    @FXML
    void onOpenCrudGato() {
        app.openCrudGato();
    }

    @FXML
    void onOpenCrudReptil() {
        app.openCrudReptil();
    }

    @FXML
    void onOpenCrudPropietario() {
        app.openCrudPropietario();
    }

    @FXML
    void onOpenCrudAve() {
        app.openCrudAve();
    }

    @FXML
    void onRankingPropietarios() {
        app.openViewRankingPropietarios();
    }


    public void setApp(App app) {
        this.app = app;
    }


    @FXML
    void initialize() {


    }
}


