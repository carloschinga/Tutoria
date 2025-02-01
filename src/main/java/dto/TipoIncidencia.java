/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "TipoIncidencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoIncidencia.findAll", query = "SELECT t.codigoIncidencia,t.incidencia FROM TipoIncidencia t where t.estado='S'"),
    @NamedQuery(name = "TipoIncidencia.findByCodigoIncidencia", query = "SELECT t FROM TipoIncidencia t WHERE t.codigoIncidencia = :codigoIncidencia"),
    @NamedQuery(name = "TipoIncidencia.findByIncidencia", query = "SELECT t FROM TipoIncidencia t WHERE t.incidencia = :incidencia"),
    @NamedQuery(name = "TipoIncidencia.findByEstado", query = "SELECT t FROM TipoIncidencia t WHERE t.estado = :estado")})
public class TipoIncidencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CodigoIncidencia")
    private Integer codigoIncidencia;
    @Size(max = 100)
    @Column(name = "Incidencia")
    private String incidencia;
    @Size(max = 1)
    @Column(name = "Estado")
    private String estado;

    public TipoIncidencia() {
    }

    public TipoIncidencia(Integer codigoIncidencia) {
        this.codigoIncidencia = codigoIncidencia;
    }

    public Integer getCodigoIncidencia() {
        return codigoIncidencia;
    }

    public void setCodigoIncidencia(Integer codigoIncidencia) {
        this.codigoIncidencia = codigoIncidencia;
    }

    public String getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(String incidencia) {
        this.incidencia = incidencia;
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
        hash += (codigoIncidencia != null ? codigoIncidencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoIncidencia)) {
            return false;
        }
        TipoIncidencia other = (TipoIncidencia) object;
        if ((this.codigoIncidencia == null && other.codigoIncidencia != null) || (this.codigoIncidencia != null && !this.codigoIncidencia.equals(other.codigoIncidencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.TipoIncidencia[ codigoIncidencia=" + codigoIncidencia + " ]";
    }
    
}
