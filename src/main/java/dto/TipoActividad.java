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
@Table(name = "TipoActividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoActividad.findAll", query = "SELECT t.codigoTipoActividad,t.nombreActividad FROM TipoActividad t  WHERE t.estado = 'S'"),
    @NamedQuery(name = "TipoActividad.findByCodigoTipoActividad", query = "SELECT t FROM TipoActividad t WHERE t.codigoTipoActividad = :codigoTipoActividad"),
    @NamedQuery(name = "TipoActividad.findByNombreActividad", query = "SELECT t FROM TipoActividad t WHERE t.nombreActividad = :nombreActividad"),
    @NamedQuery(name = "TipoActividad.findByEstado", query = "SELECT t FROM TipoActividad t WHERE t.estado = :estado")})
public class TipoActividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CodigoTipoActividad")
    private Integer codigoTipoActividad;
    @Size(max = 100)
    @Column(name = "NombreActividad")
    private String nombreActividad;
    @Size(max = 1)
    @Column(name = "Estado")
    private String estado;

    public TipoActividad() {
    }

    public TipoActividad(Integer codigoTipoActividad) {
        this.codigoTipoActividad = codigoTipoActividad;
    }

    public Integer getCodigoTipoActividad() {
        return codigoTipoActividad;
    }

    public void setCodigoTipoActividad(Integer codigoTipoActividad) {
        this.codigoTipoActividad = codigoTipoActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
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
        hash += (codigoTipoActividad != null ? codigoTipoActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoActividad)) {
            return false;
        }
        TipoActividad other = (TipoActividad) object;
        if ((this.codigoTipoActividad == null && other.codigoTipoActividad != null) || (this.codigoTipoActividad != null && !this.codigoTipoActividad.equals(other.codigoTipoActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.TipoActividad[ codigoTipoActividad=" + codigoTipoActividad + " ]";
    }
    
}
