/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.Facultades;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author shaho
 */
public class FacultadesJpaController extends JpaPadre{

    public FacultadesJpaController(String empresa) {
        super(empresa);
    }



    public void create(Facultades facultades) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(facultades);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacultades(facultades.getCodigoFacultad()) != null) {
                throw new PreexistingEntityException("Facultades " + facultades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Facultades facultades) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            facultades = em.merge(facultades);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = facultades.getCodigoFacultad();
                if (findFacultades(id) == null) {
                    throw new NonexistentEntityException("The facultades with id " + id + " no longer exists.");
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
            Facultades facultades;
            try {
                facultades = em.getReference(Facultades.class, id);
                facultades.getCodigoFacultad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facultades with id " + id + " no longer exists.", enfe);
            }
            em.remove(facultades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Facultades> findFacultadesEntities() {
        return findFacultadesEntities(true, -1, -1);
    }

    public List<Facultades> findFacultadesEntities(int maxResults, int firstResult) {
        return findFacultadesEntities(false, maxResults, firstResult);
    }

    private List<Facultades> findFacultadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Facultades.class));
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

    public Facultades findFacultades(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Facultades.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacultadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Facultades> rt = cq.from(Facultades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
