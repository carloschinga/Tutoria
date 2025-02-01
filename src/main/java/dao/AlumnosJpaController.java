/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.Alumnos;
import dto.AlumnosPK;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author shaho
 */
public class AlumnosJpaController extends JpaPadre {

    public AlumnosJpaController(String empresa) {
        super(empresa);
    }

    public String BuscarEscuelaCiclo(String codigoEscuela, String ciclo) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("SELECT al.CodigoUniversitario, al.CodigoSede, al.Nombres, al.Apellidos, ");
            queryString.append("t.CodigoDocente, s.APaterno + ' ' + s.AMaterno + ' ' + s.Nombre1 + ' ' + s.Nombre2 AS nombrecompleto ");
            queryString.append("FROM Matriculas m ");
            queryString.append("INNER JOIN SemestreAcademico a ON a.Anio = m.Anio AND a.Semestre = m.Semestre AND a.Activo = 1 ");
            queryString.append("INNER JOIN Alumnos al ON al.CodigoUniversitario = m.CodigoUniversitario AND al.CodigoSede = m.CodigoSede ");
            queryString.append("LEFT JOIN Tutoria t ON t.CodigoSede = al.CodigoSede AND t.CodigoUniversitario = al.CodigoUniversitario ");
            queryString.append("AND a.Anio = t.Anio AND a.Semestre = t.Semestre AND t.Estado='S' ");
            queryString.append("LEFT JOIN Docente d ON d.CodigoDocente = t.CodigoDocente AND d.Estado = 1 ");
            queryString.append("LEFT JOIN Sujeto s ON d.CodigoSujeto = s.CodigoSujeto ");
            queryString.append("WHERE al.CodigoEscuela = ? AND m.Ciclo = ? ");
            queryString.append("ORDER BY al.Apellidos, al.Nombres");
            Query query = em.createNativeQuery(queryString.toString());
            query.setParameter(1, codigoEscuela);
            query.setParameter(2, ciclo);
            List<Object[]> resultados = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Object[] fila : resultados) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("CodigoUniversitario", fila[0]);
                jsonObj.put("CodigoSede", fila[1]);
                jsonObj.put("Nombres", fila[2]);
                jsonObj.put("Apellidos", fila[3]);
                jsonObj.put("codigodocente", fila[4]);
                jsonObj.put("nombredocente", fila[5]);
                jsonArray.put(jsonObj);
            }
            return jsonArray.toString();

        } catch (Exception e) {
            return "{\"Resultado\":\"Error\",\"mensaje\":\"" + e.getMessage() + "\"}";
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void create(Alumnos alumnos) throws PreexistingEntityException, Exception {
        if (alumnos.getAlumnosPK() == null) {
            alumnos.setAlumnosPK(new AlumnosPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(alumnos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlumnos(alumnos.getAlumnosPK()) != null) {
                throw new PreexistingEntityException("Alumnos " + alumnos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumnos alumnos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            alumnos = em.merge(alumnos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AlumnosPK id = alumnos.getAlumnosPK();
                if (findAlumnos(id) == null) {
                    throw new NonexistentEntityException("The alumnos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AlumnosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos alumnos;
            try {
                alumnos = em.getReference(Alumnos.class, id);
                alumnos.getAlumnosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumnos with id " + id + " no longer exists.", enfe);
            }
            em.remove(alumnos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumnos> findAlumnosEntities() {
        return findAlumnosEntities(true, -1, -1);
    }

    public List<Alumnos> findAlumnosEntities(int maxResults, int firstResult) {
        return findAlumnosEntities(false, maxResults, firstResult);
    }

    private List<Alumnos> findAlumnosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumnos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Alumnos findAlumnos(AlumnosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumnos> rt = cq.from(Alumnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
