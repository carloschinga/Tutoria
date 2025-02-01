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
@Table(name = "Alumnos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumnos.findAll", query = "SELECT a FROM Alumnos a"),
    @NamedQuery(name = "Alumnos.findByCodigoSede", query = "SELECT a FROM Alumnos a WHERE a.alumnosPK.codigoSede = :codigoSede"),
    @NamedQuery(name = "Alumnos.findByCodigoUniversitario", query = "SELECT a FROM Alumnos a WHERE a.alumnosPK.codigoUniversitario = :codigoUniversitario"),
    @NamedQuery(name = "Alumnos.findByApellidos", query = "SELECT a FROM Alumnos a WHERE a.apellidos = :apellidos"),
    @NamedQuery(name = "Alumnos.findByNombres", query = "SELECT a FROM Alumnos a WHERE a.nombres = :nombres"),
    @NamedQuery(name = "Alumnos.findByDireccion", query = "SELECT a FROM Alumnos a WHERE a.direccion = :direccion"),
    @NamedQuery(name = "Alumnos.findByTelefono", query = "SELECT a FROM Alumnos a WHERE a.telefono = :telefono"),
    @NamedQuery(name = "Alumnos.findByEmail", query = "SELECT a FROM Alumnos a WHERE a.email = :email"),
    @NamedQuery(name = "Alumnos.findByNumeroDocumento", query = "SELECT a FROM Alumnos a WHERE a.numeroDocumento = :numeroDocumento"),
    @NamedQuery(name = "Alumnos.findByFechaNacimiento", query = "SELECT a FROM Alumnos a WHERE a.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Alumnos.findBySexo", query = "SELECT a FROM Alumnos a WHERE a.sexo = :sexo"),
    @NamedQuery(name = "Alumnos.findByCondicionAlumno", query = "SELECT a FROM Alumnos a WHERE a.condicionAlumno = :condicionAlumno"),
    @NamedQuery(name = "Alumnos.findByModalidadIngreso", query = "SELECT a FROM Alumnos a WHERE a.modalidadIngreso = :modalidadIngreso"),
    @NamedQuery(name = "Alumnos.findByAnioIngreso", query = "SELECT a FROM Alumnos a WHERE a.anioIngreso = :anioIngreso"),
    @NamedQuery(name = "Alumnos.findBySemestreIngreso", query = "SELECT a FROM Alumnos a WHERE a.semestreIngreso = :semestreIngreso"),
    @NamedQuery(name = "Alumnos.findByNumeroPlan", query = "SELECT a FROM Alumnos a WHERE a.numeroPlan = :numeroPlan"),
    @NamedQuery(name = "Alumnos.findByCodigoEscuela", query = "SELECT a FROM Alumnos a WHERE a.codigoEscuela = :codigoEscuela"),
    @NamedQuery(name = "Alumnos.findByEmailInstitucional", query = "SELECT a FROM Alumnos a WHERE a.emailInstitucional = :emailInstitucional"),
    @NamedQuery(name = "Alumnos.findByCodigoEstado", query = "SELECT a FROM Alumnos a WHERE a.codigoEstado = :codigoEstado"),
    @NamedQuery(name = "Alumnos.findBySituacionAcademica", query = "SELECT a FROM Alumnos a WHERE a.situacionAcademica = :situacionAcademica"),
    @NamedQuery(name = "Alumnos.findByCodigoDataBase", query = "SELECT a FROM Alumnos a WHERE a.codigoDataBase = :codigoDataBase"),
    @NamedQuery(name = "Alumnos.findByBDSede", query = "SELECT a FROM Alumnos a WHERE a.bDSede = :bDSede"),
    @NamedQuery(name = "Alumnos.findBySedeActual", query = "SELECT a FROM Alumnos a WHERE a.sedeActual = :sedeActual"),
    @NamedQuery(name = "Alumnos.findByFechaIngreso", query = "SELECT a FROM Alumnos a WHERE a.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Alumnos.findByPuntajeIngreso", query = "SELECT a FROM Alumnos a WHERE a.puntajeIngreso = :puntajeIngreso"),
    @NamedQuery(name = "Alumnos.findByLlevoNivelacion", query = "SELECT a FROM Alumnos a WHERE a.llevoNivelacion = :llevoNivelacion")})
