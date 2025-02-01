/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.IncidenciasTutoria;
import dto.IncidenciasTutoriaPK;
import java.util.List;
import javax.persistence.EntityManager;
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
public class IncidenciasTutoriaJpaController extends JpaPadre {

    public IncidenciasTutoriaJpaController(String empresa) {
        super(empresa);
    }

    public String ListarIncidencias(String CodigoUniversitario, String CodigoSede) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("SELECT Fecha, i.Anio, i.Semestre, Observacion, s.Activo, t.Incidencia, t.CodigoIncidencia,i.Item ");
            queryString.append("FROM IncidenciasTutoria i ");
            queryString.append("LEFT JOIN SemestreAcademico s ON s.Semestre = i.Semestre AND s.Anio = i.Anio AND s.Activo = 1 ");
            queryString.append("INNER JOIN TipoIncidencia t ON t.CodigoIncidencia = i.CodigoIncidencia ");
            queryString.append("WHERE i.CodigoSede = ? AND i.CodigoUniversitario = ? and i.Estado='S' ");
            queryString.append("ORDER BY i.Fecha");

            Query query = em.createNativeQuery(queryString.toString());
            query.setParameter(1, CodigoSede);
            query.setParameter(2, CodigoUniversitario);

            List<Object[]> resultados = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Object[] fila : resultados) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("fecha", fila[0]);
                jsonObj.put("anio", fila[1]);
                jsonObj.put("semestre", fila[2]);
                jsonObj.put("observacion", fila[3]);
                jsonObj.put("modificable", fila[4]);
                jsonObj.put("tipo", fila[5]);
                jsonObj.put("codigotipo", fila[6]);
                jsonObj.put("item", fila[6]);
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

    public int obtenerUltItm(String codigoUniversitario, String codigoSede, String anio, Character semestre) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Integer> query = em.createNamedQuery("IncidenciasTutoria.ultimasesion", Integer.class);
            query.setParameter("codigoUniversitario", codigoUniversitario);
            query.setParameter("codigoSede", codigoSede);
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

    public void create(IncidenciasTutoria incidenciasTutoria) throws PreexistingEntityException, Exception {
        if (incidenciasTutoria.getIncidenciasTutoriaPK() == null) {
            incidenciasTutoria.setIncidenciasTutoriaPK(new IncidenciasTutoriaPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(incidenciasTutoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIncidenciasTutoria(incidenciasTutoria.getIncidenciasTutoriaPK()) != null) {
                throw new PreexistingEntityException("IncidenciasTutoria " + incidenciasTutoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IncidenciasTutoria incidenciasTutoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            incidenciasTutoria = em.merge(incidenciasTutoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                IncidenciasTutoriaPK id = incidenciasTutoria.getIncidenciasTutoriaPK();
                if (findIncidenciasTutoria(id) == null) {
                    throw new NonexistentEntityException("The incidenciasTutoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(IncidenciasTutoriaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IncidenciasTutoria incidenciasTutoria;
            try {
                incidenciasTutoria = em.getReference(IncidenciasTutoria.class, id);
                incidenciasTutoria.getIncidenciasTutoriaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The incidenciasTutoria with id " + id + " no longer exists.", enfe);
            }
            em.remove(incidenciasTutoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IncidenciasTutoria> findIncidenciasTutoriaEntities() {
        return findIncidenciasTutoriaEntities(true, -1, -1);
    }

    public List<IncidenciasTutoria> findIncidenciasTutoriaEntities(int maxResults, int firstResult) {
        return findIncidenciasTutoriaEntities(false, maxResults, firstResult);
    }

    private List<IncidenciasTutoria> findIncidenciasTutoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IncidenciasTutoria.class));
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

    public IncidenciasTutoria findIncidenciasTutoria(IncidenciasTutoriaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IncidenciasTutoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getIncidenciasTutoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IncidenciasTutoria> rt = cq.from(IncidenciasTutoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
