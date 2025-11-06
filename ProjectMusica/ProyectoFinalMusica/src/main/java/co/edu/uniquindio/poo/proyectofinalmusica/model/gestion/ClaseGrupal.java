package co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;

import co.edu.uniquindio.poo.proyectofinalmusica.model.Clase;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;
import co.edu.uniquindio.poo.proyectofinalmusica.model.TipoInstrumento;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Asistencia;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.EvaluacionProgreso;

public class ClaseGrupal extends Clase {
    private int capacidadMaxima;
    private int capacidadActual;
    private int cuposDisponibles;
    private List<Estudiante> theEstudiantesInscritos; // muchos a muchos
    private String descripcion;

    public ClaseGrupal(int capacidadMaxima, int capacidadActual, int cuposDisponibles, String descripcion, String id, String horario, String diaSemana, String horaInicio,String HoraFin, TipoInstrumento instrumento, int nivel, boolean activa) {
        super(id,horario,diaSemana,horaInicio,HoraFin,instrumento,nivel,activa );
        this.capacidadMaxima = capacidadMaxima;
        this.capacidadActual = capacidadActual;
        this.cuposDisponibles = cuposDisponibles;
        this.descripcion = descripcion;
        this.theEstudiantesInscritos = new ArrayList<>();

    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getCapacidadActual() {
        return capacidadActual;
    }

    public void setCapacidadActual(int capacidadActual) {
        this.capacidadActual = capacidadActual;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void setCuposDisponibles(int cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }

    public List<Estudiante> getTheEstudiantesInscritos() {
        return theEstudiantesInscritos;
    }





    public void setTheEstudiantesInscritos(List<Estudiante> theEstudiantesInscritos) {
        this.theEstudiantesInscritos = theEstudiantesInscritos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public void registrarAsistencia(Estudiante estudiante, boolean presente) {
        // Verificar que el estudiante esté inscrito en la clase grupal
        if (theEstudiantesInscritos.contains(estudiante)) {
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
        // Verificar que el estudiante esté inscrito en la clase grupal
        if (theEstudiantesInscritos.contains(estudiante)) {
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
