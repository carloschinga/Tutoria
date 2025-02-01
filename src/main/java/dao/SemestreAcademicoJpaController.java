/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dto.SemestreAcademico;
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
public class SemestreAcademicoJpaController extends JpaPadre {

    public SemestreAcademicoJpaController(String empresa) {
        super(empresa);
    }

    public String listarSemestres() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("SemestreAcademico.findAll");
            List<SemestreAcademico> lista = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (SemestreAcademico semestreAcademico : lista) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("CodigoSemestre", semestreAcademico.getCodigoSemestre());
                jsonObj.put("Semestre", semestreAcademico.getAnio() + "-" + semestreAcademico.getSemestre());
                jsonArray.put(jsonObj);
            }
            /*<<List<Object[]> resultados = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Object[] fila : resultados) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("CodigoSemestre", fila[0]);
                jsonObj.put("Semestre", fila[1]+ " " + fila[2]);
                jsonArray.put(jsonObj);
            }*/
            return jsonArray.toString();

        } catch (Exception e) {
            return "{\"Resultado\":\"Error\",\"mensaje\":\"" + e.getMessage() + "\"}";
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public SemestreAcademico getSemestreAcademicoActual() {
        EntityManager em = getEntityManager();
        try {
            String sql = "select * from SemestreAcademico where activo=1 and Semestre=2";
            Query query = em.createNativeQuery(sql, SemestreAcademico.class);
            return (SemestreAcademico) query.getSingleResult();

        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Object[] obtenerSemestreActual() {

        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("SemestreAcademico.findActivo");
            List<Object[]> resultados = query.getResultList();
            Object[] fila = null;
            for (Object[] fila2 : resultados) {
                fila = fila2;
            }
            return fila;

        } catch (Exception e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void create(SemestreAcademico semestreAcademico) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(semestreAcademico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SemestreAcademico semestreAcademico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            semestreAcademico = em.merge(semestreAcademico);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = semestreAcademico.getCodigoSemestre();
                if (findSemestreAcademico(id) == null) {
                    throw new NonexistentEntityException("The semestreAcademico with id " + id + " no longer exists.");
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
            SemestreAcademico semestreAcademico;
            try {
                semestreAcademico = em.getReference(SemestreAcademico.class, id);
                semestreAcademico.getCodigoSemestre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The semestreAcademico with id " + id + " no longer exists.", enfe);
            }
            em.remove(semestreAcademico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SemestreAcademico> findSemestreAcademicoEntities() {
        return findSemestreAcademicoEntities(true, -1, -1);
    }

    public List<SemestreAcademico> findSemestreAcademicoEntities(int maxResults, int firstResult) {
        return findSemestreAcademicoEntities(false, maxResults, firstResult);
    }

    private List<SemestreAcademico> findSemestreAcademicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SemestreAcademico.class));
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

    public SemestreAcademico findSemestreAcademico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SemestreAcademico.class, id);
        } finally {
            em.close();
        }
    }

    public int getSemestreAcademicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SemestreAcademico> rt = cq.from(SemestreAcademico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        SemestreAcademicoJpaController saDAO = new SemestreAcademicoJpaController("a");
        String resultado = saDAO.listarSemestres();
        System.out.println(resultado);
        //SemestreAcademicoJpaController saDAO = new SemestreAcademicoJpaController("a");
        //SemestreAcademico sa = new SemestreAcademico();
        // sa = saDAO.getSemestreAcademicoActual();
        //System.out.println(sa.getAnio() + "-" + sa.getSemestre());
    }
}
