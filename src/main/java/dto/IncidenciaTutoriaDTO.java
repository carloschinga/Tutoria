package dto;

public class IncidenciaTutoriaDTO {
    private String codigoUniversitario;
    private String apellidos;
    private String alumno;
    private String incidencia;
    private String observacion;
    private String fecha;

    public IncidenciaTutoriaDTO(String codigoUniversitario, String apellidos, String alumno, String incidencia, String observacion, String fecha) {
        this.codigoUniversitario = codigoUniversitario;
        this.apellidos = apellidos;
        this.alumno = alumno;
        this.incidencia = incidencia;
        this.observacion = observacion;
        this.fecha = fecha;
    }

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

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(String incidencia) {
        this.incidencia = incidencia;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}