/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dto.TipoActividad;
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
public class TipoActividadJpaController extends JpaPadre {

    public TipoActividadJpaController(String empresa) {
        super(empresa);
    }
    public String listar() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("TipoActividad.findAll");
            List<Object[]> resultados = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Object[] fila : resultados) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("codtipact", fila[0]);
                jsonObj.put("nombtipact", fila[1]);
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
    

    public void create(TipoActividad tipoActividad) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoActividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoActividad tipoActividad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoActividad = em.merge(tipoActividad);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoActividad.getCodigoTipoActividad();
                if (findTipoActividad(id) == null) {
                    throw new NonexistentEntityException("The tipoActividad with id " + id + " no longer exists.");
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
            TipoActividad tipoActividad;
            try {
                tipoActividad = em.getReference(TipoActividad.class, id);
                tipoActividad.getCodigoTipoActividad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoActividad with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoActividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoActividad> findTipoActividadEntities() {
        return findTipoActividadEntities(true, -1, -1);
    }

    public List<TipoActividad> findTipoActividadEntities(int maxResults, int firstResult) {
        return findTipoActividadEntities(false, maxResults, firstResult);
    }

    private List<TipoActividad> findTipoActividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoActividad.class));
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

    public TipoActividad findTipoActividad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoActividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoActividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoActividad> rt = cq.from(TipoActividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
