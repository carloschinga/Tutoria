/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "ActividadTutoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadTutoria.findAll", query = "SELECT a FROM ActividadTutoria  a"),
    @NamedQuery(name = "ActividadTutoria.findByCodigoDocente", query = "SELECT a FROM ActividadTutoria a WHERE a.actividadTutoriaPK.codigoDocente = :codigoDocente"),
    @NamedQuery(name = "ActividadTutoria.ultimasesion", query = "SELECT a.actividadTutoriaPK.sesion FROM ActividadTutoria a WHERE a.actividadTutoriaPK.codigoDocente = :codigoDocente and a.actividadTutoriaPK.anio = :anio and a.actividadTutoriaPK.semestre = :semestre order by  a.actividadTutoriaPK.sesion desc"),
    @NamedQuery(name = "ActividadTutoria.findByAnio", query = "SELECT a FROM ActividadTutoria a WHERE a.actividadTutoriaPK.anio = :anio"),
    @NamedQuery(name = "ActividadTutoria.findBySemestre", query = "SELECT a FROM ActividadTutoria a WHERE a.actividadTutoriaPK.semestre = :semestre"),
    @NamedQuery(name = "ActividadTutoria.findBySesion", query = "SELECT a FROM ActividadTutoria a WHERE a.actividadTutoriaPK.sesion = :sesion"),
    @NamedQuery(name = "ActividadTutoria.findByActividad", query = "SELECT a FROM ActividadTutoria a WHERE a.actividad = :actividad"),
    @NamedQuery(name = "ActividadTutoria.findByCodigoTipoActividad", query = "SELECT a FROM ActividadTutoria a WHERE a.codigoTipoActividad = :codigoTipoActividad"),
    @NamedQuery(name = "ActividadTutoria.findByFechaActividad", query = "SELECT a FROM ActividadTutoria a WHERE a.fechaActividad = :fechaActividad"),
    @NamedQuery(name = "ActividadTutoria.findByLugar", query = "SELECT a FROM ActividadTutoria a WHERE a.lugar = :lugar"),
    @NamedQuery(name = "ActividadTutoria.findByEstado", query = "SELECT a FROM ActividadTutoria a WHERE a.estado = :estado")})
public class ActividadTutoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ActividadTutoriaPK actividadTutoriaPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "Actividad")
    private String actividad;
    @Column(name = "CodigoTipoActividad")
    private Integer codigoTipoActividad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FechaActividad")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActividad;
    @Size(max = 150)
    @Column(name = "Lugar")
    private String lugar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "Estado")
    private String estado;

    public ActividadTutoria() {
    }

    public ActividadTutoria(ActividadTutoriaPK actividadTutoriaPK) {
        this.actividadTutoriaPK = actividadTutoriaPK;
    }

    public ActividadTutoria(ActividadTutoriaPK actividadTutoriaPK, String actividad, Date fechaActividad, String estado) {
        this.actividadTutoriaPK = actividadTutoriaPK;
        this.actividad = actividad;
        this.fechaActividad = fechaActividad;
        this.estado = estado;
    }

    public ActividadTutoria(int codigoDocente, String anio, Character semestre, int sesion) {
        this.actividadTutoriaPK = new ActividadTutoriaPK(codigoDocente, anio, semestre, sesion);
    }

    public ActividadTutoriaPK getActividadTutoriaPK() {
        return actividadTutoriaPK;
    }

    public void setActividadTutoriaPK(ActividadTutoriaPK actividadTutoriaPK) {
        this.actividadTutoriaPK = actividadTutoriaPK;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Integer getCodigoTipoActividad() {
        return codigoTipoActividad;
    }

    public void setCodigoTipoActividad(Integer codigoTipoActividad) {
        this.codigoTipoActividad = codigoTipoActividad;
    }

    public Date getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(Date fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
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
        hash += (actividadTutoriaPK != null ? actividadTutoriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadTutoria)) {
            return false;
        }
        ActividadTutoria other = (ActividadTutoria) object;
        if ((this.actividadTutoriaPK == null && other.actividadTutoriaPK != null) || (this.actividadTutoriaPK != null && !this.actividadTutoriaPK.equals(other.actividadTutoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.ActividadTutoria[ actividadTutoriaPK=" + actividadTutoriaPK + " ]";
    }
    
}
