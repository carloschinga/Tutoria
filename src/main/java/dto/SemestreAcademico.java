/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author shaho
 */
@Entity
@Table(name = "SemestreAcademico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SemestreAcademico.findAll", query = "SELECT s FROM SemestreAcademico s order by  s.anio desc"),
    @NamedQuery(name = "SemestreAcademico.findByCodigoSemestre", query = "SELECT s FROM SemestreAcademico s WHERE s.codigoSemestre = :codigoSemestre"),
    @NamedQuery(name = "SemestreAcademico.findByAnio", query = "SELECT s FROM SemestreAcademico s WHERE s.anio = :anio"),
    @NamedQuery(name = "SemestreAcademico.findBySemestre", query = "SELECT s FROM SemestreAcademico s WHERE s.semestre = :semestre"),
    @NamedQuery(name = "SemestreAcademico.findByFechaInicio", query = "SELECT s FROM SemestreAcademico s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "SemestreAcademico.findByFechaFin", query = "SELECT s FROM SemestreAcademico s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SemestreAcademico.findByTasaMatricula", query = "SELECT s FROM SemestreAcademico s WHERE s.tasaMatricula = :tasaMatricula"),
    @NamedQuery(name = "SemestreAcademico.findByTasaCarne", query = "SELECT s FROM SemestreAcademico s WHERE s.tasaCarne = :tasaCarne"),
    @NamedQuery(name = "SemestreAcademico.findByTasaCurso", query = "SELECT s FROM SemestreAcademico s WHERE s.tasaCurso = :tasaCurso"),
    @NamedQuery(name = "SemestreAcademico.findActivo", query = "SELECT s.anio,s.semestre FROM SemestreAcademico s WHERE s.activo = 1"),
    @NamedQuery(name = "SemestreAcademico.findByAnulado", query = "SELECT s FROM SemestreAcademico s WHERE s.anulado = :anulado")})
public class SemestreAcademico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CodigoSemestre")
    private Integer codigoSemestre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "Anio")
    private String anio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Semestre")
    private Character semestre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "FechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TasaMatricula")
    private BigDecimal tasaMatricula;
    @Column(name = "TasaCarne")
    private BigDecimal tasaCarne;
    @Column(name = "TasaCurso")
    private BigDecimal tasaCurso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Activo")
    private boolean activo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Anulado")
    private boolean anulado;

    public SemestreAcademico() {
    }

    public SemestreAcademico(Integer codigoSemestre) {
        this.codigoSemestre = codigoSemestre;
    }

    public SemestreAcademico(Integer codigoSemestre, String anio, Character semestre, Date fechaInicio, boolean activo, boolean anulado) {
        this.codigoSemestre = codigoSemestre;
        this.anio = anio;
        this.semestre = semestre;
        this.fechaInicio = fechaInicio;
        this.activo = activo;
        this.anulado = anulado;
    }

    public Integer getCodigoSemestre() {
        return codigoSemestre;
    }

    public void setCodigoSemestre(Integer codigoSemestre) {
        this.codigoSemestre = codigoSemestre;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getTasaMatricula() {
        return tasaMatricula;
    }

    public void setTasaMatricula(BigDecimal tasaMatricula) {
        this.tasaMatricula = tasaMatricula;
    }

    public BigDecimal getTasaCarne() {
        return tasaCarne;
    }

    public void setTasaCarne(BigDecimal tasaCarne) {
        this.tasaCarne = tasaCarne;
    }

    public BigDecimal getTasaCurso() {
        return tasaCurso;
    }

    public void setTasaCurso(BigDecimal tasaCurso) {
        this.tasaCurso = tasaCurso;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(boolean anulado) {
        this.anulado = anulado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoSemestre != null ? codigoSemestre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SemestreAcademico)) {
            return false;
        }
        SemestreAcademico other = (SemestreAcademico) object;
        if ((this.codigoSemestre == null && other.codigoSemestre != null) || (this.codigoSemestre != null && !this.codigoSemestre.equals(other.codigoSemestre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.SemestreAcademico[ codigoSemestre=" + codigoSemestre + " ]";
    }
    
}
