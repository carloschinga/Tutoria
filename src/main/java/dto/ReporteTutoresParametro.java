/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author san21
 */
public class ReporteTutoresParametro {

    private String codiEscuela;
    private String codiAño;
    private String codiSemestre;
    private String escuela;
    private String nombretutor;
    private String CodigoTutor;

    public String getCodigoTutor() {
        return CodigoTutor;
    }

    public void setCodigoTutor(String CodigoTutor) {
        this.CodigoTutor = CodigoTutor;
    }
    public String getNombretutor() {
        return nombretutor;
    }

    public void setNombretutor(String nombretutor) {
        this.nombretutor = nombretutor;
    }

    // Constructor
    public ReporteTutoresParametro(String codiEscuela, String codiAño, String codiSemestre, String escuela) {
        this.codiEscuela = codiEscuela;
        this.codiAño = codiAño;
        this.codiSemestre = codiSemestre;
        this.escuela = escuela;
    }

    public ReporteTutoresParametro() {
    }

    // Getters y Setters
    public String getCodiEscuela() {
        return codiEscuela;
    }

    public void setCodiEscuela(String codiEscuela) {
        this.codiEscuela = codiEscuela;
    }

    public String getCodiAño() {
        return codiAño;
    }

    public void setCodiAño(String codiAño) {
        this.codiAño = codiAño;
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

    @Override
    public String toString() {
        return "ReporteTutoresParametro{"
                + "codiEscuela='" + codiEscuela + '\''
                + ", codiAnio='" + codiAño + '\''
                + ", codiSemestre='" + codiSemestre + '\''
                + ", nombretutor='" + nombretutor + '\''
                + '}';
    }

}
