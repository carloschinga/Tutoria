package dao;

import dto.ReporteAsistenciaTutoria;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para obtener la asistencia de tutoria.
 */
public class ReporteAsistencias extends JpaPadre {

    public ReporteAsistencias(String empresa) {
        super(empresa);
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    // Método para obtener asistencia por año, semestre y código de docente
    public List<ReporteAsistenciaTutoria> obtenerAsistenciaPorDocente(String codiDoce, String anio, String semestre, String sesion) {
        String sql = "SELECT UPPER(CONCAT(a.Apellidos ,' ', a.Nombres )) AS NombreAlumno, "
                + "a.CodigoUniversitario AS CodigoUniversitario, "
                + "ast.Asistencia AS Asistencia, "
                + "UPPER(CONCAT(s.APaterno, ' ', s.AMaterno, ' ', s.Nombre1, ' ', s.Nombre2)) AS Tutor "
                + "FROM AsistenciaTutoria ast "
                + "INNER JOIN Alumnos a ON ast.CodigoUniversitario = a.CodigoUniversitario "
                + "INNER JOIN Docente d ON d.CodigoDocente = ast.CodigoDocente "
                + "INNER JOIN Sujeto s ON s.CodigoSujeto = d.CodigoSujeto "
                + "WHERE ast.Anio = ? AND ast.Semestre = ? "
                + "AND d.CodigoDocente = ? AND ast.Sesion = ?";

        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(sql);

            // Establecer los parámetros en el orden correcto
            query.setParameter(1, anio);
            query.setParameter(2, semestre);
            query.setParameter(3, codiDoce);  // Primero el código del docente
            query.setParameter(4, sesion);    // Luego la sesión

            // Ejecutar la consulta y convertir los resultados en objetos DTO
            List<Object[]> resultList = query.getResultList();
            List<ReporteAsistenciaTutoria> asistenciaList = new ArrayList<>();

            for (Object[] row : resultList) {
                ReporteAsistenciaTutoria asistencia = new ReporteAsistenciaTutoria(
                        (String) row[0], // NombreAlumno
                        (String) row[1], // CodigoUniversitario
                        (String) row[2], // Asistencia
                        (String) row[3] // Tutor
                );
                asistenciaList.add(asistencia);
            }

            return asistenciaList;
        } finally {
            em.close();
        }
    }

}
