/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dto.AsistenciaTutoria;
import dto.AsistenciaTutoriaPK;
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
public class AsistenciaTutoriaJpaController extends JpaPadre {

    public AsistenciaTutoriaJpaController(String empresa) {
        super(empresa);
    }

    public String BuscarAsistencias(int codigoDocente, int sesion) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("SELECT al.CodigoUniversitario, al.CodigoSede, al.Nombres, al.Apellidos, asis.Sesion ");
            queryString.append("FROM Alumnos al ");
            queryString.append("INNER JOIN SemestreAcademico a ON a.Activo = 1 ");
            queryString.append("INNER JOIN Tutoria t ON t.CodigoSede = al.CodigoSede AND t.CodigoUniversitario = al.CodigoUniversitario ");
            queryString.append("AND a.Anio = t.Anio AND a.Semestre = t.Semestre AND t.Estado='S' ");
            queryString.append("LEFT JOIN AsistenciaTutoria asis ON asis.CodigoDocente = t.CodigoDocente ");
            queryString.append("AND asis.CodigoUniversitario = al.CodigoUniversitario ");
            queryString.append("AND asis.CodigoSede = al.CodigoSede ");
            queryString.append("AND asis.Sesion = ? ");
            queryString.append("WHERE t.CodigoDocente = ? ");
            queryString.append("ORDER BY al.Apellidos, al.Nombres;");

            Query query = em.createNativeQuery(queryString.toString());
            query.setParameter(1, sesion);
            query.setParameter(2, codigoDocente);

