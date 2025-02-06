package dto;

public class ReporteActividad {

    private String actividad;
    private String lugar;
    private String tutor;

    // Constructor
    public ReporteActividad(String actividad, String lugar,String tutor) {
        this.actividad = actividad;
        this.lugar = lugar;
        this.tutor = tutor;
    }

    public String getTutor() {
        return tutor;
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
}