public class Alumnos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AlumnosPK alumnosPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Apellidos")
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Nombres")
    private String nombres;
    @Size(max = 100)
    @Column(name = "Direccion")
    private String direccion;
    @Size(max = 20)
    @Column(name = "Telefono")
    private String telefono;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "Email")
    private String email;
    @Size(max = 20)
    @Column(name = "NumeroDocumento")
    private String numeroDocumento;
    @Size(max = 10)
    @Column(name = "FechaNacimiento")
    private String fechaNacimiento;
    @Column(name = "Sexo")
    private Character sexo;
    @Column(name = "CondicionAlumno")
    private Short condicionAlumno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "ModalidadIngreso")
    private String modalidadIngreso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "AnioIngreso")
    private String anioIngreso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SemestreIngreso")
    private Character semestreIngreso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "NumeroPlan")
    private String numeroPlan;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "CodigoEscuela")
    private String codigoEscuela;
    @Size(max = 50)
    @Column(name = "EmailInstitucional")
    private String emailInstitucional;
    @Column(name = "CodigoEstado")
    private Short codigoEstado;
    @Size(max = 30)
    @Column(name = "SituacionAcademica")
    private String situacionAcademica;
    @Size(max = 4)
    @Column(name = "CodigoDataBase")
    private String codigoDataBase;
    @Size(max = 2)
    @Column(name = "BDSede")
    private String bDSede;
    @Size(max = 2)
    @Column(name = "SedeActual")
    private String sedeActual;
    @Column(name = "FechaIngreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Size(max = 50)
    @Column(name = "PuntajeIngreso")
    private String puntajeIngreso;
    @Size(max = 15)
    @Column(name = "LlevoNivelacion")
    private String llevoNivelacion;

    public Alumnos() {
    }

    public Alumnos(AlumnosPK alumnosPK) {
        this.alumnosPK = alumnosPK;
    }

    public Alumnos(AlumnosPK alumnosPK, String apellidos, String nombres, String modalidadIngreso, String anioIngreso, Character semestreIngreso, String numeroPlan, String codigoEscuela) {
        this.alumnosPK = alumnosPK;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.modalidadIngreso = modalidadIngreso;
        this.anioIngreso = anioIngreso;
        this.semestreIngreso = semestreIngreso;
        this.numeroPlan = numeroPlan;
        this.codigoEscuela = codigoEscuela;
    }

    public Alumnos(String codigoSede, String codigoUniversitario) {
        this.alumnosPK = new AlumnosPK(codigoSede, codigoUniversitario);
    }

    public AlumnosPK getAlumnosPK() {
        return alumnosPK;
    }

    public void setAlumnosPK(AlumnosPK alumnosPK) {
        this.alumnosPK = alumnosPK;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Short getCondicionAlumno() {
        return condicionAlumno;
    }

    public void setCondicionAlumno(Short condicionAlumno) {
        this.condicionAlumno = condicionAlumno;
    }

    public String getModalidadIngreso() {
        return modalidadIngreso;
    }

    public void setModalidadIngreso(String modalidadIngreso) {
        this.modalidadIngreso = modalidadIngreso;
    }

    public String getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(String anioIngreso) {
        this.anioIngreso = anioIngreso;
    }

    public Character getSemestreIngreso() {
        return semestreIngreso;
    }

    public void setSemestreIngreso(Character semestreIngreso) {
        this.semestreIngreso = semestreIngreso;
    }

    public String getNumeroPlan() {
        return numeroPlan;
    }

    public void setNumeroPlan(String numeroPlan) {
        this.numeroPlan = numeroPlan;
    }

    public String getCodigoEscuela() {
        return codigoEscuela;
    }

    public void setCodigoEscuela(String codigoEscuela) {
        this.codigoEscuela = codigoEscuela;
    }

    public String getEmailInstitucional() {
        return emailInstitucional;
    }

    public void setEmailInstitucional(String emailInstitucional) {
        this.emailInstitucional = emailInstitucional;
    }

    public Short getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(Short codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getSituacionAcademica() {
        return situacionAcademica;
    }

    public void setSituacionAcademica(String situacionAcademica) {
        this.situacionAcademica = situacionAcademica;
    }

    public String getCodigoDataBase() {
        return codigoDataBase;
    }

    public void setCodigoDataBase(String codigoDataBase) {
        this.codigoDataBase = codigoDataBase;
    }

    public String getBDSede() {
        return bDSede;
    }

    public void setBDSede(String bDSede) {
        this.bDSede = bDSede;
    }

    public String getSedeActual() {
        return sedeActual;
    }

    public void setSedeActual(String sedeActual) {
        this.sedeActual = sedeActual;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getPuntajeIngreso() {
        return puntajeIngreso;
    }

    public void setPuntajeIngreso(String puntajeIngreso) {
        this.puntajeIngreso = puntajeIngreso;
    }

    public String getLlevoNivelacion() {
        return llevoNivelacion;
    }

    public void setLlevoNivelacion(String llevoNivelacion) {
        this.llevoNivelacion = llevoNivelacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (alumnosPK != null ? alumnosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alumnos)) {
            return false;
        }
        Alumnos other = (Alumnos) object;
        if ((this.alumnosPK == null && other.alumnosPK != null) || (this.alumnosPK != null && !this.alumnosPK.equals(other.alumnosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Alumnos[ alumnosPK=" + alumnosPK + " ]";
    }
    
}
