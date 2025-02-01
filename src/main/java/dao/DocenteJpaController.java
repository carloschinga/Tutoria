/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dto.Docente;
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
public class DocenteJpaController extends JpaPadre {

    public DocenteJpaController(String empresa) {
        super(empresa);
    }

    public String BuscarFacultad(String codigoFacultad) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("SELECT d.CodigoDocente, s.APaterno + ' ' + s.AMaterno + ' ' + s.Nombre1 + ' ' + s.Nombre2 AS nombrecompleto ");
            queryString.append("FROM Docente d ");
            queryString.append("INNER JOIN Sujeto s ON d.CodigoSujeto = s.CodigoSujeto ");
            queryString.append("INNER JOIN DepartamentoAcademico de ON d.CodigoDptoAcademico = de.CodigoDptoAcademico ");
            queryString.append("WHERE d.Estado = 1 AND de.CodigoFacultad = ? ");
            queryString.append("ORDER BY s.APaterno, s.AMaterno, s.Nombre1, s.Nombre2");
            Query query = em.createNativeQuery(queryString.toString());
            query.setParameter(1, codigoFacultad);
            List<Object[]> resultados = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Object[] fila : resultados) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("CodigoDocente", fila[0]);
                jsonObj.put("Nombres", fila[1]);
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

    public void create(Docente docente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(docente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Docente docente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            docente = em.merge(docente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = docente.getCodigoDocente();
                if (findDocente(id) == null) {
                    throw new NonexistentEntityException("The docente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docente docente;
            try {
                docente = em.getReference(Docente.class, id);
                docente.getCodigoDocente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The docente with id " + id + " no longer exists.", enfe);
            }
            em.remove(docente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Docente> findDocenteEntities() {
        return findDocenteEntities(true, -1, -1);
    }

    public List<Docente> findDocenteEntities(int maxResults, int firstResult) {
        return findDocenteEntities(false, maxResults, firstResult);
    }

    private List<Docente> findDocenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Docente.class));
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

    public Docente findDocente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Docente.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Docente> rt = cq.from(Docente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
