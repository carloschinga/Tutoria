/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dto.TipoIncidencia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author shaho
 */
public class TipoIncidenciaJpaController extends JpaPadre{

    public TipoIncidenciaJpaController(String empresa) {
        super(empresa);
    }

    public String listar() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("TipoIncidencia.findAll");
            List<Object[]> resultados = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Object[] fila : resultados) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("codigo", fila[0]);
                jsonObj.put("incidencia", fila[1]);
                jsonArray.put(jsonObj);
            }
            return jsonArray.toString();

        } catch (JSONException e) {
            return "{\"Resultado\":\"Error\",\"mensaje\":\"" + e.getMessage() + "\"}";
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void create(TipoIncidencia tipoIncidencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoIncidencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoIncidencia tipoIncidencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoIncidencia = em.merge(tipoIncidencia);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoIncidencia.getCodigoIncidencia();
                if (findTipoIncidencia(id) == null) {
                    throw new NonexistentEntityException("The tipoIncidencia with id " + id + " no longer exists.");
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
            TipoIncidencia tipoIncidencia;
            try {
                tipoIncidencia = em.getReference(TipoIncidencia.class, id);
                tipoIncidencia.getCodigoIncidencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoIncidencia with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoIncidencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoIncidencia> findTipoIncidenciaEntities() {
        return findTipoIncidenciaEntities(true, -1, -1);
    }

    public List<TipoIncidencia> findTipoIncidenciaEntities(int maxResults, int firstResult) {
        return findTipoIncidenciaEntities(false, maxResults, firstResult);
    }

    private List<TipoIncidencia> findTipoIncidenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoIncidencia.class));
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

    public TipoIncidencia findTipoIncidencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoIncidencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoIncidenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoIncidencia> rt = cq.from(TipoIncidencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
