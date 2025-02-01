/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author shaho
 */
@Entity
@Table(name = "Matriculas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Matriculas.findAll", query = "SELECT m FROM Matriculas m"),
    @NamedQuery(name = "Matriculas.findByCodigoSede", query = "SELECT m FROM Matriculas m WHERE m.matriculasPK.codigoSede = :codigoSede"),
    @NamedQuery(name = "Matriculas.findByCodigoUniversitario", query = "SELECT m FROM Matriculas m WHERE m.matriculasPK.codigoUniversitario = :codigoUniversitario"),
    @NamedQuery(name = "Matriculas.findByNumeroMatricula", query = "SELECT m FROM Matriculas m WHERE m.matriculasPK.numeroMatricula = :numeroMatricula"),
    @NamedQuery(name = "Matriculas.findByAnio", query = "SELECT m FROM Matriculas m WHERE m.anio = :anio"),
    @NamedQuery(name = "Matriculas.findBySemestre", query = "SELECT m FROM Matriculas m WHERE m.semestre = :semestre"),
    @NamedQuery(name = "Matriculas.findByCiclo", query = "SELECT m FROM Matriculas m WHERE m.ciclo = :ciclo"),
    @NamedQuery(name = "Matriculas.findByObservacion", query = "SELECT m FROM Matriculas m WHERE m.observacion = :observacion"),
    @NamedQuery(name = "Matriculas.findByFechaMatricula", query = "SELECT m FROM Matriculas m WHERE m.fechaMatricula = :fechaMatricula"),
    @NamedQuery(name = "Matriculas.findByResponsableMatricula", query = "SELECT m FROM Matriculas m WHERE m.responsableMatricula = :responsableMatricula"),
    @NamedQuery(name = "Matriculas.findByCondicionMatricula", query = "SELECT m FROM Matriculas m WHERE m.condicionMatricula = :condicionMatricula"),
    @NamedQuery(name = "Matriculas.findByTipoMatricula", query = "SELECT m FROM Matriculas m WHERE m.tipoMatricula = :tipoMatricula"),
    @NamedQuery(name = "Matriculas.findByNumeroMatriculaOSI", query = "SELECT m FROM Matriculas m WHERE m.numeroMatriculaOSI = :numeroMatriculaOSI"),
    @NamedQuery(name = "Matriculas.findByCodigoSituacion", query = "SELECT m FROM Matriculas m WHERE m.codigoSituacion = :codigoSituacion"),
    @NamedQuery(name = "Matriculas.findByPonderado", query = "SELECT m FROM Matriculas m WHERE m.ponderado = :ponderado"),
    @NamedQuery(name = "Matriculas.findByCodigoRangoNota", query = "SELECT m FROM Matriculas m WHERE m.codigoRangoNota = :codigoRangoNota"),
    @NamedQuery(name = "Matriculas.findBySedeMatricula", query = "SELECT m FROM Matriculas m WHERE m.sedeMatricula = :sedeMatricula"),
    @NamedQuery(name = "Matriculas.findByCodigoDesaprobado", query = "SELECT m FROM Matriculas m WHERE m.codigoDesaprobado = :codigoDesaprobado"),
    @NamedQuery(name = "Matriculas.findByCursosDesaprobados", query = "SELECT m FROM Matriculas m WHERE m.cursosDesaprobados = :cursosDesaprobados")})
