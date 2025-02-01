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
public class ActividadTutoriaPK implements Serializable {

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

    public ActividadTutoriaPK() {
    }

    public ActividadTutoriaPK(int codigoDocente, String anio, Character semestre, int sesion) {
        this.codigoDocente = codigoDocente;
        this.anio = anio;
        this.semestre = semestre;
        this.sesion = sesion;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoDocente;
        hash += (anio != null ? anio.hashCode() : 0);
        hash += (semestre != null ? semestre.hashCode() : 0);
        hash += (int) sesion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadTutoriaPK)) {
            return false;
        }
        ActividadTutoriaPK other = (ActividadTutoriaPK) object;
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
        return true;
    }

    @Override
    public String toString() {
        return "dto.ActividadTutoriaPK[ codigoDocente=" + codigoDocente + ", anio=" + anio + ", semestre=" + semestre + ", sesion=" + sesion + " ]";
    }
    
}
