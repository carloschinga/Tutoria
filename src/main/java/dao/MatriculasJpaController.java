/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.Matriculas;
import dto.MatriculasPK;
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
public class MatriculasJpaController extends JpaPadre {

    public MatriculasJpaController(String empresa) {
        super(empresa);
    }

    public String buscarCiclosMatriculados(String codigoFacultad) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("SELECT DISTINCT m.Ciclo ");
            queryString.append("FROM Matriculas m ");
            queryString.append("INNER JOIN SemestreAcademico a ON a.Anio = m.Anio AND a.Semestre = m.Semestre AND a.Activo = 1 ");
            queryString.append("INNER JOIN Alumnos al ON al.CodigoUniversitario = m.CodigoUniversitario AND al.CodigoSede = m.CodigoSede ");
            queryString.append("INNER JOIN Escuelas e ON e.CodigoEscuela = al.CodigoEscuela ");
            queryString.append("WHERE e.CodigoFacultad = ? ");
            queryString.append("ORDER BY m.Ciclo");

            Query query = em.createNativeQuery(queryString.toString());
            query.setParameter(1, codigoFacultad);
            List<String> resultados = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (String fila : resultados) {
                jsonArray.put(fila);
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
    public static void main(String[] args) {
        MatriculasJpaController dao=new MatriculasJpaController("a");
        System.out.println(dao.buscarCiclosMatriculados("03"));
    }
    public void create(Matriculas matriculas) throws PreexistingEntityException, Exception {
        if (matriculas.getMatriculasPK() == null) {
            matriculas.setMatriculasPK(new MatriculasPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(matriculas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMatriculas(matriculas.getMatriculasPK()) != null) {
                throw new PreexistingEntityException("Matriculas " + matriculas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Matriculas matriculas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            matriculas = em.merge(matriculas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MatriculasPK id = matriculas.getMatriculasPK();
                if (findMatriculas(id) == null) {
                    throw new NonexistentEntityException("The matriculas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MatriculasPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matriculas matriculas;
            try {
                matriculas = em.getReference(Matriculas.class, id);
                matriculas.getMatriculasPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matriculas with id " + id + " no longer exists.", enfe);
            }
            em.remove(matriculas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Matriculas> findMatriculasEntities() {
        return findMatriculasEntities(true, -1, -1);
    }

    public List<Matriculas> findMatriculasEntities(int maxResults, int firstResult) {
        return findMatriculasEntities(false, maxResults, firstResult);
    }

    private List<Matriculas> findMatriculasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Matriculas.class));
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

    public Matriculas findMatriculas(MatriculasPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Matriculas.class, id);
        } finally {
            em.close();
        }
    }

    public int getMatriculasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Matriculas> rt = cq.from(Matriculas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
