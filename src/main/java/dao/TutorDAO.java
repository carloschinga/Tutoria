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

       public JSONArray obtenerTutoriaPorSemestre(int codigoSemestre, String nombreAlumno) {
            EntityManager em = getEntityManager();
        // Asegurar que nombreAlumno se use correctamente en LIKE
        String nombreAlumnoLike = nombreAlumno + "%"; 

        // Consulta SQL con `?` en lugar de `:parametro`
        String sql = "WITH AlumnosConNombre AS ( " +
                     "    SELECT CodigoUniversitario, UPPER(CONCAT(Apellidos, ' ', Nombres)) AS Nombre " +
                     "    FROM Alumnos " +
                     "    WHERE Apellidos LIKE 'DE LA%' " +
                     ") " +
                     "SELECT " +
                     "    a.Nombre AS Alumno, " +
                     "    UPPER(CONCAT(s.APaterno, ' ', s.AMaterno, ' ', s.Nombre1, ' ', s.Nombre2)) AS Tutor, " +
                     "    m.Ciclo AS Ciclo " +
                     "FROM " +
                     "    Tutoria t " +
                     "INNER JOIN Docente d ON d.CodigoDocente = t.CodigoDocente " +
                     "INNER JOIN Sujeto s ON d.CodigoSujeto = s.CodigoSujeto " +
                     "INNER JOIN Matriculas m ON t.CodigoUniversitario = m.CodigoUniversitario " +
                     "INNER JOIN Escuelas e ON e.CodigoEscuela = SUBSTRING(m.CodigoUniversitario, 1, 4) " +
                     "INNER JOIN SemestreAcademico se ON m.Anio = se.Anio AND m.Semestre = se.Semestre " +
                     "    AND se.CodigoSemestre = ? " +
                     "INNER JOIN AlumnosConNombre a ON a.CodigoUniversitario = m.CodigoUniversitario " +
                     "WHERE a.Nombre LIKE ? " +
                     "FOR JSON PATH;";

        // Crear la consulta nativa usando JPA
        Query query = em.createNativeQuery(sql);

        // Establecer los parámetros en orden
        query.setParameter(1, codigoSemestre);
        query.setParameter(2, nombreAlumnoLike);

        // Ejecutar la consulta y obtener el resultado JSON
        String jsonResult = (String) query.getSingleResult();
        
        // Convertir la cadena JSON a JSONArray
        return new JSONArray(jsonResult);
    }
}


