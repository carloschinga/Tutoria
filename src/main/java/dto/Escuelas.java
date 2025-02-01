/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "Escuelas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Escuelas.findAll", query = "SELECT e FROM Escuelas e"),
    @NamedQuery(name = "Escuelas.findByCodigoEscuela", query = "SELECT e FROM Escuelas e WHERE e.codigoEscuela = :codigoEscuela"),
    @NamedQuery(name = "Escuelas.findByCodigoFacultad", query = "SELECT e.codigoEscuela,e.escuela,e.escuelaAbrev FROM Escuelas e WHERE e.codigoFacultad = :codigoFacultad and e.estado=1"),
    @NamedQuery(name = "Escuelas.findByCodPrograma", query = "SELECT e FROM Escuelas e WHERE e.codPrograma = :codPrograma"),
    @NamedQuery(name = "Escuelas.findByEscuela", query = "SELECT e FROM Escuelas e WHERE e.escuela = :escuela"),
    @NamedQuery(name = "Escuelas.findByEscuelaAbrev", query = "SELECT e FROM Escuelas e WHERE e.escuelaAbrev = :escuelaAbrev"),
    @NamedQuery(name = "Escuelas.findByEstado", query = "SELECT e FROM Escuelas e WHERE e.estado = :estado"),
    @NamedQuery(name = "Escuelas.findByTasaImplementacion", query = "SELECT e FROM Escuelas e WHERE e.tasaImplementacion = :tasaImplementacion"),
    @NamedQuery(name = "Escuelas.findByCodigoPesoAcademico", query = "SELECT e FROM Escuelas e WHERE e.codigoPesoAcademico = :codigoPesoAcademico"),
    @NamedQuery(name = "Escuelas.findByCodigoDptoAcademico", query = "SELECT e FROM Escuelas e WHERE e.codigoDptoAcademico = :codigoDptoAcademico"),
    @NamedQuery(name = "Escuelas.findByCodigoTributoMatricula", query = "SELECT e FROM Escuelas e WHERE e.codigoTributoMatricula = :codigoTributoMatricula"),
    @NamedQuery(name = "Escuelas.findByCodigoTributoCurso", query = "SELECT e FROM Escuelas e WHERE e.codigoTributoCurso = :codigoTributoCurso"),
    @NamedQuery(name = "Escuelas.findByCodProgDocSUNEDU", query = "SELECT e FROM Escuelas e WHERE e.codProgDocSUNEDU = :codProgDocSUNEDU")})
public class Escuelas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "CodigoEscuela")
    private String codigoEscuela;
    @Size(max = 3)
    @Column(name = "Cod_Programa")
    private String codPrograma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Escuela")
    private String escuela;
    @Size(max = 20)
    @Column(name = "EscuelaAbrev")
    private String escuelaAbrev;
    @Column(name = "Estado")
    private Boolean estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TasaImplementacion")
    private BigDecimal tasaImplementacion;
    @Column(name = "CodigoPesoAcademico")
    private Short codigoPesoAcademico;
    @Column(name = "CodigoDptoAcademico")
    private Short codigoDptoAcademico;
    @Size(max = 5)
    @Column(name = "CodigoTributoMatricula")
    private String codigoTributoMatricula;
    @Size(max = 5)
    @Column(name = "CodigoTributoCurso")
    private String codigoTributoCurso;
    @Size(max = 5)
    @Column(name = "CodProgDocSUNEDU")
    private String codProgDocSUNEDU;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "CodigoFacultad")
    private String codigoFacultad;

    public String getCodigoFacultad() {
        return codigoFacultad;
    }

    public void setCodigoFacultad(String codigoFacultad) {
        this.codigoFacultad = codigoFacultad;
    }

    public Escuelas() {
    }

    public Escuelas(String codigoEscuela) {
        this.codigoEscuela = codigoEscuela;
    }

    public Escuelas(String codigoEscuela, String escuela) {
        this.codigoEscuela = codigoEscuela;
        this.escuela = escuela;
    }

    public String getCodigoEscuela() {
        return codigoEscuela;
    }

    public void setCodigoEscuela(String codigoEscuela) {
        this.codigoEscuela = codigoEscuela;
    }

    public String getCodPrograma() {
        return codPrograma;
    }

    public void setCodPrograma(String codPrograma) {
        this.codPrograma = codPrograma;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public String getEscuelaAbrev() {
        return escuelaAbrev;
    }

    public void setEscuelaAbrev(String escuelaAbrev) {
        this.escuelaAbrev = escuelaAbrev;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public BigDecimal getTasaImplementacion() {
        return tasaImplementacion;
    }

    public void setTasaImplementacion(BigDecimal tasaImplementacion) {
        this.tasaImplementacion = tasaImplementacion;
    }

    public Short getCodigoPesoAcademico() {
        return codigoPesoAcademico;
    }

    public void setCodigoPesoAcademico(Short codigoPesoAcademico) {
        this.codigoPesoAcademico = codigoPesoAcademico;
    }

    public Short getCodigoDptoAcademico() {
        return codigoDptoAcademico;
    }

    public void setCodigoDptoAcademico(Short codigoDptoAcademico) {
        this.codigoDptoAcademico = codigoDptoAcademico;
    }

    public String getCodigoTributoMatricula() {
        return codigoTributoMatricula;
    }

    public void setCodigoTributoMatricula(String codigoTributoMatricula) {
        this.codigoTributoMatricula = codigoTributoMatricula;
    }

    public String getCodigoTributoCurso() {
        return codigoTributoCurso;
    }

    public void setCodigoTributoCurso(String codigoTributoCurso) {
        this.codigoTributoCurso = codigoTributoCurso;
    }

    public String getCodProgDocSUNEDU() {
        return codProgDocSUNEDU;
    }

    public void setCodProgDocSUNEDU(String codProgDocSUNEDU) {
        this.codProgDocSUNEDU = codProgDocSUNEDU;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoEscuela != null ? codigoEscuela.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Escuelas)) {
            return false;
        }
        Escuelas other = (Escuelas) object;
        if ((this.codigoEscuela == null && other.codigoEscuela != null) || (this.codigoEscuela != null && !this.codigoEscuela.equals(other.codigoEscuela))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Escuelas[ codigoEscuela=" + codigoEscuela + " ]";
    }
    
}
