package co.edu.uniquindio.poo.proyectofinalmusica;


import co.edu.uniquindio.poo.proyectofinalmusica.model.Administrador;


import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;
import co.edu.uniquindio.poo.proyectofinalmusica.model.SistemaAcademia;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Asistencia;
import co.edu.uniquindio.poo.proyectofinalmusica.viewController.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class App extends Application {


    private Stage primaryStage;
    public static SistemaAcademia sistemaAcademia = new SistemaAcademia("Academia UQ", "123");


    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Academia UQ");
        openViewPrincipal();
    }

    public void openCrudAdministrador() {
        inicializarData();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("administradorView.fxml"));
            AnchorPane rootLayout = loader.load();

            AdministradorViewController administradorViewController = loader.getController();
            administradorViewController.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openViewPrincipal() {
        inicializarData();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("primary.fxml"));
            javafx.scene.layout.VBox rootLayout = (javafx.scene.layout.VBox) loader.load();
            PrimaryViewController primaryController = loader.getController();
            primaryController.setApp(this);


            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public void inicializarData(){

        LocalDate date = LocalDate.now();
        LocalDate date1 = LocalDate.of(2008, 4, 9) ;
        Estudiante estudiante = new Estudiante("s", date, true,"123", "Juan", "juane.mejiat@uqvirtual.edu.co","3137013718", "Kra 25#33-28", date1);
        SistemaAcademia.registrarEstudiante(estudiante);


    }

}


