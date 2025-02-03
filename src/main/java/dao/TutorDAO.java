package dao;

import dto.TutorDto;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import org.json.JSONObject;

public class TutorDAO extends JpaPadre {

    public TutorDAO(String empresa) {
        super(empresa); // Llamamos al constructor de la clase JpaPadre
    }

    // Método para obtener el EntityManager desde la configuración de persistence.xml
    public EntityManager getEntityManager() {
        return super.getEntityManager(); // Se aprovecha el EntityManager proporcionado por JpaPadre
    }

    // Método para obtener el tutor del alumno según el nombre del alumno
    public JSONObject obtenerTutoresPorAnioSemestre(int anio, int semestre, String nombre) {
        try {
            EntityManager em = getEntityManager();

            // Consulta SQL que devuelve JSON directamente
            String sql = "WITH AlumnosConNombre AS ("
                    + "    SELECT CodigoUniversitario, UPPER(CONCAT(Apellidos, ' ', Nombres)) AS Nombre "
                    + "    FROM Alumnos "
                    + "    WHERE Apellidos LIKE 'DE LA%'"
                    + ") "
                    + "SELECT "
                    + "    a.Nombre AS Alumno, "
                    + "    UPPER(CONCAT(s.APaterno, ' ', s.AMaterno, ' ', s.Nombre1, ' ', s.Nombre2)) AS Tutor, "
                    + "    m.Ciclo AS Ciclo "
                    + "FROM "
                    + "    Tutoria t "
                    + "INNER JOIN Docente d ON d.CodigoDocente = t.CodigoDocente "
                    + "INNER JOIN Sujeto s ON d.CodigoSujeto = s.CodigoSujeto "
                    + "INNER JOIN Matriculas m ON t.CodigoUniversitario = m.CodigoUniversitario "
                    + "    AND m.Anio = :anio "
                    + "    AND m.Semestre = :semestre "
                    + "INNER JOIN Escuelas e ON e.CodigoEscuela = SUBSTRING(m.CodigoUniversitario, 1, 4) "
                    + "INNER JOIN SemestreAcademico se ON m.Anio = se.Anio AND m.Semestre = se.Semestre "
                    + "INNER JOIN AlumnosConNombre a ON a.CodigoUniversitario = m.CodigoUniversitario "
                    + "WHERE a.Nombre LIKE 'DE LA%' "
                    + "FOR JSON PATH;";

            // Crear la consulta nativa usando el EntityManager
            Query query = em.createNativeQuery(sql);

            // Establecer los parámetros de la consulta
            query.setParameter("anio", anio);
            query.setParameter("semestre", semestre);

            // Ejecutar la consulta y obtener el resultado JSON
            String jsonResult = (String) query.getSingleResult();

            // Convertir el resultado a JSONObject
            JSONObject jsonObject = new JSONObject(jsonResult);
            jsonObject.put("resultado", "ok");

            // Retornar el JSON
            return jsonObject;
        } catch (Exception ex) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultado", "ok");

            // Retornar el JSON
            return jsonObject;
        }
    }
}
