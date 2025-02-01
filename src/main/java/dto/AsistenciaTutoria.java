/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author shaho
 */
@Entity
@Table(name = "AsistenciaTutoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsistenciaTutoria.findAll", query = "SELECT a FROM AsistenciaTutoria a"),
    @NamedQuery(name = "AsistenciaTutoria.findByCodigoDocente", query = "SELECT a FROM AsistenciaTutoria a WHERE a.asistenciaTutoriaPK.codigoDocente = :codigoDocente"),
    @NamedQuery(name = "AsistenciaTutoria.findByAnio", query = "SELECT a FROM AsistenciaTutoria a WHERE a.asistenciaTutoriaPK.anio = :anio"),
    @NamedQuery(name = "AsistenciaTutoria.findBySemestre", query = "SELECT a FROM AsistenciaTutoria a WHERE a.asistenciaTutoriaPK.semestre = :semestre"),
    @NamedQuery(name = "AsistenciaTutoria.findBySesion", query = "SELECT a FROM AsistenciaTutoria a WHERE a.asistenciaTutoriaPK.sesion = :sesion"),
    @NamedQuery(name = "AsistenciaTutoria.findByCodigoSede", query = "SELECT a FROM AsistenciaTutoria a WHERE a.asistenciaTutoriaPK.codigoSede = :codigoSede"),
    @NamedQuery(name = "AsistenciaTutoria.findByCodigoUniversitario", query = "SELECT a FROM AsistenciaTutoria a WHERE a.asistenciaTutoriaPK.codigoUniversitario = :codigoUniversitario"),
    @NamedQuery(name = "AsistenciaTutoria.findByAsistencia", query = "SELECT a FROM AsistenciaTutoria a WHERE a.asistencia = :asistencia")})
public class AsistenciaTutoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AsistenciaTutoriaPK asistenciaTutoriaPK;
    @Size(max = 1)
    @Column(name = "Asistencia")
    private String asistencia;

    public AsistenciaTutoria() {
    }

    public AsistenciaTutoria(AsistenciaTutoriaPK asistenciaTutoriaPK) {
        this.asistenciaTutoriaPK = asistenciaTutoriaPK;
    }

    public AsistenciaTutoria(int codigoDocente, String anio, Character semestre, int sesion, String codigoSede, String codigoUniversitario) {
        this.asistenciaTutoriaPK = new AsistenciaTutoriaPK(codigoDocente, anio, semestre, sesion, codigoSede, codigoUniversitario);
    }

    public AsistenciaTutoriaPK getAsistenciaTutoriaPK() {
        return asistenciaTutoriaPK;
    }

    public void setAsistenciaTutoriaPK(AsistenciaTutoriaPK asistenciaTutoriaPK) {
        this.asistenciaTutoriaPK = asistenciaTutoriaPK;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (asistenciaTutoriaPK != null ? asistenciaTutoriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistenciaTutoria)) {
            return false;
        }
        AsistenciaTutoria other = (AsistenciaTutoria) object;
        if ((this.asistenciaTutoriaPK == null && other.asistenciaTutoriaPK != null) || (this.asistenciaTutoriaPK != null && !this.asistenciaTutoriaPK.equals(other.asistenciaTutoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.AsistenciaTutoria[ asistenciaTutoriaPK=" + asistenciaTutoriaPK + " ]";
    }
    
}