            List<Object[]> resultados = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Object[] fila : resultados) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("CodigoUniversitario", fila[0]);
                jsonObj.put("CodigoSede", fila[1]);
                jsonObj.put("Nombres", fila[2]);
                jsonObj.put("Apellidos", fila[3]);
                jsonObj.put("Sesion", fila[4]);
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

    public String BuscarAsistenciassoloasignados(int codigoDocente, int sesion) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("SELECT al.CodigoUniversitario, al.CodigoSede, al.Nombres, al.Apellidos, asis.Asistencia ");
            queryString.append("FROM Alumnos al ");
            queryString.append("INNER JOIN SemestreAcademico a on a.Activo = 1 ");
            queryString.append("INNER JOIN Tutoria t ON t.CodigoSede = al.CodigoSede AND t.CodigoUniversitario = al.CodigoUniversitario ");
            queryString.append("AND a.Anio = t.Anio AND a.Semestre = t.Semestre AND t.Estado='S' ");
            queryString.append("INNER JOIN AsistenciaTutoria asis ON asis.CodigoDocente = t.CodigoDocente ");
            queryString.append("AND asis.CodigoUniversitario = al.CodigoUniversitario ");
            queryString.append("AND asis.CodigoSede = al.CodigoSede ");
            queryString.append("AND asis.Sesion = ? ");
            queryString.append("WHERE t.CodigoDocente = ? ");
            queryString.append("ORDER BY al.Apellidos, al.Nombres;");

            Query query = em.createNativeQuery(queryString.toString());
            query.setParameter(1, sesion);
            query.setParameter(2, codigoDocente);

            List<Object[]> resultados = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Object[] fila : resultados) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("CodigoUniversitario", fila[0]);
                jsonObj.put("CodigoSede", fila[1]);
                jsonObj.put("Nombres", fila[2]);
                jsonObj.put("Apellidos", fila[3]);
                jsonObj.put("Sesion", fila[4]);
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

    public String ContarAsistenciasPorActividad(String codigoDocente, String anio, Character semestre, int sesion) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("SELECT act.Actividad, COUNT(asis.Sesion) ");
            queryString.append("FROM ActividadTutoria act ");
            queryString.append("LEFT JOIN AsistenciaTutoria asis ON asis.CodigoDocente = act.CodigoDocente ");
            queryString.append("AND asis.Anio = act.Anio ");
            queryString.append("AND asis.Semestre = act.Semestre ");
            queryString.append("AND asis.Sesion = act.Sesion ");
            queryString.append("WHERE act.CodigoDocente = ? ");
            queryString.append("AND act.Anio = ? ");
            queryString.append("AND act.Semestre = ? ");
            queryString.append("AND act.Sesion = ?");
            queryString.append(" group by act.Actividad");

            Query query = em.createNativeQuery(queryString.toString());
            query.setParameter(1, codigoDocente);
            query.setParameter(2, anio);
            query.setParameter(3, semestre);
            query.setParameter(4, sesion);

            Object[] resultado = (Object[]) query.getSingleResult();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("Actividad", resultado[0]);
            jsonObj.put("CantidadAsistencias", resultado[1]);
            jsonObj.put("resultado", "ok");

            return jsonObj.toString();

        } catch (JSONException e) {
            return "{\"Resultado\":\"Error\",\"mensaje\":\"" + e + "\"}";
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public String AsignarAlumnos(String json, String docente, String anio, Character semestre, String sesion) {
        String result = "E"; // Valor por defecto es error
        EntityManager em = null;
        try {
            em = getEntityManager();

            // Crear el StoredProcedureQuery
            StoredProcedureQuery query = em.createStoredProcedureQuery("registrar_alumnos_tutoria");
            query.registerStoredProcedureParameter("XmlData", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("docente", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("anio", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("Semestre", Character.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("sesion", Integer.class, ParameterMode.IN);

            // Convertir el JSON a un JSONArray
            JSONArray jsonArray = new JSONArray(json);

            // Crear un nuevo JSONObject con el nombre de la raíz "Registros"
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Registro", jsonArray);

            // Convertir el JSONObject a XML con la raíz "Registros"
            String xml = XML.toString(jsonObject, "Registros");

            // Configurar los parámetros del procedimiento almacenado
            query.setParameter("XmlData", xml);
            query.setParameter("docente", Integer.valueOf(docente));
            query.setParameter("anio", anio);
            query.setParameter("Semestre", semestre);
            query.setParameter("sesion", Integer.valueOf(sesion));

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

    public String RegistrarAsistencias(String json, String docente, String anio, Character semestre, String sesion) {
        String result = "E"; // Valor por defecto es error
        EntityManager em = null;
        try {
            em = getEntityManager();

            // Crear el StoredProcedureQuery
            StoredProcedureQuery query = em.createStoredProcedureQuery("actualizar_asistencia_tutoria");
            query.registerStoredProcedureParameter("XmlData", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("docente", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("anio", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("Semestre", Character.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("sesion", Integer.class, ParameterMode.IN);

            // Convertir el JSON a un JSONArray
            JSONArray jsonArray = new JSONArray(json);

            // Crear un nuevo JSONObject con el nombre de la raíz "Registros"
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Registro", jsonArray);

            // Convertir el JSONObject a XML con la raíz "Registros"
            String xml = XML.toString(jsonObject, "Registros");

            // Configurar los parámetros del procedimiento almacenado
            query.setParameter("XmlData", xml);
            query.setParameter("docente", Integer.valueOf(docente));
            query.setParameter("anio", anio);
            query.setParameter("Semestre", semestre);
            query.setParameter("sesion", Integer.valueOf(sesion));

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

    public void create(AsistenciaTutoria asistenciaTutoria) throws PreexistingEntityException, Exception {
        if (asistenciaTutoria.getAsistenciaTutoriaPK() == null) {
            asistenciaTutoria.setAsistenciaTutoriaPK(new AsistenciaTutoriaPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asistenciaTutoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAsistenciaTutoria(asistenciaTutoria.getAsistenciaTutoriaPK()) != null) {
                throw new PreexistingEntityException("AsistenciaTutoria " + asistenciaTutoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AsistenciaTutoria asistenciaTutoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            asistenciaTutoria = em.merge(asistenciaTutoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AsistenciaTutoriaPK id = asistenciaTutoria.getAsistenciaTutoriaPK();
                if (findAsistenciaTutoria(id) == null) {
                    throw new NonexistentEntityException("The asistenciaTutoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AsistenciaTutoriaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AsistenciaTutoria asistenciaTutoria;
            try {
                asistenciaTutoria = em.getReference(AsistenciaTutoria.class, id);
                asistenciaTutoria.getAsistenciaTutoriaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistenciaTutoria with id " + id + " no longer exists.", enfe);
            }
            em.remove(asistenciaTutoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AsistenciaTutoria> findAsistenciaTutoriaEntities() {
        return findAsistenciaTutoriaEntities(true, -1, -1);
    }

    public List<AsistenciaTutoria> findAsistenciaTutoriaEntities(int maxResults, int firstResult) {
        return findAsistenciaTutoriaEntities(false, maxResults, firstResult);
    }

    private List<AsistenciaTutoria> findAsistenciaTutoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsistenciaTutoria.class));
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

    public AsistenciaTutoria findAsistenciaTutoria(AsistenciaTutoriaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsistenciaTutoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaTutoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsistenciaTutoria> rt = cq.from(AsistenciaTutoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
