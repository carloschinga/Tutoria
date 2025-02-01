/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author san21
 */
public class ReporteTutores {

    private String nombre;
    private String ciclo;
    private String Codigo_Docente;

    public String getCodigo_Docente() {
        return Codigo_Docente;
    }

    public void setCodigo_Docente(String Codigo_Docente) {
        this.Codigo_Docente = Codigo_Docente;
    }

    // Constructor
    public ReporteTutores(String nombre, String ciclo) {
        this.nombre = nombre;
        this.ciclo = ciclo;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

}
