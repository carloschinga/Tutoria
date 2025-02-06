package dao;
import dto.IncidenciaTutoriaDTO;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReporteIncidenciasTutoria extends JpaPadre {
    public ReporteIncidenciasTutoria(String empresa) {
        super(empresa);
    }
    
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }
    
    public List<IncidenciaTutoriaDTO> obtenerIncidencias(String anio, String semestre) {
        String sql = "SELECT i.Fecha AS Fecha, " +
                     "i.Observacion AS Observacion, " +
                     "s.Activo AS Activo, " +
                     "t.Incidencia AS Incidencia, " +
                     "t.CodigoIncidencia AS CodigoIncidencia " +
                     "FROM IncidenciasTutoria i " +
                     "INNER JOIN SemestreAcademico s ON i.Semestre = s.Semestre " +
                     "AND s.Semestre = ? " +
                     "AND s.Anio = ? " +
                     "AND s.Activo = 1 " +
                     "INNER JOIN TipoIncidencia t ON t.CodigoIncidencia = i.CodigoIncidencia " +
                     "WHERE i.Estado = 'S' " +
                     "ORDER BY i.Fecha";
                     
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, semestre);
            query.setParameter(2, anio);
            
            List<Object[]> resultList = query.getResultList();
            List<IncidenciaTutoriaDTO> incidenciaList = new ArrayList<>();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            for (Object[] row : resultList) {
                String fechaFormateada = null;
                if (row[0] != null) {
                    Timestamp timestamp = (Timestamp) row[0];
                    fechaFormateada = dateFormat.format(timestamp);
                }
                
                IncidenciaTutoriaDTO incidencia = new IncidenciaTutoriaDTO(
                        fechaFormateada,                    // Fecha (convertida de Timestamp a String)
                        row[1] != null ? row[1].toString() : null,  // Observacion
                        row[2] != null ? row[3].toString() : null,  // Incidencia
                        row[3] != null ? row[4].toString() : null   // CodigoIncidencia
                );
                incidenciaList.add(incidencia);
            }
            return incidenciaList;
        } finally {
            em.close();
        }
    }
}