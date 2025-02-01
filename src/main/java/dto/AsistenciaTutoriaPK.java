/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author shaho
 */
@Embeddable
public class AsistenciaTutoriaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "CodigoDocente")
    private int codigoDocente;
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
    @Column(name = "Sesion")
    private int sesion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "CodigoSede")
    private String codigoSede;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CodigoUniversitario")
    private String codigoUniversitario;

    public AsistenciaTutoriaPK() {
    }

    public AsistenciaTutoriaPK(int codigoDocente, String anio, Character semestre, int sesion, String codigoSede, String codigoUniversitario) {
        this.codigoDocente = codigoDocente;
        this.anio = anio;
        this.semestre = semestre;
        this.sesion = sesion;
        this.codigoSede = codigoSede;
        this.codigoUniversitario = codigoUniversitario;
    }

    public int getCodigoDocente() {
        return codigoDocente;
    }

    public void setCodigoDocente(int codigoDocente) {
        this.codigoDocente = codigoDocente;
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

    public int getSesion() {
        return sesion;
    }

    public void setSesion(int sesion) {
        this.sesion = sesion;
    }

    public String getCodigoSede() {
        return codigoSede;
    }

    public void setCodigoSede(String codigoSede) {
        this.codigoSede = codigoSede;
    }

    public String getCodigoUniversitario() {
        return codigoUniversitario;
    }

    public void setCodigoUniversitario(String codigoUniversitario) {
        this.codigoUniversitario = codigoUniversitario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoDocente;
        hash += (anio != null ? anio.hashCode() : 0);
        hash += (semestre != null ? semestre.hashCode() : 0);
        hash += (int) sesion;
        hash += (codigoSede != null ? codigoSede.hashCode() : 0);
        hash += (codigoUniversitario != null ? codigoUniversitario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistenciaTutoriaPK)) {
            return false;
        }
        AsistenciaTutoriaPK other = (AsistenciaTutoriaPK) object;
        if (this.codigoDocente != other.codigoDocente) {
            return false;
        }
        if ((this.anio == null && other.anio != null) || (this.anio != null && !this.anio.equals(other.anio))) {
            return false;
        }
        if ((this.semestre == null && other.semestre != null) || (this.semestre != null && !this.semestre.equals(other.semestre))) {
            return false;
        }
        if (this.sesion != other.sesion) {
            return false;
        }
        if ((this.codigoSede == null && other.codigoSede != null) || (this.codigoSede != null && !this.codigoSede.equals(other.codigoSede))) {
            return false;
        }
        if ((this.codigoUniversitario == null && other.codigoUniversitario != null) || (this.codigoUniversitario != null && !this.codigoUniversitario.equals(other.codigoUniversitario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.AsistenciaTutoriaPK[ codigoDocente=" + codigoDocente + ", anio=" + anio + ", semestre=" + semestre + ", sesion=" + sesion + ", codigoSede=" + codigoSede + ", codigoUniversitario=" + codigoUniversitario + " ]";
    }
    
}
