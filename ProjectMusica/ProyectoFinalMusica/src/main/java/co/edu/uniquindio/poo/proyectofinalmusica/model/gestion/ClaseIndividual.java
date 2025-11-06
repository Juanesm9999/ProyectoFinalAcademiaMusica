package co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;

import co.edu.uniquindio.poo.proyectofinalmusica.model.Clase;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;
import co.edu.uniquindio.poo.proyectofinalmusica.model.TipoInstrumento;
import java.time.LocalDate;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Asistencia;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.EvaluacionProgreso;

public class ClaseIndividual extends Clase {
    private Estudiante theEstudiante; // 1 a 1
    private String temaEspecifico;
    private String objetivos;
    private String observaciones;

    public ClaseIndividual(String temaEspecifico, String objetivos, String observaciones,String id, String horario,String diaSemana, String horaInicio,String HoraFin,TipoInstrumento instrumento, int nivel, boolean activa){
        super(id,horario,diaSemana,horaInicio,HoraFin,instrumento,nivel,activa);
        this.temaEspecifico = temaEspecifico;
        this.objetivos = objetivos;
        this.observaciones = observaciones;
    }

    public Estudiante getTheEstudiante() {
        return theEstudiante;
    }

    public void setTheEstudiante(Estudiante theEstudiante) {
        this.theEstudiante = theEstudiante;
    }

    public String getTemaEspecifico() {
        return temaEspecifico;
    }

    public void setTemaEspecifico(String temaEspecifico) {
        this.temaEspecifico = temaEspecifico;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public void registrarAsistencia(Estudiante estudiante, boolean presente) {
        // Verificar que el estudiante sea el asignado a esta clase individual
        if (theEstudiante != null && theEstudiante.equals(estudiante)) {
            LocalDate fechaActual = LocalDate.now();
            
            // Generar ID único para la asistencia
            String idAsistencia = "AST-" + this.id + "-" + estudiante.getId() + "-" + 
                                  fechaActual.toString().replace("-", "");
            
            // Crear nueva asistencia
            Asistencia asistencia = new Asistencia(
                idAsistencia,
                estudiante,
                this,
                fechaActual,
                presente,
                presente ? "Presente" : "Ausente"
            );
            
            // Agregar al historial de asistencia del estudiante
            estudiante.getTheHistorialAsistencia().add(asistencia);
        }
    }

    @Override
    public void evaluarProgreso(Estudiante estudiante, double calificacion, String comentarios) {
        // Verificar que el estudiante sea el asignado a esta clase individual
        if (theEstudiante != null && theEstudiante.equals(estudiante)) {
            LocalDate fechaActual = LocalDate.now();
            
            // Generar ID único para la evaluación
            String idEvaluacion = "EVAL-" + this.id + "-" + estudiante.getId() + "-" + 
                                  fechaActual.toString().replace("-", "");
            
            // Crear nueva evaluación de progreso
            EvaluacionProgreso evaluacion = new EvaluacionProgreso(
                idEvaluacion,
                calificacion,
                comentarios,
                "", // areasAMejorar inicialmente vacío
                fechaActual.toString() // Convierte LocalDate a String en formato ISO (yyyy-MM-dd)
            );
            
            // Configurar relaciones
            evaluacion.setTheEstudiante(estudiante);
            evaluacion.setTheClase(this);
            if (theProfesor != null) {
                evaluacion.setTheEvaluador(theProfesor);
            }
            
            // Agregar a las evaluaciones del estudiante
            estudiante.getTheEvaluaciones().add(evaluacion);
        }
    }
}
