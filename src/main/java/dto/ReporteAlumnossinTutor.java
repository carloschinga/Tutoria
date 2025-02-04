package dto;

/**
 * DTO para representar los datos de alumnos sin tutoría.
 * 
 * @author san21
 */
public class ReporteAlumnossinTutor {

    private String codigoUniversitario;
    private String apellidos;
    private String nombres;
    private String ciclo;

    // Constructor vacío
    public ReporteAlumnossinTutor() {
    }

    // Constructor con todos los campos
    public ReporteAlumnossinTutor(String codigoUniversitario, String apellidos, String nombres, String ciclo) {
        this.codigoUniversitario = codigoUniversitario;
        this.apellidos = apellidos;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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
                ", apellidos='" + apellidos + '\'' +
                ", nombres='" + nombres + '\'' +
                ", ciclo='" + ciclo + '\'' +
                '}';
    }
}
