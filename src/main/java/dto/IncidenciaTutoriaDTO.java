package dto;

/**
 * DTO para representar una incidencia de tutoría.
 */
public class IncidenciaTutoriaDTO {
    private String fecha;
    private String observacion;
    ;
    private String incidencia;
    private String codigoIncidencia;

    // Constructor
    public IncidenciaTutoriaDTO(String fecha, String observacion, String incidencia, String codigoIncidencia) {
        this.fecha = fecha;
        this.observacion = observacion;
        this.incidencia = incidencia;
        this.codigoIncidencia = codigoIncidencia;
    }

    // Getters y Setters
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }  
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(String incidencia) {
        this.incidencia = incidencia;
    }

    public String getCodigoIncidencia() {
        return codigoIncidencia;
    }

    public void setCodigoIncidencia(String codigoIncidencia) {
        this.codigoIncidencia = codigoIncidencia;
    }

    // Método toString() para depuración
    @Override
    public String toString() {
        return "IncidenciaTutoriaDTO{" +
                "fecha='" + fecha + '\'' +
                ", observacion='" + observacion + '\'' +
                ", incidencia='" + incidencia + '\'' +
                ", codigoIncidencia='" + codigoIncidencia + '\'' +
                '}';
    }
}
