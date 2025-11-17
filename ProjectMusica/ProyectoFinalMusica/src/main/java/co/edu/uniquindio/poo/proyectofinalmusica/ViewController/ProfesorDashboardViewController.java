package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.ProfesorController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class ProfesorDashboardViewController {
    private App app;
    private Profesor profesor;
    private ProfesorController profesorController;

    @FXML private Label lblBienvenida;
    @FXML private Label lblCodigo;
    @FXML private Label lblEspecialidad;
    @FXML private Label lblNumeroClases;
    @FXML private ListView<String> lstInstrumentos;
    @FXML private ListView<String> lstClasesAsignadas;
    @FXML private Button btnGestionarDisponibilidad;
    @FXML private Button btnRegistrarAsistencia;
    @FXML private Button btnEvaluarEstudiantes;
    @FXML private Button btnCerrarSesion;

    public void setApp(App app, Profesor profesor) {
        this.app = app;
        this.profesor = profesor;
        this.profesorController = new ProfesorController(app.getSistemaAcademia());

        if (profesor != null) {
            cargarInformacion();
        }
    }

    private void cargarInformacion() {
        lblBienvenida.setText("Bienvenido, " + profesor.getNombre());
        lblCodigo.setText("Código: " + profesor.getCodigo());
        lblEspecialidad.setText("Especialidad: " + profesor.getEspecialidad());

        // Cargar instrumentos
        lstInstrumentos.getItems().clear();
        for (TipoInstrumento inst : profesor.getTheInstrumentosImpartidos()) {
            lstInstrumentos.getItems().add("🎵 " + inst.toString());
        }

        // Cargar clases asignadas
        int numeroClases = profesorController.contarClasesProfesor(profesor.getId());
        lblNumeroClases.setText("Clases asignadas: " + numeroClases);

        lstClasesAsignadas.getItems().clear();
        for (Clase clase : profesor.getTheClasesAsignadas()) {
            String tipoClase = clase instanceof co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.ClaseGrupal ?
                    "Grupal" : "Individual";
            lstClasesAsignadas.getItems().add(
                    tipoClase + " - " +
                            clase.getInstrumento() + " Nivel " + clase.getNivel() +
                            " - " + clase.getDiaSemana() + " " + clase.getHoraInicio()
            );
        }
    }

    @FXML
    void onGestionarDisponibilidad() {
        if (profesor == null) {
            mostrarAlerta("Error", "No hay información del profesor", Alert.AlertType.ERROR);
            return;
        }

        Stage stageDisponibilidad = new Stage();
        stageDisponibilidad.setTitle("Gestionar Disponibilidad");
        stageDisponibilidad.initModality(Modality.APPLICATION_MODAL);

        // Obtener bloques de disponibilidad
        List<BloqueDisponibilidad> bloques = profesor.getTheDisponibilidad();
        ObservableList<BloqueDisponibilidad> listaBloques = FXCollections.observableArrayList(bloques);

        // Tabla de disponibilidad
        TableView<BloqueDisponibilidad> tablaDisponibilidad = new TableView<>();
        tablaDisponibilidad.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<BloqueDisponibilidad, String> colDia = new TableColumn<>("Día");
        colDia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDiaSemana()));
        colDia.setPrefWidth(120);

        TableColumn<BloqueDisponibilidad, String> colHoraInicio = new TableColumn<>("Hora Inicio");
        colHoraInicio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoraInicio()));
        colHoraInicio.setPrefWidth(100);

        TableColumn<BloqueDisponibilidad, String> colHoraFin = new TableColumn<>("Hora Fin");
        colHoraFin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoraFin()));
        colHoraFin.setPrefWidth(100);

        TableColumn<BloqueDisponibilidad, String> colDisponible = new TableColumn<>("Disponible");
        colDisponible.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().isDisponible() ? "Sí" : "No"));
        colDisponible.setPrefWidth(100);

        tablaDisponibilidad.getColumns().addAll(colDia, colHoraInicio, colHoraFin, colDisponible);
        tablaDisponibilidad.setItems(listaBloques);

        // Formulario para agregar bloque
        ComboBox<String> cmbDia = new ComboBox<>(FXCollections.observableArrayList(
                "LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO"));
        TextField txtHoraInicio = new TextField();
        txtHoraInicio.setPromptText("08:00");
        TextField txtHoraFin = new TextField();
        txtHoraFin.setPromptText("10:00");
        CheckBox chkDisponible = new CheckBox("Disponible");
        chkDisponible.setSelected(true);

        // Layout
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label titulo = new Label("📅 Gestión de Disponibilidad");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label info = new Label("Profesor: " + profesor.getNombre());
        info.setStyle("-fx-font-size: 13px; -fx-text-fill: #666;");

        Separator separator = new Separator();

        // Formulario
        VBox formulario = new VBox(10);
        formulario.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 15; -fx-background-radius: 8;");
        Label lblFormulario = new Label("Agregar Nuevo Bloque de Disponibilidad");
        lblFormulario.setStyle("-fx-font-weight: bold;");

        HBox fila1 = new HBox(10);
        fila1.getChildren().addAll(
                new Label("Día:"), cmbDia,
                new Label("Hora Inicio:"), txtHoraInicio,
                new Label("Hora Fin:"), txtHoraFin,
                chkDisponible
        );
        fila1.setAlignment(Pos.CENTER_LEFT);

        Button btnAgregarBloque = new Button("Agregar Bloque");
        btnAgregarBloque.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnAgregarBloque.setOnAction(e -> {
            if (cmbDia.getValue() == null || txtHoraInicio.getText().isEmpty() || txtHoraFin.getText().isEmpty()) {
                mostrarAlerta("Error", "Complete todos los campos", Alert.AlertType.ERROR);
                return;
            }

            String idBloque = "BD-" + profesor.getId() + "-" + System.currentTimeMillis();
            BloqueDisponibilidad nuevoBloque = new BloqueDisponibilidad(
                    idBloque,
                    cmbDia.getValue(),
                    txtHoraInicio.getText(),
                    txtHoraFin.getText(),
                    chkDisponible.isSelected(),
                    profesor
            );

            profesor.getTheDisponibilidad().add(nuevoBloque);
            listaBloques.add(nuevoBloque);
            tablaDisponibilidad.refresh();

            cmbDia.setValue(null);
            txtHoraInicio.clear();
            txtHoraFin.clear();
            chkDisponible.setSelected(true);

            mostrarAlerta("Éxito", "Bloque de disponibilidad agregado", Alert.AlertType.INFORMATION);
        });

        Button btnEliminarBloque = new Button("Eliminar Seleccionado");
        btnEliminarBloque.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEliminarBloque.setOnAction(e -> {
            BloqueDisponibilidad seleccionado = tablaDisponibilidad.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                profesor.getTheDisponibilidad().remove(seleccionado);
                listaBloques.remove(seleccionado);
                mostrarAlerta("Éxito", "Bloque eliminado", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Advertencia", "Seleccione un bloque para eliminar", Alert.AlertType.WARNING);
            }
        });

        HBox botonesForm = new HBox(10);
        botonesForm.getChildren().addAll(btnAgregarBloque, btnEliminarBloque);

        formulario.getChildren().addAll(lblFormulario, fila1, botonesForm);

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-padding: 10 25;");
        btnCerrar.setOnAction(e -> stageDisponibilidad.close());

        HBox botonera = new HBox();
        botonera.setAlignment(Pos.CENTER_RIGHT);
        botonera.getChildren().add(btnCerrar);

        vbox.getChildren().addAll(titulo, info, separator, tablaDisponibilidad, formulario, botonera);
        VBox.setVgrow(tablaDisponibilidad, javafx.scene.layout.Priority.ALWAYS);

        Scene scene = new Scene(vbox, 700, 500);
        stageDisponibilidad.setScene(scene);
        stageDisponibilidad.show();
    }

    @FXML
    void onRegistrarAsistencia() {
        if (profesor == null) {
            mostrarAlerta("Error", "No hay información del profesor", Alert.AlertType.ERROR);
            return;
        }

        List<Clase> clasesActivas = profesorController.obtenerClasesActivasProfesor(profesor.getId());
        if (clasesActivas == null || clasesActivas.isEmpty()) {
            mostrarAlerta("Información", "No tienes clases asignadas para registrar asistencia", Alert.AlertType.INFORMATION);
            return;
        }

        Stage stageAsistencia = new Stage();
        stageAsistencia.setTitle("Registrar Asistencia");
        stageAsistencia.initModality(Modality.APPLICATION_MODAL);

        // Seleccionar clase
        ComboBox<Clase> cmbClase = new ComboBox<>(FXCollections.observableArrayList(clasesActivas));
        cmbClase.setCellFactory(param -> new ListCell<Clase>() {
            @Override
            protected void updateItem(Clase item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String tipo = item instanceof ClaseGrupal ? "Grupal" : "Individual";
                    setText(item.getInstrumento() + " Nivel " + item.getNivel() + " - " + tipo + 
                            " (" + item.getDiaSemana() + " " + item.getHoraInicio() + ")");
                }
            }
        });

        // Tabla de estudiantes
        TableView<Estudiante> tablaEstudiantes = new TableView<>();
        tablaEstudiantes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Estudiante, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colNombre.setPrefWidth(200);

        TableColumn<Estudiante, String> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricula()));
        colMatricula.setPrefWidth(120);

        TableColumn<Estudiante, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colEmail.setPrefWidth(200);

        tablaEstudiantes.getColumns().addAll(colNombre, colMatricula, colEmail);

        // Actualizar estudiantes cuando se selecciona una clase
        cmbClase.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList();
                if (newVal instanceof ClaseGrupal) {
                    estudiantes.addAll(((ClaseGrupal) newVal).getTheEstudiantesInscritos());
                } else if (newVal instanceof ClaseIndividual) {
                    Estudiante est = ((ClaseIndividual) newVal).getTheEstudiante();
                    if (est != null) {
                        estudiantes.add(est);
                    }
                }
                tablaEstudiantes.setItems(estudiantes);
            }
        });

        // Checkbox para presente/ausente
        CheckBox chkPresente = new CheckBox("Presente");
        chkPresente.setSelected(true);
        TextArea txtObservaciones = new TextArea();
        txtObservaciones.setPromptText("Observaciones (opcional)");
        txtObservaciones.setPrefRowCount(3);

        // Layout
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label titulo = new Label("✅ Registrar Asistencia");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox seleccionClase = new HBox(10);
        seleccionClase.getChildren().addAll(new Label("Seleccionar Clase:"), cmbClase);
        seleccionClase.setAlignment(Pos.CENTER_LEFT);

        Label lblEstudiantes = new Label("Estudiantes de la Clase:");
        lblEstudiantes.setStyle("-fx-font-weight: bold;");

        VBox formularioAsistencia = new VBox(10);
        formularioAsistencia.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 15; -fx-background-radius: 8;");
        formularioAsistencia.getChildren().addAll(
                new Label("Estado de Asistencia:"),
                chkPresente,
                new Label("Observaciones:"),
                txtObservaciones
        );

        Button btnRegistrar = new Button("Registrar Asistencia");
        btnRegistrar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25;");
        btnRegistrar.setOnAction(e -> {
            Clase claseSeleccionada = cmbClase.getValue();
            Estudiante estudianteSeleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();

            if (claseSeleccionada == null) {
                mostrarAlerta("Error", "Seleccione una clase", Alert.AlertType.ERROR);
                return;
            }

            if (estudianteSeleccionado == null) {
                mostrarAlerta("Error", "Seleccione un estudiante", Alert.AlertType.ERROR);
                return;
            }

            try {
                // Obtener el tamaño del historial antes de registrar
                int tamanioAntes = estudianteSeleccionado.getTheHistorialAsistencia().size();
                
                // Registrar asistencia usando el método de la clase
                claseSeleccionada.registrarAsistencia(estudianteSeleccionado, chkPresente.isSelected());
                
                // Obtener la asistencia recién creada
                List<Asistencia> historial = estudianteSeleccionado.getTheHistorialAsistencia();
                if (historial.size() > tamanioAntes) {
                    Asistencia nuevaAsistencia = historial.get(historial.size() - 1);
                    
                    // Agregar observaciones si hay
                    if (!txtObservaciones.getText().isEmpty()) {
                        nuevaAsistencia.setObservaciones(txtObservaciones.getText());
                    }

                    // Registrar en el sistema
                    boolean exito = app.getSistemaAcademia().registrarAsistencia(nuevaAsistencia);
                    
                    if (exito) {
                        mostrarAlerta("Éxito", "Asistencia registrada correctamente", Alert.AlertType.INFORMATION);
                        txtObservaciones.clear();
                        chkPresente.setSelected(true);
                    } else {
                        mostrarAlerta("Error", "No se pudo registrar la asistencia. Puede que ya exista una asistencia con el mismo ID para esta fecha.", Alert.AlertType.ERROR);
                    }
                } else {
                    mostrarAlerta("Error", "No se pudo crear la asistencia. Verifique que el estudiante esté inscrito en la clase.", Alert.AlertType.ERROR);
                }
            } catch (Exception ex) {
                mostrarAlerta("Error", "Error al registrar asistencia: " + ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-padding: 10 25;");
        btnCerrar.setOnAction(e -> stageAsistencia.close());

        HBox botonera = new HBox(10);
        botonera.setAlignment(Pos.CENTER_RIGHT);
        botonera.getChildren().addAll(btnRegistrar, btnCerrar);

        vbox.getChildren().addAll(titulo, seleccionClase, lblEstudiantes, tablaEstudiantes, 
                formularioAsistencia, botonera);
        VBox.setVgrow(tablaEstudiantes, javafx.scene.layout.Priority.ALWAYS);

        Scene scene = new Scene(vbox, 700, 600);
        stageAsistencia.setScene(scene);
        stageAsistencia.show();
    }

    @FXML
    void onEvaluarEstudiantes() {
        if (profesor == null) {
            mostrarAlerta("Error", "No hay información del profesor", Alert.AlertType.ERROR);
            return;
        }

        List<Clase> clasesActivas = profesorController.obtenerClasesActivasProfesor(profesor.getId());
        if (clasesActivas == null || clasesActivas.isEmpty()) {
            mostrarAlerta("Información", "No tienes clases asignadas para evaluar", Alert.AlertType.INFORMATION);
            return;
        }

        Stage stageEvaluacion = new Stage();
        stageEvaluacion.setTitle("Evaluar Estudiantes");
        stageEvaluacion.initModality(Modality.APPLICATION_MODAL);

        // Seleccionar clase
        ComboBox<Clase> cmbClase = new ComboBox<>(FXCollections.observableArrayList(clasesActivas));
        cmbClase.setCellFactory(param -> new ListCell<Clase>() {
            @Override
            protected void updateItem(Clase item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String tipo = item instanceof ClaseGrupal ? "Grupal" : "Individual";
                    setText(item.getInstrumento() + " Nivel " + item.getNivel() + " - " + tipo);
                }
            }
        });

        // Tabla de estudiantes
        TableView<Estudiante> tablaEstudiantes = new TableView<>();
        tablaEstudiantes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Estudiante, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colNombre.setPrefWidth(200);

        TableColumn<Estudiante, String> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricula()));
        colMatricula.setPrefWidth(120);

        tablaEstudiantes.getColumns().addAll(colNombre, colMatricula);

        // Actualizar estudiantes cuando se selecciona una clase
        cmbClase.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList();
                if (newVal instanceof ClaseGrupal) {
                    estudiantes.addAll(((ClaseGrupal) newVal).getTheEstudiantesInscritos());
                } else if (newVal instanceof ClaseIndividual) {
                    Estudiante est = ((ClaseIndividual) newVal).getTheEstudiante();
                    if (est != null) {
                        estudiantes.add(est);
                    }
                }
                tablaEstudiantes.setItems(estudiantes);
            }
        });

        // Formulario de evaluación
        Spinner<Double> spnCalificacion = new Spinner<>(0.0, 5.0, 3.0, 0.1);
        spnCalificacion.setEditable(true);
        TextArea txtComentarios = new TextArea();
        txtComentarios.setPromptText("Comentarios sobre el progreso del estudiante");
        txtComentarios.setPrefRowCount(3);
        TextArea txtAreasMejorar = new TextArea();
        txtAreasMejorar.setPromptText("Áreas a mejorar");
        txtAreasMejorar.setPrefRowCount(2);

        // Layout
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label titulo = new Label("📝 Evaluar Estudiantes");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox seleccionClase = new HBox(10);
        seleccionClase.getChildren().addAll(new Label("Seleccionar Clase:"), cmbClase);
        seleccionClase.setAlignment(Pos.CENTER_LEFT);

        Label lblEstudiantes = new Label("Estudiantes de la Clase:");
        lblEstudiantes.setStyle("-fx-font-weight: bold;");

        VBox formularioEvaluacion = new VBox(10);
        formularioEvaluacion.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 15; -fx-background-radius: 8;");
        formularioEvaluacion.getChildren().addAll(
                new Label("Calificación (0.0 - 5.0):"),
                spnCalificacion,
                new Label("Comentarios:"),
                txtComentarios,
                new Label("Áreas a Mejorar:"),
                txtAreasMejorar
        );

        Button btnEvaluar = new Button("Registrar Evaluación");
        btnEvaluar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25;");
        btnEvaluar.setOnAction(e -> {
            Clase claseSeleccionada = cmbClase.getValue();
            Estudiante estudianteSeleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();

            if (claseSeleccionada == null) {
                mostrarAlerta("Error", "Seleccione una clase", Alert.AlertType.ERROR);
                return;
            }

            if (estudianteSeleccionado == null) {
                mostrarAlerta("Error", "Seleccione un estudiante", Alert.AlertType.ERROR);
                return;
            }

            if (txtComentarios.getText().isEmpty()) {
                mostrarAlerta("Error", "Ingrese comentarios sobre el progreso", Alert.AlertType.ERROR);
                return;
            }

            try {
                // Obtener el tamaño de las evaluaciones antes de evaluar
                int tamanioAntes = estudianteSeleccionado.getTheEvaluaciones().size();
                
                // Evaluar usando el método de la clase
                claseSeleccionada.evaluarProgreso(estudianteSeleccionado, 
                        spnCalificacion.getValue(), txtComentarios.getText());

                // Obtener la evaluación recién creada
                List<EvaluacionProgreso> evaluaciones = estudianteSeleccionado.getTheEvaluaciones();
                if (evaluaciones.size() > tamanioAntes) {
                    EvaluacionProgreso nuevaEvaluacion = evaluaciones.get(evaluaciones.size() - 1);
                    
                    // Agregar áreas a mejorar si hay
                    if (!txtAreasMejorar.getText().isEmpty()) {
                        nuevaEvaluacion.setAreasAMejorar(txtAreasMejorar.getText());
                    }
                    
                    // Asignar el curso si es una clase grupal
                    if (claseSeleccionada instanceof ClaseGrupal) {
                        // Buscar el curso asociado a esta clase
                        for (Curso curso : app.getSistemaAcademia().getListCursos()) {
                            if (curso.getTheClases().contains(claseSeleccionada)) {
                                nuevaEvaluacion.setTheCurso(curso);
                                break;
                            }
                        }
                    }

                    // Registrar en el sistema
                    boolean exito = app.getSistemaAcademia().registrarEvaluacion(nuevaEvaluacion);
                    
                    if (exito) {
                        mostrarAlerta("Éxito", "Evaluación registrada correctamente", Alert.AlertType.INFORMATION);
                        txtComentarios.clear();
                        txtAreasMejorar.clear();
                        spnCalificacion.getValueFactory().setValue(3.0);
                    } else {
                        mostrarAlerta("Error", "No se pudo registrar la evaluación. Puede que ya exista una evaluación con el mismo ID para esta fecha.", Alert.AlertType.ERROR);
                    }
                } else {
                    mostrarAlerta("Error", "No se pudo crear la evaluación. Verifique que el estudiante esté inscrito en la clase.", Alert.AlertType.ERROR);
                }
            } catch (Exception ex) {
                mostrarAlerta("Error", "Error al registrar evaluación: " + ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-padding: 10 25;");
        btnCerrar.setOnAction(e -> stageEvaluacion.close());

        HBox botonera = new HBox(10);
        botonera.setAlignment(Pos.CENTER_RIGHT);
        botonera.getChildren().addAll(btnEvaluar, btnCerrar);

        vbox.getChildren().addAll(titulo, seleccionClase, lblEstudiantes, tablaEstudiantes, 
                formularioEvaluacion, botonera);
        VBox.setVgrow(tablaEstudiantes, javafx.scene.layout.Priority.ALWAYS);

        Scene scene = new Scene(vbox, 700, 650);
        stageEvaluacion.setScene(scene);
        stageEvaluacion.show();
    }

    @FXML
    void onCerrarSesion() {
        app.openLoginView();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}