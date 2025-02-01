/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.ActividadTutoria;
import dto.ActividadTutoriaPK;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author shaho
 */
public class ActividadTutoriaJpaController extends JpaPadre {

    public ActividadTutoriaJpaController(String empresa) {
        super(empresa);
    }

    public int obtenerUltItm(int codigoDocente, String anio,Character semestre) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Integer> query = em.createNamedQuery("ActividadTutoria.ultimasesion", Integer.class);
            query.setParameter("codigoDocente", codigoDocente);
            query.setParameter("anio", anio);
            query.setParameter("semestre", semestre);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        } catch (Exception e) {
            return -1;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public String Listar(int codigoDocente, String anio,Character semestre) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery("select a.*,t.NombreActividad from ActividadTutoria a inner join TipoActividad t on t.CodigoTipoActividad=a.CodigoTipoActividad where a.CodigoDocente=? and a.Estado='S' and a.Anio=? and Semestre=?");
            query.setParameter(1, codigoDocente);
            query.setParameter(2, anio);
            query.setParameter(3, semestre);
            List<Object[]> resultados = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Object[] fila : resultados) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("coddocente", fila[0]);
                jsonObj.put("anio", fila[1]);
                jsonObj.put("semestre", fila[2]);
                jsonObj.put("sesion", fila[3]);
                jsonObj.put("actividad", fila[4]);
                jsonObj.put("codtipoactividad", fila[5]);
                jsonObj.put("fecha", fila[6]);
                jsonObj.put("lugar", fila[7]);
                jsonObj.put("nombretipoactividad", fila[9]);
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
    public void create(ActividadTutoria actividadTutoria) throws PreexistingEntityException, Exception {
        if (actividadTutoria.getActividadTutoriaPK() == null) {
            actividadTutoria.setActividadTutoriaPK(new ActividadTutoriaPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(actividadTutoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActividadTutoria(actividadTutoria.getActividadTutoriaPK()) != null) {
                throw new PreexistingEntityException("ActividadTutoria " + actividadTutoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActividadTutoria actividadTutoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            actividadTutoria = em.merge(actividadTutoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ActividadTutoriaPK id = actividadTutoria.getActividadTutoriaPK();
                if (findActividadTutoria(id) == null) {
                    throw new NonexistentEntityException("The actividadTutoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ActividadTutoriaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadTutoria actividadTutoria;
            try {
                actividadTutoria = em.getReference(ActividadTutoria.class, id);
                actividadTutoria.getActividadTutoriaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividadTutoria with id " + id + " no longer exists.", enfe);
            }
            em.remove(actividadTutoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActividadTutoria> findActividadTutoriaEntities() {
        return findActividadTutoriaEntities(true, -1, -1);
    }

    public List<ActividadTutoria> findActividadTutoriaEntities(int maxResults, int firstResult) {
        return findActividadTutoriaEntities(false, maxResults, firstResult);
    }

    private List<ActividadTutoria> findActividadTutoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActividadTutoria.class));
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

    public ActividadTutoria findActividadTutoria(ActividadTutoriaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActividadTutoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadTutoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActividadTutoria> rt = cq.from(ActividadTutoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
