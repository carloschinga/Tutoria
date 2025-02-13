package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.json.JSONArray;

public class TutorDAO extends JpaPadre {

    public TutorDAO(String empresa) {
        super(empresa);
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    public JSONArray obtenerTutoriaPorSemestre(int codigoSemestre, String nombreAlumno, String prefijoApellido, String codiFacu) {
        EntityManager em = getEntityManager();
        try {
            String nombreAlumnoLike = "%" + nombreAlumno + "%";
            String apellidoLike = prefijoApellido + "%";

            String sql = "WITH AlumnosConNombre AS ( "
                    + "    SELECT CodigoUniversitario, UPPER(CONCAT(Apellidos, ' ', Nombres)) AS Nombre "
                    + "    FROM Alumnos "
                    + "    WHERE Apellidos LIKE ? and CodigoUniversitario LIKE  ? "
                    + ") "
                    + "SELECT "
                    + "    a.Nombre AS Alumno, "
                    + "    UPPER(CONCAT(s.APaterno, ' ', s.AMaterno, ' ', s.Nombre1, ' ', s.Nombre2)) AS Tutor, "
                    + "    m.Ciclo AS Ciclo, "
                    + "    e.Escuela as Escuela "
                    + "FROM "
                    + "    Tutoria t "
                    + "INNER JOIN Docente d ON d.CodigoDocente = t.CodigoDocente "
                    + "INNER JOIN Sujeto s ON d.CodigoSujeto = s.CodigoSujeto "
                    + "INNER JOIN Matriculas m ON t.CodigoUniversitario = m.CodigoUniversitario "
                    + "INNER JOIN Escuelas e ON e.CodigoEscuela = SUBSTRING(m.CodigoUniversitario, 1, 4) "
                    + "INNER JOIN SemestreAcademico se ON m.Anio = se.Anio AND m.Semestre = se.Semestre "
                    + "    AND se.CodigoSemestre = ? "
                    + "INNER JOIN AlumnosConNombre a ON a.CodigoUniversitario = m.CodigoUniversitario "
                    + "WHERE a.Nombre LIKE ? "
                    + "FOR JSON PATH;";

            Query query = em.createNativeQuery(sql);
            query.setParameter(1, apellidoLike);
            query.setParameter(2, codiFacu + "%");
            query.setParameter(3, codigoSemestre);
            query.setParameter(4, nombreAlumnoLike);

            // Obtener la lista de resultados
            List<String> results = query.getResultList();

            // Si la lista está vacía, devolvemos un JSONArray vacío
            if (results.isEmpty()) {
                return new JSONArray();
            }

            // Concatenar todos los fragmentos JSON en una sola cadena válida
            StringBuilder jsonString = new StringBuilder();
            for (String result : results) {
                jsonString.append(result);
            }

            // Convertir la cadena JSON a un JSONArray y devolverlo
            return new JSONArray(jsonString.toString());

        } catch (Exception ex) {
            ex.printStackTrace();  // Imprimir el error para depuración
            return new JSONArray();
        } finally {
            em.close();
        }
    }

    /*
    public JSONArray obtenerTutoriaPorSemestre(int codigoSemestre, String nombreAlumno, String prefijoApellido) {
        EntityManager em = getEntityManager();
        try {
            // Ajustar la búsqueda del nombre para que funcione con LIKE
            String nombreAlumnoLike = "%" + nombreAlumno + "%";
            String apellidoLike = prefijoApellido + "%"; // Prefijo dinámico

            // Consulta SQL corregida con parámetros dinámicos
            String sql = "WITH AlumnosConNombre AS ( "
                    + "    SELECT CodigoUniversitario, UPPER(CONCAT(Apellidos, ' ', Nombres)) AS Nombre "
                    + "    FROM Alumnos "
                    + "    WHERE Apellidos LIKE ? "
                    + ") "
                    + "SELECT "
                    + "    a.Nombre AS Alumno, "
                    + "    UPPER(CONCAT(s.APaterno, ' ', s.AMaterno, ' ', s.Nombre1, ' ', s.Nombre2)) AS Tutor, "
                    + "    m.Ciclo AS Ciclo, "
                    + "    e.Escuela as Escuela "
                    + "FROM "
                    + "    Tutoria t "
                    + "INNER JOIN Docente d ON d.CodigoDocente = t.CodigoDocente "
                    + "INNER JOIN Sujeto s ON d.CodigoSujeto = s.CodigoSujeto "
                    + "INNER JOIN Matriculas m ON t.CodigoUniversitario = m.CodigoUniversitario "
                    + "INNER JOIN Escuelas e ON e.CodigoEscuela = SUBSTRING(m.CodigoUniversitario, 1, 4) "
                    + "INNER JOIN SemestreAcademico se ON m.Anio = se.Anio AND m.Semestre = se.Semestre "
                    + "    AND se.CodigoSemestre = ? "
                    + "INNER JOIN AlumnosConNombre a ON a.CodigoUniversitario = m.CodigoUniversitario "
                    + "WHERE a.Nombre LIKE ? "
                    + "FOR JSON PATH;";

            // Crear la consulta nativa usando JPA
            Query query = em.createNativeQuery(sql);

            // Establecer los parámetros en orden
            query.setParameter(1, apellidoLike);
            query.setParameter(2, codigoSemestre);
            query.setParameter(3, nombreAlumnoLike);

            // Ejecutar la consulta y obtener el resultado JSON
            String jsonResult = (String) query.getSingleResult();

            // Convertir la cadena JSON a JSONArray
            return new JSONArray(jsonResult);
        }catch(Exception ex){
            String s="";
            s="sasas";
            return new JSONArray();
        } 
        finally {
            em.close();
        }
    }

     */
}
