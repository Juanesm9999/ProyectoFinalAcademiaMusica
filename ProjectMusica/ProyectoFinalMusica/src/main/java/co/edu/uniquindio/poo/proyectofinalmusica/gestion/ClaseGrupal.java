package ProyectoFinal.model.gestion;

import ProyectoFinal.model.Clase;
import ProyectoFinal.model.Estudiante;
import ProyectoFinal.model.TipoInstrumento;

import java.util.List;

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
    public void registrarAsistenciaGrupal(Estudiante estudiante, boolean presente) {

    }

    @Override
    public void evaluarProgresoGrupal(Estudiante estudiante, double calificacion, String comentarios) {

    }
}