public class Matriculas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MatriculasPK matriculasPK;
    @Size(max = 4)
    @Column(name = "Anio")
    private String anio;
    @Column(name = "Semestre")
    private Character semestre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "Ciclo")
    private String ciclo;
    @Size(max = 2000)
    @Column(name = "Observacion")
    private String observacion;
    @Size(max = 10)
    @Column(name = "FechaMatricula")
    private String fechaMatricula;
    @Size(max = 10)
    @Column(name = "ResponsableMatricula")
    private String responsableMatricula;
    @Column(name = "CondicionMatricula")
    private Short condicionMatricula;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TipoMatricula")
    private short tipoMatricula;
    @Size(max = 7)
    @Column(name = "NumeroMatriculaOSI")
    private String numeroMatriculaOSI;
    @Column(name = "CodigoSituacion")
    private Short codigoSituacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Ponderado")
    private BigDecimal ponderado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Codigo_Rango_Nota")
    private short codigoRangoNota;
    @Size(max = 2)
    @Column(name = "SedeMatricula")
    private String sedeMatricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "CodigoDesaprobado")
    private String codigoDesaprobado;
    @Column(name = "CursosDesaprobados")
    private Short cursosDesaprobados;

    public Matriculas() {
    }

    public Matriculas(MatriculasPK matriculasPK) {
        this.matriculasPK = matriculasPK;
    }

    public Matriculas(MatriculasPK matriculasPK, String ciclo, short tipoMatricula, short codigoRangoNota, String codigoDesaprobado) {
        this.matriculasPK = matriculasPK;
        this.ciclo = ciclo;
        this.tipoMatricula = tipoMatricula;
        this.codigoRangoNota = codigoRangoNota;
        this.codigoDesaprobado = codigoDesaprobado;
    }

    public Matriculas(String codigoSede, String codigoUniversitario, String numeroMatricula) {
        this.matriculasPK = new MatriculasPK(codigoSede, codigoUniversitario, numeroMatricula);
    }

    public MatriculasPK getMatriculasPK() {
        return matriculasPK;
    }

    public void setMatriculasPK(MatriculasPK matriculasPK) {
        this.matriculasPK = matriculasPK;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public Character getSemestre() {
        return semestre;
    }

    public void setSemestre(Character semestre) {
        this.semestre = semestre;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(String fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getResponsableMatricula() {
        return responsableMatricula;
    }

    public void setResponsableMatricula(String responsableMatricula) {
        this.responsableMatricula = responsableMatricula;
    }

    public Short getCondicionMatricula() {
        return condicionMatricula;
    }

    public void setCondicionMatricula(Short condicionMatricula) {
        this.condicionMatricula = condicionMatricula;
    }

    public short getTipoMatricula() {
        return tipoMatricula;
    }

    public void setTipoMatricula(short tipoMatricula) {
        this.tipoMatricula = tipoMatricula;
    }

    public String getNumeroMatriculaOSI() {
        return numeroMatriculaOSI;
    }

    public void setNumeroMatriculaOSI(String numeroMatriculaOSI) {
        this.numeroMatriculaOSI = numeroMatriculaOSI;
    }

    public Short getCodigoSituacion() {
        return codigoSituacion;
    }

    public void setCodigoSituacion(Short codigoSituacion) {
        this.codigoSituacion = codigoSituacion;
    }

    public BigDecimal getPonderado() {
        return ponderado;
    }

    public void setPonderado(BigDecimal ponderado) {
        this.ponderado = ponderado;
    }

    public short getCodigoRangoNota() {
        return codigoRangoNota;
    }

    public void setCodigoRangoNota(short codigoRangoNota) {
        this.codigoRangoNota = codigoRangoNota;
    }

    public String getSedeMatricula() {
        return sedeMatricula;
    }

    public void setSedeMatricula(String sedeMatricula) {
        this.sedeMatricula = sedeMatricula;
    }

    public String getCodigoDesaprobado() {
        return codigoDesaprobado;
    }

    public void setCodigoDesaprobado(String codigoDesaprobado) {
        this.codigoDesaprobado = codigoDesaprobado;
    }

    public Short getCursosDesaprobados() {
        return cursosDesaprobados;
    }

    public void setCursosDesaprobados(Short cursosDesaprobados) {
        this.cursosDesaprobados = cursosDesaprobados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matriculasPK != null ? matriculasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Matriculas)) {
            return false;
        }
        Matriculas other = (Matriculas) object;
        if ((this.matriculasPK == null && other.matriculasPK != null) || (this.matriculasPK != null && !this.matriculasPK.equals(other.matriculasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Matriculas[ matriculasPK=" + matriculasPK + " ]";
    }
    
}
