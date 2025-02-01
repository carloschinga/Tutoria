/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
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
@Table(name = "Tutoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tutoria.findAll", query = "SELECT t FROM Tutoria t"),
    @NamedQuery(name = "Tutoria.findByCodigoSede", query = "SELECT t FROM Tutoria t WHERE t.tutoriaPK.codigoSede = :codigoSede"),
    @NamedQuery(name = "Tutoria.findByCodigoUniversitario", query = "SELECT t FROM Tutoria t WHERE t.tutoriaPK.codigoUniversitario = :codigoUniversitario"),
    @NamedQuery(name = "Tutoria.findByCodigoDocente", query = "SELECT t FROM Tutoria t WHERE t.tutoriaPK.codigoDocente = :codigoDocente"),
    @NamedQuery(name = "Tutoria.findByAnio", query = "SELECT t FROM Tutoria t WHERE t.tutoriaPK.anio = :anio"),
    @NamedQuery(name = "Tutoria.findBySemestre", query = "SELECT t FROM Tutoria t WHERE t.tutoriaPK.semestre = :semestre"),
    @NamedQuery(name = "Tutoria.findByEstado", query = "SELECT t FROM Tutoria t WHERE t.estado = :estado")})
    
public class Tutoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TutoriaPK tutoriaPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "Estado")
    private String estado;

    public Tutoria() {
    }

    public Tutoria(TutoriaPK tutoriaPK) {
        this.tutoriaPK = tutoriaPK;
    }

    public Tutoria(TutoriaPK tutoriaPK, String estado) {
        this.tutoriaPK = tutoriaPK;
        this.estado = estado;
    }

    public Tutoria(String codigoSede, String codigoUniversitario, int codigoDocente, String anio, Character semestre) {
        this.tutoriaPK = new TutoriaPK(codigoSede, codigoUniversitario, codigoDocente, anio, semestre);
    }

    public TutoriaPK getTutoriaPK() {
        return tutoriaPK;
    }

    public void setTutoriaPK(TutoriaPK tutoriaPK) {
        this.tutoriaPK = tutoriaPK;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tutoriaPK != null ? tutoriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tutoria)) {
            return false;
        }
        Tutoria other = (Tutoria) object;
        if ((this.tutoriaPK == null && other.tutoriaPK != null) || (this.tutoriaPK != null && !this.tutoriaPK.equals(other.tutoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Tutoria[ tutoriaPK=" + tutoriaPK + " ]";
    }
    
}
