/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.Escuelas;
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
public class EscuelasJpaController extends JpaPadre {

    public EscuelasJpaController(String empresa) {
        super(empresa);
    }

    public String buscarFacultad(String codigoFacultad) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Escuelas.findByCodigoFacultad");
            query.setParameter("codigoFacultad", codigoFacultad);
            List<Object[]> resultados = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Object[] fila : resultados) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("codigoEscuela", fila[0]);
                jsonObj.put("escuela", fila[1]);
                jsonObj.put("escuelaAbrev", fila[2]);
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
    public static void main(String[] args) {
        EscuelasJpaController dao= new EscuelasJpaController("a");
        System.out.println(dao.buscarFacultad("03"));
    }
    public void create(Escuelas escuelas) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(escuelas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEscuelas(escuelas.getCodigoEscuela()) != null) {
                throw new PreexistingEntityException("Escuelas " + escuelas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Escuelas escuelas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            escuelas = em.merge(escuelas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = escuelas.getCodigoEscuela();
                if (findEscuelas(id) == null) {
                    throw new NonexistentEntityException("The escuelas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Escuelas escuelas;
            try {
                escuelas = em.getReference(Escuelas.class, id);
                escuelas.getCodigoEscuela();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The escuelas with id " + id + " no longer exists.", enfe);
            }
            em.remove(escuelas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Escuelas> findEscuelasEntities() {
        return findEscuelasEntities(true, -1, -1);
    }

    public List<Escuelas> findEscuelasEntities(int maxResults, int firstResult) {
        return findEscuelasEntities(false, maxResults, firstResult);
    }

    private List<Escuelas> findEscuelasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Escuelas.class));
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

    public Escuelas findEscuelas(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Escuelas.class, id);
        } finally {
            em.close();
        }
    }

    public int getEscuelasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Escuelas> rt = cq.from(Escuelas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
