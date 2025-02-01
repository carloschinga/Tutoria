/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "Facultades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facultades.findAll", query = "SELECT f FROM Facultades f"),
    @NamedQuery(name = "Facultades.findByCodigoFacultad", query = "SELECT f FROM Facultades f WHERE f.codigoFacultad = :codigoFacultad"),
    @NamedQuery(name = "Facultades.findByFacultad", query = "SELECT f FROM Facultades f WHERE f.facultad = :facultad"),
    @NamedQuery(name = "Facultades.findByCodigoTributo", query = "SELECT f FROM Facultades f WHERE f.codigoTributo = :codigoTributo"),
    @NamedQuery(name = "Facultades.findByCodigoTCarnet", query = "SELECT f FROM Facultades f WHERE f.codigoTCarnet = :codigoTCarnet"),
    @NamedQuery(name = "Facultades.findByCodFacSunedu", query = "SELECT f FROM Facultades f WHERE f.codFacSunedu = :codFacSunedu"),
    @NamedQuery(name = "Facultades.findByPresencial", query = "SELECT f FROM Facultades f WHERE f.presencial = :presencial"),
    @NamedQuery(name = "Facultades.findByPresencial2", query = "SELECT f FROM Facultades f WHERE f.presencial2 = :presencial2")})
public class Facultades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "CodigoFacultad")
    private String codigoFacultad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Facultad")
    private String facultad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "CodigoTributo")
    private String codigoTributo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "CodigoTCarnet")
    private String codigoTCarnet;
    @Size(max = 5)
    @Column(name = "CodFacSunedu")
    private String codFacSunedu;
    @Column(name = "Presencial")
    private Character presencial;
    @Column(name = "Presencial2")
    private Character presencial2;

    public Facultades() {
    }

    public Facultades(String codigoFacultad) {
        this.codigoFacultad = codigoFacultad;
    }

    public Facultades(String codigoFacultad, String facultad, String codigoTributo, String codigoTCarnet) {
        this.codigoFacultad = codigoFacultad;
        this.facultad = facultad;
        this.codigoTributo = codigoTributo;
        this.codigoTCarnet = codigoTCarnet;
    }

    public String getCodigoFacultad() {
        return codigoFacultad;
    }

    public void setCodigoFacultad(String codigoFacultad) {
        this.codigoFacultad = codigoFacultad;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getCodigoTributo() {
        return codigoTributo;
    }

    public void setCodigoTributo(String codigoTributo) {
        this.codigoTributo = codigoTributo;
    }

    public String getCodigoTCarnet() {
        return codigoTCarnet;
    }

    public void setCodigoTCarnet(String codigoTCarnet) {
        this.codigoTCarnet = codigoTCarnet;
    }

    public String getCodFacSunedu() {
        return codFacSunedu;
    }

    public void setCodFacSunedu(String codFacSunedu) {
        this.codFacSunedu = codFacSunedu;
    }

    public Character getPresencial() {
        return presencial;
    }

    public void setPresencial(Character presencial) {
        this.presencial = presencial;
    }

    public Character getPresencial2() {
        return presencial2;
    }

    public void setPresencial2(Character presencial2) {
        this.presencial2 = presencial2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoFacultad != null ? codigoFacultad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facultades)) {
            return false;
        }
        Facultades other = (Facultades) object;
        if ((this.codigoFacultad == null && other.codigoFacultad != null) || (this.codigoFacultad != null && !this.codigoFacultad.equals(other.codigoFacultad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Facultades[ codigoFacultad=" + codigoFacultad + " ]";
    }
    
}
