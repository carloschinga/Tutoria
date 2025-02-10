package dao;

import dto.ReporteActividad;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para obtener reportes de actividades. Extiende JpaPadre para manejar la
 * persistencia.
 *
 */
public class ReporteActividadjpaController extends JpaPadre {

    public ReporteActividadjpaController(String empresa) {
        super(empresa);
    }

    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    // Método para obtener actividades por semestre
    public List<ReporteActividad> obtenerActividadesPorSemestre(String anio, String semestre) {
        String sql = "SELECT "
                + "act.Actividad AS Actividad, "
                + "act.Lugar AS Lugar, "
                + "UPPER(CONCAT(s.APaterno, ' ', s.AMaterno, ' ', s.Nombre1, ' ', s.Nombre2)) AS Tutor "
                + "FROM ActividadTutoria act "
                + "INNER JOIN TipoActividad tact ON tact.CodigoTipoActividad = act.CodigoTipoActividad "
                + "INNER JOIN AsistenciaTutoria ast ON ast.CodigoDocente = act.CodigoDocente "
                + "INNER JOIN Docente d ON d.CodigoDocente = ast.CodigoDocente "
                + "INNER JOIN Sujeto s ON s.CodigoSujeto = d.CodigoSujeto "
                + "WHERE act.Anio = ? "
                + "AND act.Semestre = ?;";

        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(sql);

            // Establecer los parámetros
            query.setParameter(1, anio);
            query.setParameter(2, semestre);

            // Ejecutar la consulta y convertir los resultados en objetos DTO
            List<Object[]> resultList = query.getResultList();
            List<ReporteActividad> reporteList = new ArrayList<>();

            for (Object[] row : resultList) {
                ReporteActividad reporte = new ReporteActividad(
                        (String) row[0], // Actividad
                        (String) row[1], // Lugar
                        (String) row[2] // Lugar
                );
                reporteList.add(reporte);
            }

            return reporteList;
        } finally {
            em.close();
        }
    }
}
