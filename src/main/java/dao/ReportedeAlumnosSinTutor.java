package dao;

import dto.ReporteAlumnossinTutor;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para obtener reportes de alumnos sin tutoría. Extiende JpaPadre para
 * manejar la persistencia.
 *
 * @author san21
 */
public class ReportedeAlumnosSinTutor extends JpaPadre {

    public ReportedeAlumnosSinTutor(String empresa) {
        super(empresa);
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    // Método para obtener alumnos sin tutoría
    public List<ReporteAlumnossinTutor> getAlumnosSinTutoria(String anio, String semestre, String codigoEscuela) {
       String query = "SELECT a.CodigoUniversitario, " 
             + "CONCAT(a.Apellidos, ' ', a.Nombres) AS NombreCompleto, " 
             + "m.Ciclo "
             + "FROM Matriculas m "
             + "INNER JOIN SemestreAcademico s ON m.Anio = s.Anio "
             + "AND m.Semestre = s.Semestre "
             + "AND m.Anio = ?1 "
             + "AND m.Semestre = ?2 "
             + "AND m.TipoMatricula <> 10 "
             + "AND m.CodigoUniversitario LIKE ?3 "
             + "INNER JOIN Alumnos a ON m.CodigoUniversitario = a.CodigoUniversitario "
             + "WHERE NOT EXISTS ( "
             + "SELECT 1 FROM Tutoria t "
             + "WHERE t.Anio = ?1 AND t.Semestre = ?2 "
             + "AND t.CodigoUniversitario = m.CodigoUniversitario "
             + "AND t.CodigoUniversitario LIKE ?3 "
             + ") "
             + "ORDER BY m.Ciclo, a.Apellidos, a.Nombres";


        EntityManager em = getEntityManager();
        try {
            Query nativeQuery = em.createNativeQuery(query);

            // Pasar los parámetros recibidos del servlet (semestre, escuela, año)
            nativeQuery.setParameter(1, anio);               // Año
            nativeQuery.setParameter(2, semestre);           // Semestre
            nativeQuery.setParameter(3, codigoEscuela + "%"); // Escuela (se concatena % para hacer el LIKE)

            // Ejecutar la consulta y convertir los resultados en objetos DTO
            List<Object[]> resultList = nativeQuery.getResultList();
            List<ReporteAlumnossinTutor> reporteList = new ArrayList<>();

            for (Object[] row : resultList) {
                ReporteAlumnossinTutor reporte = new ReporteAlumnossinTutor(
                        (String) row[0], // CodigoUniversitario
                        (String) row[1], // Apellidos
                        (String) row[2] // Ciclo
                );
                reporteList.add(reporte);
            }

            return reporteList;
        } finally {
            em.close();
        }
    }
}
