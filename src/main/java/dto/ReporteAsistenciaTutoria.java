/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author san21
 */
public class ReporteAsistenciaTutoria {
    private String NombreAlumno;
    private String CodigoUniversitario;
    private String Asistencia;
    private String Tutor;

    // Constructor vacío (necesario para algunos frameworks como Jackson o Gson)
    public ReporteAsistenciaTutoria() {
    }

    // Constructor con parámetros
    public ReporteAsistenciaTutoria(String nombreAlumno, String codigoUniversitario, String asistencia, String tutor) {
        this.NombreAlumno = nombreAlumno;
        this.CodigoUniversitario = codigoUniversitario;
        this.Asistencia = asistencia;
        this.Tutor = tutor;
    }

    // Getters y Setters
    public String getNombreAlumno() {
        return NombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.NombreAlumno = nombreAlumno;
    }

    public String getCodigoUniversitario() {
        return CodigoUniversitario;
    }

    public void setCodigoUniversitario(String codigoUniversitario) {
        this.CodigoUniversitario = codigoUniversitario;
    }

    public String getAsistencia() {
        return Asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.Asistencia = asistencia;
    }

    public String getTutor() {
        return Tutor;
    }

    public void setTutor(String tutor) {
        this.Tutor = tutor;
    }

    @Override
    public String toString() {
        return "ReporteAsistenciaTutoria{" +
                "NombreAlumno='" + NombreAlumno + '\'' +
                ", CodigoUniversitario='" + CodigoUniversitario + '\'' +
                ", Asistencia='" + Asistencia + '\'' +
                ", Tutor='" + Tutor + '\'' +
                '}';
    }
}