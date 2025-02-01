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
import javax.persistence.Lob;
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
@Table(name = "IncidenciasTutoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IncidenciasTutoria.findAll", query = "SELECT i FROM IncidenciasTutoria i"),
    @NamedQuery(name = "IncidenciasTutoria.ultimasesion", query = "SELECT i.incidenciasTutoriaPK.item FROM IncidenciasTutoria i where i.incidenciasTutoriaPK.codigoUniversitario=:codigoUniversitario and i.incidenciasTutoriaPK.codigoSede=:codigoSede and i.incidenciasTutoriaPK.anio=:anio and i.incidenciasTutoriaPK.semestre=:semestre order by i.incidenciasTutoriaPK.item desc"),
    @NamedQuery(name = "IncidenciasTutoria.findByCodigoSede", query = "SELECT i FROM IncidenciasTutoria i WHERE i.incidenciasTutoriaPK.codigoSede = :codigoSede"),
    @NamedQuery(name = "IncidenciasTutoria.findByCodigoUniversitario", query = "SELECT i FROM IncidenciasTutoria i WHERE i.incidenciasTutoriaPK.codigoUniversitario = :codigoUniversitario"),
    @NamedQuery(name = "IncidenciasTutoria.findByAnio", query = "SELECT i FROM IncidenciasTutoria i WHERE i.incidenciasTutoriaPK.anio = :anio"),
    @NamedQuery(name = "IncidenciasTutoria.findBySemestre", query = "SELECT i FROM IncidenciasTutoria i WHERE i.incidenciasTutoriaPK.semestre = :semestre"),
    @NamedQuery(name = "IncidenciasTutoria.findByItem", query = "SELECT i FROM IncidenciasTutoria i WHERE i.incidenciasTutoriaPK.item = :item"),
    @NamedQuery(name = "IncidenciasTutoria.findByFecha", query = "SELECT i FROM IncidenciasTutoria i WHERE i.fecha = :fecha"),
    @NamedQuery(name = "IncidenciasTutoria.findByCodigoIncidencia", query = "SELECT i FROM IncidenciasTutoria i WHERE i.codigoIncidencia = :codigoIncidencia"),
    @NamedQuery(name = "IncidenciasTutoria.findByCodigoDocente", query = "SELECT i FROM IncidenciasTutoria i WHERE i.codigoDocente = :codigoDocente"),
    @NamedQuery(name = "IncidenciasTutoria.findByEstado", query = "SELECT i FROM IncidenciasTutoria i WHERE i.estado = :estado")})
public class IncidenciasTutoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IncidenciasTutoriaPK incidenciasTutoriaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CodigoIncidencia")
    private int codigoIncidencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CodigoDocente")
    private int codigoDocente;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Observacion")
    private String observacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "Estado")
    private String estado;

    public IncidenciasTutoria() {
    }

    public IncidenciasTutoria(IncidenciasTutoriaPK incidenciasTutoriaPK) {
        this.incidenciasTutoriaPK = incidenciasTutoriaPK;
    }

    public IncidenciasTutoria(IncidenciasTutoriaPK incidenciasTutoriaPK, Date fecha, int codigoIncidencia, int codigoDocente, String estado) {
        this.incidenciasTutoriaPK = incidenciasTutoriaPK;
        this.fecha = fecha;
        this.codigoIncidencia = codigoIncidencia;
        this.codigoDocente = codigoDocente;
        this.estado = estado;
    }

    public IncidenciasTutoria(String codigoSede, String codigoUniversitario, String anio, Character semestre, int item) {
        this.incidenciasTutoriaPK = new IncidenciasTutoriaPK(codigoSede, codigoUniversitario, anio, semestre, item);
    }

    public IncidenciasTutoriaPK getIncidenciasTutoriaPK() {
        return incidenciasTutoriaPK;
    }

    public void setIncidenciasTutoriaPK(IncidenciasTutoriaPK incidenciasTutoriaPK) {
        this.incidenciasTutoriaPK = incidenciasTutoriaPK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCodigoIncidencia() {
        return codigoIncidencia;
    }

    public void setCodigoIncidencia(int codigoIncidencia) {
        this.codigoIncidencia = codigoIncidencia;
    }

    public int getCodigoDocente() {
        return codigoDocente;
    }

    public void setCodigoDocente(int codigoDocente) {
        this.codigoDocente = codigoDocente;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
        hash += (incidenciasTutoriaPK != null ? incidenciasTutoriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncidenciasTutoria)) {
            return false;
        }
        IncidenciasTutoria other = (IncidenciasTutoria) object;
        if ((this.incidenciasTutoriaPK == null && other.incidenciasTutoriaPK != null) || (this.incidenciasTutoriaPK != null && !this.incidenciasTutoriaPK.equals(other.incidenciasTutoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.IncidenciasTutoria[ incidenciasTutoriaPK=" + incidenciasTutoriaPK + " ]";
    }
    
}
