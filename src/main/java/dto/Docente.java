/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "Docente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Docente.findAll", query = "SELECT d FROM Docente d"),
    @NamedQuery(name = "Docente.findByCodigoDocente", query = "SELECT d FROM Docente d WHERE d.codigoDocente = :codigoDocente"),
    @NamedQuery(name = "Docente.findByCodigoSujeto", query = "SELECT d FROM Docente d WHERE d.codigoSujeto = :codigoSujeto"),
    @NamedQuery(name = "Docente.findByCodigoPersonal", query = "SELECT d FROM Docente d WHERE d.codigoPersonal = :codigoPersonal"),
    @NamedQuery(name = "Docente.findByCondicion", query = "SELECT d FROM Docente d WHERE d.condicion = :condicion"),
    @NamedQuery(name = "Docente.findByHorasAsignadas", query = "SELECT d FROM Docente d WHERE d.horasAsignadas = :horasAsignadas"),
    @NamedQuery(name = "Docente.findByProfesionDocente", query = "SELECT d FROM Docente d WHERE d.profesionDocente = :profesionDocente"),
    @NamedQuery(name = "Docente.findByEstado", query = "SELECT d FROM Docente d WHERE d.estado = :estado"),
    @NamedQuery(name = "Docente.findByCodigoTipoSituacion", query = "SELECT d FROM Docente d WHERE d.codigoTipoSituacion = :codigoTipoSituacion"),
    @NamedQuery(name = "Docente.findByObservaciones", query = "SELECT d FROM Docente d WHERE d.observaciones = :observaciones"),
    @NamedQuery(name = "Docente.findByFechaUModificacion", query = "SELECT d FROM Docente d WHERE d.fechaUModificacion = :fechaUModificacion"),
    @NamedQuery(name = "Docente.findByCargo", query = "SELECT d FROM Docente d WHERE d.cargo = :cargo"),
    @NamedQuery(name = "Docente.findByFichaDina", query = "SELECT d FROM Docente d WHERE d.fichaDina = :fichaDina"),
    @NamedQuery(name = "Docente.findByTipocontrato", query = "SELECT d FROM Docente d WHERE d.tipocontrato = :tipocontrato"),
    @NamedQuery(name = "Docente.findByCodDocSunedu", query = "SELECT d FROM Docente d WHERE d.codDocSunedu = :codDocSunedu")})
public class Docente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CodigoDocente")
    private Integer codigoDocente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CodigoSujeto")
    private long codigoSujeto;
    @Size(max = 6)
    @Column(name = "CodigoPersonal")
    private String codigoPersonal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Condicion")
    private Character condicion;
    @Size(max = 2)
    @Column(name = "HorasAsignadas")
    private String horasAsignadas;
    @Size(max = 50)
    @Column(name = "ProfesionDocente")
    private String profesionDocente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Estado")
    private boolean estado;
    @Column(name = "CodigoTipoSituacion")
    private Short codigoTipoSituacion;
    @Size(max = 250)
    @Column(name = "Observaciones")
    private String observaciones;
    @Column(name = "FechaUModificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaUModificacion;
    @Size(max = 50)
    @Column(name = "Cargo")
    private String cargo;
    @Size(max = 250)
    @Column(name = "FichaDina")
    private String fichaDina;
    @Size(max = 3)
    @Column(name = "tipocontrato")
    private String tipocontrato;
    @Size(max = 7)
    @Column(name = "CodDocSunedu")
    private String codDocSunedu;

    public Docente() {
    }

    public Docente(Integer codigoDocente) {
        this.codigoDocente = codigoDocente;
    }

    public Docente(Integer codigoDocente, long codigoSujeto, Character condicion, boolean estado) {
        this.codigoDocente = codigoDocente;
        this.codigoSujeto = codigoSujeto;
        this.condicion = condicion;
        this.estado = estado;
    }

    public Integer getCodigoDocente() {
        return codigoDocente;
    }

    public void setCodigoDocente(Integer codigoDocente) {
        this.codigoDocente = codigoDocente;
    }

    public long getCodigoSujeto() {
        return codigoSujeto;
    }

    public void setCodigoSujeto(long codigoSujeto) {
        this.codigoSujeto = codigoSujeto;
    }

    public String getCodigoPersonal() {
        return codigoPersonal;
    }

    public void setCodigoPersonal(String codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }

    public Character getCondicion() {
        return condicion;
    }

    public void setCondicion(Character condicion) {
        this.condicion = condicion;
    }

    public String getHorasAsignadas() {
        return horasAsignadas;
    }

    public void setHorasAsignadas(String horasAsignadas) {
        this.horasAsignadas = horasAsignadas;
    }

    public String getProfesionDocente() {
        return profesionDocente;
    }

    public void setProfesionDocente(String profesionDocente) {
        this.profesionDocente = profesionDocente;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Short getCodigoTipoSituacion() {
        return codigoTipoSituacion;
    }

    public void setCodigoTipoSituacion(Short codigoTipoSituacion) {
        this.codigoTipoSituacion = codigoTipoSituacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaUModificacion() {
        return fechaUModificacion;
    }

    public void setFechaUModificacion(Date fechaUModificacion) {
        this.fechaUModificacion = fechaUModificacion;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFichaDina() {
        return fichaDina;
    }

    public void setFichaDina(String fichaDina) {
        this.fichaDina = fichaDina;
    }

    public String getTipocontrato() {
        return tipocontrato;
    }

    public void setTipocontrato(String tipocontrato) {
        this.tipocontrato = tipocontrato;
    }

    public String getCodDocSunedu() {
        return codDocSunedu;
    }

    public void setCodDocSunedu(String codDocSunedu) {
        this.codDocSunedu = codDocSunedu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoDocente != null ? codigoDocente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Docente)) {
            return false;
        }
        Docente other = (Docente) object;
        if ((this.codigoDocente == null && other.codigoDocente != null) || (this.codigoDocente != null && !this.codigoDocente.equals(other.codigoDocente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Docente[ codigoDocente=" + codigoDocente + " ]";
    }
    
}
