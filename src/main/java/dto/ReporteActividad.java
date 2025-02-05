package dto;

public class ReporteActividad {

    private String actividad;
    private String lugar;

    // Constructor
    public ReporteActividad(String actividad, String lugar) {
        this.actividad = actividad;
        this.lugar = lugar;
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
