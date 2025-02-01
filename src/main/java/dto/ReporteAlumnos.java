package dto;

/**
 *
 * @author san21
 */
public class ReporteAlumnos {

    private String codiEscuela;
    private String codiAnio;  // Cambio de "Año" a "Anio" para evitar problemas
    private String codiSemestre;
    private String escuela;
    private String nombretutor;

    // Constructor con todos los campos
    public ReporteAlumnos(String codiEscuela, String codiAnio, String codiSemestre, String escuela, String nombretutor) {
        this.codiEscuela = codiEscuela;
        this.codiAnio = codiAnio;
        this.codiSemestre = codiSemestre;
        this.escuela = escuela;
        this.nombretutor = nombretutor;
    }

    // Constructor vacío
    public ReporteAlumnos() {
    }

    // Getters y Setters
    public String getCodiEscuela() {
        return codiEscuela;
    }

    public void setCodiEscuela(String codiEscuela) {
        this.codiEscuela = codiEscuela;
    }

    public String getCodiAnio() {
        return codiAnio;
    }

    public void setCodiAnio(String codiAnio) {
        this.codiAnio = codiAnio;
    }

    public String getCodiSemestre() {
        return codiSemestre;
    }

    public void setCodiSemestre(String codiSemestre) {
        this.codiSemestre = codiSemestre;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public String getNombretutor() {
        return nombretutor;
    }

    public void setNombretutor(String nombretutor) {
        this.nombretutor = nombretutor;
    }

    // Método toString() para depuración
   
}
