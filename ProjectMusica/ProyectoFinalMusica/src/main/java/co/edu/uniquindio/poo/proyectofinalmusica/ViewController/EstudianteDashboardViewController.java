package co.edu.uniquindio.poo.proyectofinalmusica.ViewController;

import co.edu.uniquindio.poo.proyectofinalmusica.App;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.CursoController;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.EstudianteController;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.InscripcionController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Clase;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;
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

public class EstudianteDashboardViewController {
    private App app;
    private Estudiante estudiante;
    private EstudianteController estudianteController;

    @FXML private Label lblBienvenida;
    @FXML private Label lblMatricula;
    @FXML private Label lblEmail;
    @FXML private Label lblNumeroInscripciones;
    @FXML private Label lblPromedioGeneral;
    @FXML private ListView<String> lstInscripciones;
    @FXML private ListView<String> lstNivelesAprobados;
    @FXML private Button btnInscribirseCurso;
    @FXML private Button btnVerHorario;
    @FXML private Button btnVerAsistencia;
    @FXML private Button btnVerEvaluaciones;
    @FXML private Button btnCerrarSesion;

    private CursoController cursoController;
    private InscripcionController inscripcionController;

    public void setApp(App app, Estudiante estudiante) {
        this.app = app;
        this.estudiante = estudiante;
        this.estudianteController = new EstudianteController(app.getSistemaAcademia());
        this.cursoController = new CursoController(app.getSistemaAcademia());
        this.inscripcionController = new InscripcionController(app.getSistemaAcademia());

        if (estudiante != null) {
            cargarInformacion();
        }
    }
    @FXML
    void onInscribirseCurso() {
        if (estudiante == null) {
            mostrarAlerta("Error", "No hay información del estudiante", Alert.AlertType.ERROR);
            return;
        }

        // Crear ventana modal para mostrar cursos disponibles
        Stage stageInscripcion = new Stage();
        stageInscripcion.setTitle("Inscribirse a un Curso");
        stageInscripcion.initModality(Modality.APPLICATION_MODAL);

        // Obtener cursos disponibles (activos y con cupos)
        List<Curso> cursosDisponibles = cursoController.obtenerCursosConCuposDisponibles();

        // Filtrar cursos en los que el estudiante NO esté ya inscrito
        List<Curso> cursosFiltrados = cursosDisponibles.stream()
                .filter(curso -> !inscripcionController.verificarInscripcionExistente(estudiante.getId(), curso.getId()))
                .collect(java.util.stream.Collectors.toList());

        // Crear tabla de cursos
        TableView<Curso> tablaCursos = new TableView<>();
        tablaCursos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Columnas de la tabla
        TableColumn<Curso, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        colCodigo.setPrefWidth(100);

        TableColumn<Curso, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colNombre.setPrefWidth(200);

        TableColumn<Curso, String> colInstrumento = new TableColumn<>("Instrumento");
        colInstrumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInstrumento().toString()));
        colInstrumento.setPrefWidth(120);

        TableColumn<Curso, String> colNivel = new TableColumn<>("Nivel");
        colNivel.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNivel())));
        colNivel.setPrefWidth(80);

        TableColumn<Curso, String> colCapacidad = new TableColumn<>("Cupos");
        colCapacidad.setCellValueFactory(cellData -> {
            Curso curso = cellData.getValue();
            int cuposDisponibles = curso.getCapacidadMaxima() - curso.getCapacidadActual();
            return new SimpleStringProperty(cuposDisponibles + " / " + curso.getCapacidadMaxima());
        });
        colCapacidad.setPrefWidth(100);

        TableColumn<Curso, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        colDescripcion.setPrefWidth(250);

        tablaCursos.getColumns().addAll(colCodigo, colNombre, colInstrumento, colNivel, colCapacidad, colDescripcion);
        tablaCursos.setItems(FXCollections.observableArrayList(cursosFiltrados));

        // Layout
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label titulo = new Label("📚 Cursos Disponibles");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label info = new Label("Selecciona un curso para inscribirte");
        info.setStyle("-fx-font-size: 13px; -fx-text-fill: #666;");

        Separator separator = new Separator();

        // Botones
        Button btnInscribir = new Button("Inscribirse");
        btnInscribir.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-cursor: hand;");
        btnInscribir.setOnAction(e -> {
            Curso cursoSeleccionado = tablaCursos.getSelectionModel().getSelectedItem();
            if (cursoSeleccionado == null) {
                mostrarAlerta("Advertencia", "Por favor selecciona un curso", Alert.AlertType.WARNING);
                return;
            }

            // Verificar nuevamente que no esté inscrito
            if (inscripcionController.verificarInscripcionExistente(estudiante.getId(), cursoSeleccionado.getId())) {
                mostrarAlerta("Error", "Ya estás inscrito en este curso", Alert.AlertType.ERROR);
                return;
            }

            // Intentar inscribir
            boolean exito = inscripcionController.inscribirEstudiante(estudiante, cursoSeleccionado);
            if (exito) {
                mostrarAlerta("Éxito", "Te has inscrito exitosamente en: " + cursoSeleccionado.getNombre(), Alert.AlertType.INFORMATION);
                cargarInformacion(); // Actualizar información del dashboard
                stageInscripcion.close();
            } else {
                mostrarAlerta("Error", "No se pudo realizar la inscripción. Verifica que cumplas con los prerrequisitos y que haya cupos disponibles.", Alert.AlertType.ERROR);
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> stageInscripcion.close());

        HBox botonera = new HBox(15);
        botonera.setAlignment(Pos.CENTER_RIGHT);
        botonera.getChildren().addAll(btnInscribir, btnCancelar);

        if (cursosFiltrados.isEmpty()) {
            Label lblVacio = new Label("📋 No hay cursos disponibles para inscribirte en este momento.\n\n" +
                    "Todos los cursos activos están llenos o ya estás inscrito en ellos.");
            lblVacio.setStyle("-fx-font-size: 15px; -fx-text-alignment: center; -fx-text-fill: #666;");
            lblVacio.setWrapText(true);

            VBox contenidoVacio = new VBox(20);
            contenidoVacio.setAlignment(Pos.CENTER);
            contenidoVacio.setPrefHeight(300);
            contenidoVacio.getChildren().addAll(lblVacio, btnCancelar);

            vbox.getChildren().addAll(titulo, info, separator, contenidoVacio);
        } else {
            vbox.getChildren().addAll(titulo, info, separator, tablaCursos, botonera);
            VBox.setVgrow(tablaCursos, javafx.scene.layout.Priority.ALWAYS);
        }

        Scene scene = new Scene(vbox, 900, 600);
        stageInscripcion.setScene(scene);
        stageInscripcion.show();
    }

    @FXML
    void onCerrarSesion() {
        app.openLoginView();
    }

    private void cargarInformacion() {
        lblBienvenida.setText("Bienvenido, " + estudiante.getNombre());
        lblMatricula.setText("Matrícula: " + estudiante.getMatricula());
        lblEmail.setText("Email: " + estudiante.getEmail());

        // Cargar inscripciones activas
        int inscripcionesActivas = 0;
        lstInscripciones.getItems().clear();
        for (Inscripcion insc : estudiante.getTheInscripciones()) {
            if (insc.isActiva()) {
                inscripcionesActivas++;
                lstInscripciones.getItems().add(
                        insc.getTheCurso().getNombre() + " - " +
                                insc.getTheCurso().getInstrumento() + " Nivel " +
                                insc.getTheCurso().getNivel()
                );
            }
        }
        lblNumeroInscripciones.setText("Inscripciones activas: " + inscripcionesActivas);

        // Cargar niveles aprobados
        lstNivelesAprobados.getItems().clear();
        for (NivelAprobado nivel : estudiante.getTheNivelesAprobados()) {
            lstNivelesAprobados.getItems().add(
                    nivel.getInstrumento() + " Nivel " + nivel.getNivel() +
                            " - Calificación: " + String.format("%.2f", nivel.getCalificacion())
            );
        }

        // Calcular promedio
        double promedio = estudianteController.calcularPromedioEstudiante(estudiante.getId());
        lblPromedioGeneral.setText("Promedio General: " + String.format("%.2f", promedio));
    }

    // Reemplaza el método onVerHorario() en EstudianteDashboardViewController.java

    @FXML
    void onVerHorario() {
        if (estudiante == null) {
            mostrarAlerta("Error", "No hay información del estudiante", Alert.AlertType.ERROR);
            return;
        }

        // Crear nueva ventana
        Stage stageHorario = new Stage();
        stageHorario.setTitle("Mi Horario de Clases");
        stageHorario.initModality(Modality.APPLICATION_MODAL);

        // Crear tabla
        TableView<Horario> tableHorario = new TableView<>();
        tableHorario.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Definir columnas
        TableColumn<Horario, String> colDia = new TableColumn<>("Día");
        colDia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDiaSemana()));
        colDia.setPrefWidth(100);

        TableColumn<Horario, String> colHorario = new TableColumn<>("Horario");
        colHorario.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getHoraInicio() + " - " + cellData.getValue().getHoraFin()
        ));
        colHorario.setPrefWidth(120);

        TableColumn<Horario, String> colCurso = new TableColumn<>("Curso");
        colCurso.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getCurso() != null ? cellData.getValue().getCurso().getNombre() : "N/A"
        ));
        colCurso.setPrefWidth(200);

        TableColumn<Horario, String> colInstrumento = new TableColumn<>("Instrumento");
        colInstrumento.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getTipoInstrumento() != null ? cellData.getValue().getTipoInstrumento().toString() : "N/A"
        ));
        colInstrumento.setPrefWidth(120);

        TableColumn<Horario, String> colProfesor = new TableColumn<>("Profesor");
        colProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getProfesor() != null ? cellData.getValue().getProfesor().getNombre() : "Sin asignar"
        ));
        colProfesor.setPrefWidth(150);

        TableColumn<Horario, String> colAula = new TableColumn<>("Aula");
        colAula.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getTheAula() != null ? cellData.getValue().getTheAula().getCodigo() : "Sin asignar"
        ));
        colAula.setPrefWidth(100);

        // Agregar columnas a la tabla
        tableHorario.getColumns().addAll(colDia, colHorario, colCurso, colInstrumento, colProfesor, colAula);

        // Llenar datos - Crear objetos Horario desde las clases del estudiante
        ObservableList<Horario> horarios = FXCollections.observableArrayList();

        for (Inscripcion inscripcion : estudiante.getTheInscripciones()) {
            if (inscripcion.isActiva()) {
                Curso curso = inscripcion.getTheCurso();

                for (Clase clase : curso.getTheClases()) {
                    if (clase.isActiva()) {
                        // Crear objeto Horario con los datos de la clase
                        Horario horario = new Horario(
                                "H-" + clase.getId(),
                                clase.getDiaSemana(),
                                clase.getHoraInicio(),
                                clase.getHoraFin(),
                                clase.getTheAula()
                        );

                        // Configurar propiedades adicionales usando los setters
                        horario.setTheClase(clase);  // Relación 1 a 1
                        horario.setClase(clase);
                        horario.setProfesor(clase.getTheProfesor());
                        horario.setCurso(curso);
                        horario.setTipoInstrumento(clase.getInstrumento());

                        horarios.add(horario);
                    }
                }
            }
        }

        tableHorario.setItems(horarios);

        // Layout de la ventana
        VBox vbox = new VBox(15);
        vbox.setPadding(new javafx.geometry.Insets(20));

        // Título
        Label titulo = new Label("📅 Mi Horario de Clases");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Información del estudiante
        Label info = new Label("Estudiante: " + estudiante.getNombre() + " | Matrícula: " + estudiante.getMatricula());
        info.setStyle("-fx-font-size: 13px; -fx-text-fill: #666;");

        // Separador
        Separator separator = new Separator();

        // Si no hay datos, mostrar mensaje
        if (horarios.isEmpty()) {
            Label lblVacio = new Label("📚 No tienes clases programadas\n\nInscríbete en un curso para ver tu horario.");
            lblVacio.setStyle("-fx-font-size: 15px; -fx-text-alignment: center; -fx-text-fill: #666;");
            lblVacio.setWrapText(true);

            VBox contenidoVacio = new VBox(20);
            contenidoVacio.setAlignment(javafx.geometry.Pos.CENTER);
            contenidoVacio.setPrefHeight(300);
            contenidoVacio.getChildren().add(lblVacio);

            vbox.getChildren().addAll(titulo, info, separator, contenidoVacio);
        } else {
            // Botón cerrar
            Button btnCerrar = new Button("Cerrar");
            btnCerrar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-padding: 8 20; -fx-cursor: hand;");
            btnCerrar.setOnAction(e -> stageHorario.close());

            HBox botonera = new HBox();
            botonera.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
            botonera.getChildren().add(btnCerrar);

            vbox.getChildren().addAll(titulo, info, separator, tableHorario, botonera);
            VBox.setVgrow(tableHorario, javafx.scene.layout.Priority.ALWAYS);
        }

        // Crear escena y mostrar
        Scene scene = new Scene(vbox, 920, 600);
        stageHorario.setScene(scene);
        stageHorario.show();
    }

    @FXML
    void onVerAsistencia() {
        if (estudiante == null) {
            mostrarAlerta("Error", "No hay información del estudiante", Alert.AlertType.ERROR);
            return;
        }

        StringBuilder asistencia = new StringBuilder();
        asistencia.append("═══════════════════════════════════════\n");
        asistencia.append("    HISTORIAL DE ASISTENCIA\n");
        asistencia.append("═══════════════════════════════════════\n\n");
        asistencia.append("Estudiante: ").append(estudiante.getNombre()).append("\n");
        asistencia.append("Matrícula: ").append(estudiante.getMatricula()).append("\n\n");

        List<Asistencia> historial =
                estudiante.getTheHistorialAsistencia();

        if (historial == null || historial.isEmpty()) {
            asistencia.append("📋 No hay registros de asistencia disponibles.\n\n");
            asistencia.append("Las asistencias se registrarán cuando:\n");
            asistencia.append("• Estés inscrito en un curso\n");
            asistencia.append("• Los profesores registren tu asistencia a clases\n");
        } else {
            int totalPresentes = 0;
            int totalAusentes = 0;

            asistencia.append("REGISTRO DE ASISTENCIAS:\n");
            asistencia.append("───────────────────────────────────────\n\n");

            for (co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Asistencia a : historial) {
                // Icono según presente/ausente
                String icono = a.isPresente() ? "✅" : "❌";
                String estado = a.isPresente() ? "PRESENTE" : "AUSENTE";

                asistencia.append(icono).append(" ").append(estado).append("\n");
                asistencia.append("   📅 Fecha: ").append(a.getFecha()).append("\n");

                if (a.getTheClase() != null) {
                    asistencia.append("   📚 Clase: ").append(a.getTheClase().getInstrumento())
                            .append(" - Nivel ").append(a.getTheClase().getNivel()).append("\n");
                }

                if (a.getObservaciones() != null && !a.getObservaciones().isEmpty()) {
                    asistencia.append("   📝 Observaciones: ").append(a.getObservaciones()).append("\n");
                }

                asistencia.append("\n");

                if (a.isPresente()) {
                    totalPresentes++;
                } else {
                    totalAusentes++;
                }
            }

            // Estadísticas
            asistencia.append("═══════════════════════════════════════\n");
            asistencia.append("ESTADÍSTICAS:\n");
            asistencia.append("───────────────────────────────────────\n");
            asistencia.append("Total de registros: ").append(historial.size()).append("\n");
            asistencia.append("✅ Presentes: ").append(totalPresentes).append("\n");
            asistencia.append("❌ Ausentes: ").append(totalAusentes).append("\n");

            if (historial.size() > 0) {
                double porcentaje = (totalPresentes * 100.0) / historial.size();
                asistencia.append("📊 Porcentaje de asistencia: ")
                        .append(String.format("%.1f%%", porcentaje)).append("\n");
            }
        }

        // Mostrar en ventana
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Historial de Asistencia");
        alert.setHeaderText(null);

        TextArea textArea = new TextArea(asistencia.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(550, 450);
        textArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");

        alert.getDialogPane().setContent(textArea);
        alert.getDialogPane().setPrefSize(600, 500);
        alert.showAndWait();
    }
    @FXML
    void onVerEvaluaciones() {
        if (estudiante == null) {
            mostrarAlerta("Error", "No hay información del estudiante", Alert.AlertType.ERROR);
            return;
        }

        StringBuilder evaluaciones = new StringBuilder();
        evaluaciones.append("═══════════════════════════════════════\n");
        evaluaciones.append("    HISTORIAL DE EVALUACIONES\n");
        evaluaciones.append("═══════════════════════════════════════\n\n");
        evaluaciones.append("Estudiante: ").append(estudiante.getNombre()).append("\n");
        evaluaciones.append("Matrícula: ").append(estudiante.getMatricula()).append("\n\n");

        List<co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.EvaluacionProgreso> historialEval =
                estudiante.getTheEvaluaciones();

        if (historialEval == null || historialEval.isEmpty()) {
            evaluaciones.append("📋 No hay evaluaciones registradas.\n\n");
            evaluaciones.append("Las evaluaciones se mostrarán cuando:\n");
            evaluaciones.append("• Estés inscrito en un curso\n");
            evaluaciones.append("• Los profesores evalúen tu progreso\n");
            evaluaciones.append("• Completes actividades del curso\n");
        } else {
            double sumaCalificaciones = 0.0;
            int contadorAprobadas = 0;

            evaluaciones.append("EVALUACIONES REALIZADAS:\n");
            evaluaciones.append("───────────────────────────────────────\n\n");

            for (co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.EvaluacionProgreso eval : historialEval) {
                // Determinar estado de la calificación
                String estado;
                String icono;
                if (eval.getCalificacion() >= 3.0) {
                    estado = "APROBADO";
                    icono = "✅";
                    contadorAprobadas++;
                } else {
                    estado = "REPROBADO";
                    icono = "❌";
                }

                evaluaciones.append(icono).append(" ").append(estado).append("\n");
                evaluaciones.append("   📊 Calificación: ").append(String.format("%.2f", eval.getCalificacion()))
                        .append(" / 5.0\n");
                evaluaciones.append("   📅 Fecha: ").append(eval.getFecha()).append("\n");

                if (eval.getTheCurso() != null) {
                    evaluaciones.append("   📚 Curso: ").append(eval.getTheCurso().getNombre()).append("\n");
                }

                if (eval.getTheClase() != null) {
                    evaluaciones.append("   🎵 Instrumento: ").append(eval.getTheClase().getInstrumento())
                            .append(" - Nivel ").append(eval.getTheClase().getNivel()).append("\n");
                }

                if (eval.getTheEvaluador() != null) {
                    evaluaciones.append("   👨‍🏫 Profesor: ").append(eval.getTheEvaluador().getNombre()).append("\n");
                }

                if (eval.getComentarios() != null && !eval.getComentarios().isEmpty()) {
                    evaluaciones.append("   💬 Comentarios: ").append(eval.getComentarios()).append("\n");
                }

                if (eval.getAreasAMejorar() != null && !eval.getAreasAMejorar().isEmpty()) {
                    evaluaciones.append("   📝 Áreas a mejorar: ").append(eval.getAreasAMejorar()).append("\n");
                }

                evaluaciones.append("\n");
                sumaCalificaciones += eval.getCalificacion();
            }

            // Estadísticas
            evaluaciones.append("═══════════════════════════════════════\n");
            evaluaciones.append("ESTADÍSTICAS:\n");
            evaluaciones.append("───────────────────────────────────────\n");
            evaluaciones.append("Total de evaluaciones: ").append(historialEval.size()).append("\n");
            evaluaciones.append("✅ Aprobadas: ").append(contadorAprobadas).append("\n");
            evaluaciones.append("❌ Reprobadas: ").append(historialEval.size() - contadorAprobadas).append("\n");

            double promedio = sumaCalificaciones / historialEval.size();
            evaluaciones.append("📊 Promedio general: ").append(String.format("%.2f", promedio)).append("\n");

            // Clasificación del promedio
            String clasificacion;
            if (promedio >= 4.5) {
                clasificacion = "🏆 EXCELENTE";
            } else if (promedio >= 4.0) {
                clasificacion = "⭐ SOBRESALIENTE";
            } else if (promedio >= 3.5) {
                clasificacion = "👍 BUENO";
            } else if (promedio >= 3.0) {
                clasificacion = "✔️ ACEPTABLE";
            } else {
                clasificacion = "⚠️ NECESITA MEJORAR";
            }
            evaluaciones.append("📈 Clasificación: ").append(clasificacion).append("\n");
        }

        // Mostrar en ventana
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Historial de Evaluaciones");
        alert.setHeaderText(null);

        TextArea textArea = new TextArea(evaluaciones.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(550, 500);
        textArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");

        alert.getDialogPane().setContent(textArea);
        alert.getDialogPane().setPrefSize(600, 550);
        alert.showAndWait();
    }
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
