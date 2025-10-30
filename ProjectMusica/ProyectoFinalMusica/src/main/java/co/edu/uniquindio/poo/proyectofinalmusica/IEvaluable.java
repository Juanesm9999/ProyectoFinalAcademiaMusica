package ProyectoFinal.model;

public interface IEvaluable {
    void registrarAsistencia(Estudiante estudiante, boolean presente);
    void evaluarProgreso(Estudiante estudiante, double calificacion, String comentarios);
}