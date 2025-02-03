package dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class TutorDAO extends JpaPadre {

    public TutorDAO(String empresa) {
        super(empresa);
    }

    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    public JSONObject obtenerTutoresPorAnioSemestre(int anio, int semestre, String nombre) {
        EntityManager em = null;
        JSONObject jsonObject = new JSONObject();
        try {
            em = getEntityManager();

            String sql = "WITH AlumnosConNombre AS ("
                    + "    SELECT CodigoUniversitario, UPPER(CONCAT(Apellidos, ' ', Nombres)) AS Nombre "
                    + "    FROM Alumnos "
                    + "    WHERE Apellidos LIKE :nombre"
                    + ") "
                    + "SELECT "
                    + "    a.Nombre AS Alumno, "
                    + "    UPPER(CONCAT(s.APaterno, ' ', s.AMaterno, ' ', s.Nombre1, ' ', s.Nombre2)) AS Tutor, "
                    + "    m.Ciclo AS Ciclo "
                    + "FROM Tutoria t "
                    + "INNER JOIN Docente d ON d.CodigoDocente = t.CodigoDocente "
                    + "INNER JOIN Sujeto s ON d.CodigoSujeto = s.CodigoSujeto "
                    + "INNER JOIN Matriculas m ON t.CodigoUniversitario = m.CodigoUniversitario "
                    + "    AND m.Anio = :anio "
                    + "    AND m.Semestre = :semestre "
                    + "INNER JOIN Escuelas e ON e.CodigoEscuela = SUBSTRING(m.CodigoUniversitario, 1, 4) "
                    + "INNER JOIN SemestreAcademico se ON m.Anio = se.Anio AND m.Semestre = se.Semestre "
                    + "INNER JOIN AlumnosConNombre a ON a.CodigoUniversitario = m.CodigoUniversitario "
                    + "WHERE a.Nombre LIKE :nombre "
                    + "FOR JSON PATH;";

            Query query = em.createNativeQuery(sql);
            query.setParameter("anio", anio);
            query.setParameter("semestre", semestre);
            query.setParameter("nombre", nombre + "%");  // Permite mayor flexibilidad

            List<String> resultList = query.getResultList();

            if (resultList.isEmpty()) {
                jsonObject.put("resultado", "vacio");
            } else {
                String jsonResult = resultList.get(0);
                jsonObject = new JSONObject(jsonResult);
                jsonObject.put("resultado", "ok");
            }
        } catch (Exception ex) {
            jsonObject.put("resultado", "error");
            jsonObject.put("mensaje", ex.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return jsonObject;
    }
}

