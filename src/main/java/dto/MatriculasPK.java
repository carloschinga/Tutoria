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
public class MatriculasPK implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "NumeroMatricula")
    private String numeroMatricula;

    public MatriculasPK() {
    }

    public MatriculasPK(String codigoSede, String codigoUniversitario, String numeroMatricula) {
        this.codigoSede = codigoSede;
        this.codigoUniversitario = codigoUniversitario;
        this.numeroMatricula = numeroMatricula;
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

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoSede != null ? codigoSede.hashCode() : 0);
        hash += (codigoUniversitario != null ? codigoUniversitario.hashCode() : 0);
        hash += (numeroMatricula != null ? numeroMatricula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MatriculasPK)) {
            return false;
        }
        MatriculasPK other = (MatriculasPK) object;
        if ((this.codigoSede == null && other.codigoSede != null) || (this.codigoSede != null && !this.codigoSede.equals(other.codigoSede))) {
            return false;
        }
        if ((this.codigoUniversitario == null && other.codigoUniversitario != null) || (this.codigoUniversitario != null && !this.codigoUniversitario.equals(other.codigoUniversitario))) {
            return false;
        }
        if ((this.numeroMatricula == null && other.numeroMatricula != null) || (this.numeroMatricula != null && !this.numeroMatricula.equals(other.numeroMatricula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.MatriculasPK[ codigoSede=" + codigoSede + ", codigoUniversitario=" + codigoUniversitario + ", numeroMatricula=" + numeroMatricula + " ]";
    }
    
}
