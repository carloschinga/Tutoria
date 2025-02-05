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

    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    // Método para obtener asistencia por año, semestre y código de docente
    public List<ReporteAsistenciaTutoria> obtenerAsistenciaPorDocente(String anio, String semestre, String codigoDocente) {
        String sql = "SELECT UPPER(CONCAT(a.Nombres, ' ', a.Apellidos)) AS NombreAlumno, "
                + "a.CodigoUniversitario AS CodigoUniversitario, "
                + "ast.Asistencia "
                + "FROM AsistenciaTutoria ast "
                + "INNER JOIN Alumnos a ON ast.CodigoUniversitario = a.CodigoUniversitario "
                + "INNER JOIN Docente d ON d.CodigoDocente = ast.CodigoDocente "
                + "WHERE ast.Anio = ? AND ast.Semestre = ? AND d.CodigoDocente = ?";

        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(sql);

            // Establecer los parámetros
            query.setParameter(1, anio);
            query.setParameter(2, semestre);
            query.setParameter(3, codigoDocente);

            // Ejecutar la consulta y convertir los resultados en objetos DTO
            List<Object[]> resultList = query.getResultList();
            List<ReporteAsistenciaTutoria> asistenciaList = new ArrayList<>();

            for (Object[] row : resultList) {
                ReporteAsistenciaTutoria asistencia = new ReporteAsistenciaTutoria(
                        (String) row[0], // NombreAlumno
                        (String) row[1], // CodigoUniversitario
                        (String) row[2] // Asistencia
                );
                asistenciaList.add(asistencia);
            }

            return asistenciaList;
        } finally {
            em.close();
        }
    }
}
