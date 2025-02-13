package dto;

/**
 * DTO para representar los datos de alumnos sin tutoría.
 * 
 * @author san21
 */
public class ReporteAlumnossinTutor {

    private String codigoUniversitario;
    private String nombres;
    private String ciclo;

    // Constructor vacío
    public ReporteAlumnossinTutor() {
    }

    // Constructor con todos los campos
    public ReporteAlumnossinTutor(String codigoUniversitario, String nombres, String ciclo) {
        this.codigoUniversitario = codigoUniversitario;
        this.nombres = nombres;
        this.ciclo = ciclo;
    }

    // Getters y Setters
    public String getCodigoUniversitario() {
        return codigoUniversitario;
    }

    public void setCodigoUniversitario(String codigoUniversitario) {
        this.codigoUniversitario = codigoUniversitario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    @Override
    public String toString() {
        return "ReportedeAlumnosSinTutor{" +
                "codigoUniversitario='" + codigoUniversitario + '\'' +
                ", nombres='" + nombres + '\'' +
                ", ciclo='" + ciclo + '\'' +
                '}';
    }
}
