package dto;

public class ReporteActividad {

    private String actividad;
    private String lugar;
    private String tutor;
    private String tipo;
    private String fecha;


    // Constructor
    public ReporteActividad( String tipo, String actividad, String lugar, String tutor, String fecha) {
        this.actividad = actividad;
        this.lugar = lugar;
        this.tutor = tutor;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public String getTutor() {
        return tutor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    // Getters y Setters
    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
