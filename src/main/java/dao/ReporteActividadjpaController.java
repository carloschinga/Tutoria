package dao;

import dto.ReporteActividad;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    // Método para obtener actividades por semestre
    public List<ReporteActividad> obtenerActividadesPorSemestre(String anio, String semestre, String codiDocente) {
        String sql = "SELECT "
                + "UPPER(tact.NombreActividad) AS Tipo, "
                + "act.Actividad AS Actividad, "
                + "act.Lugar AS Lugar, "
                + "act.FechaActividad AS Fecha, "
                + "UPPER(LTRIM(RTRIM(CONCAT( "
                + "ISNULL(s.APaterno, ''), ' ', "
                + "ISNULL(s.AMaterno, ''), ' ', "
                + "ISNULL(s.Nombre1, ''), ' ', "
                + "ISNULL(s.Nombre2, '') "
                + ")))) AS Tutor "
                + "FROM ActividadTutoria act "
                + "INNER JOIN TipoActividad tact ON tact.CodigoTipoActividad = act.CodigoTipoActividad "
                + "INNER JOIN Docente d ON d.CodigoDocente = act.CodigoDocente "
                + "INNER JOIN Sujeto s ON s.CodigoSujeto = d.CodigoSujeto "
                + "WHERE act.Anio = ? "
                + "AND act.Semestre = ? "
                + "AND act.CodigoDocente = ?;";

        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(sql);

            // Establecer los parámetros
            query.setParameter(1, anio);
            query.setParameter(2, semestre);
            query.setParameter(3, codiDocente);

            // Ejecutar la consulta y convertir los resultados en objetos DTO
            List<Object[]> resultList = query.getResultList();
            List<ReporteActividad> reporteList = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (Object[] row : resultList) {
                String fechaFormateada = null;
                if (row[3] != null) {
                    Timestamp timestamp = (Timestamp) row[5];
                    fechaFormateada = dateFormat.format(timestamp);
                }
                ReporteActividad reporte = new ReporteActividad(
                        row[0] != null ? row[0].toString() : null, // CodigoUniversitario
                        row[1] != null ? row[1].toString() : null, // Apellidos
                        row[2] != null ? row[2].toString() : null, // Alumno
                        row[3] != null ? row[3].toString() : null, // Observacion
                        
                        fechaFormateada // Fecha
                );
                reporteList.add(reporte);
            }

            return reporteList;
        } finally {
            em.close();
        }
    }
}