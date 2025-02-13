/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.Tutoria;
import dto.TutoriaPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author shaho
 */
public class TutoriaJpaController extends JpaPadre {

    public TutoriaJpaController(String empresa) {
        super(empresa);
    }

    public String listarPorDocente(int codigoDocente) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("SELECT al.CodigoUniversitario, al.CodigoSede, al.Nombres, al.Apellidos ");
            queryString.append("FROM Alumnos al ");
            queryString.append("INNER JOIN SemestreAcademico a ON a.Activo = 1 ");
            queryString.append("INNER JOIN Tutoria t ON t.CodigoSede = al.CodigoSede ");
            queryString.append("AND t.CodigoUniversitario = al.CodigoUniversitario ");
            queryString.append("AND a.Anio = t.Anio ");
            queryString.append("AND a.Semestre = t.Semestre ");
            queryString.append("AND t.Estado = 'S' ");
            queryString.append("WHERE t.CodigoDocente = ? ");
            queryString.append("ORDER BY al.Apellidos, al.Nombres;");

            Query query = em.createNativeQuery(queryString.toString());
            query.setParameter(1, codigoDocente);

            List<Object[]> resultados = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Object[] fila : resultados) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("CodigoUniversitario", fila[0]);
                jsonObj.put("CodigoSede", fila[1]);
                jsonObj.put("Nombres", fila[2]);
                jsonObj.put("Apellidos", fila[3]);
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

    public String insertarRegistrosDesdeJson(String json, String docente) {
        String result = "E"; // Default to error
        EntityManager em = null;
        try {
            em = getEntityManager();

            // Crear el StoredProcedureQuery
            StoredProcedureQuery query = em.createStoredProcedureQuery("registrar_tutores");
            query.registerStoredProcedureParameter("XmlData", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("docente", Integer.class, ParameterMode.IN);

            // Convertir el JSON a un JSONArray
            JSONArray jsonArray = new JSONArray(json);

            // Crear un nuevo JSONObject con el nombre de la raíz "Registros"
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Registro", jsonArray);

            // Convertir el JSONObject a XML con la raíz "Registros"
            String xml = XML.toString(jsonObject, "Registros");

            // Configurar el parámetro XML en la consulta del procedimiento almacenado
            query.setParameter("XmlData", xml);
            query.setParameter("docente", Integer.valueOf(docente));

            // Ejecutar el procedimiento almacenado
            query.execute();
            result = "S"; // Éxito
        } catch (NumberFormatException | JSONException e) {
            result = "E"; // Error
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }

    public String modificarlista(String json) {
        String result = "E"; // Default to error
        EntityManager em = null;
        try {
            em = getEntityManager();

            // Crear el StoredProcedureQuery
            StoredProcedureQuery query = em.createStoredProcedureQuery("modificar_tutores");
            query.registerStoredProcedureParameter("XmlData", String.class, ParameterMode.IN);

            // Convertir el JSON a un JSONArray
            JSONArray jsonArray = new JSONArray(json);

            // Crear un nuevo JSONObject con el nombre de la raíz "Registros"
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Registro", jsonArray);

            // Convertir el JSONObject a XML con la raíz "Registros"
            String xml = XML.toString(jsonObject, "Registros");

            // Configurar el parámetro XML en la consulta del procedimiento almacenado
            query.setParameter("XmlData", xml);

            // Ejecutar el procedimiento almacenado
            query.execute();
            result = "S"; // Éxito
        } catch (JSONException e) {
            result = "E"; // Error
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }

    public void create(Tutoria tutoria) throws PreexistingEntityException, Exception {
        if (tutoria.getTutoriaPK() == null) {
            tutoria.setTutoriaPK(new TutoriaPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tutoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTutoria(tutoria.getTutoriaPK()) != null) {
                throw new PreexistingEntityException("Tutoria " + tutoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tutoria tutoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tutoria = em.merge(tutoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TutoriaPK id = tutoria.getTutoriaPK();
                if (findTutoria(id) == null) {
                    throw new NonexistentEntityException("The tutoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TutoriaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutoria tutoria;
            try {
                tutoria = em.getReference(Tutoria.class, id);
                tutoria.getTutoriaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tutoria with id " + id + " no longer exists.", enfe);
            }
            em.remove(tutoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tutoria> findTutoriaEntities() {
        return findTutoriaEntities(true, -1, -1);
    }

    public List<Tutoria> findTutoriaEntities(int maxResults, int firstResult) {
        return findTutoriaEntities(false, maxResults, firstResult);
    }

    private List<Tutoria> findTutoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tutoria.class));
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

    public Tutoria findTutoria(TutoriaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tutoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getTutoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tutoria> rt = cq.from(Tutoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
