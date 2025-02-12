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
    
    public List<IncidenciaTutoriaDTO> obtenerIncidencias(String termino) {
    String sql = "SELECT " +
            "a.CodigoUniversitario, " +
            "a.Apellidos, " +
            "UPPER(CONCAT(a.Nombres, ' ', a.Apellidos)) AS Alumno, " +
            "ti.Incidencia, " +
            "it.Observacion, " +
            "it.Fecha " +
            "FROM Alumnos a " +
            "INNER JOIN IncidenciasTutoria it ON a.CodigoUniversitario = it.CodigoUniversitario " +
            "INNER JOIN TipoIncidencia ti ON it.CodigoIncidencia = ti.CodigoIncidencia " +
            "WHERE a.CodigoUniversitario LIKE '03%' " +
            "AND (LOWER(a.Nombres) LIKE LOWER(?) OR LOWER(a.Apellidos) LIKE LOWER(?)) " +
            "ORDER BY it.Fecha;";

    EntityManager em = getEntityManager();
    try {
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, "%" + termino + "%"); // Permite buscar cualquier coincidencia
        query.setParameter(2, "%" + termino + "%"); 

        List<Object[]> resultList = query.getResultList();
        List<IncidenciaTutoriaDTO> incidenciaList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Object[] row : resultList) {
            String fechaFormateada = null;
            if (row[5] != null) {
                Timestamp timestamp = (Timestamp) row[5];
                fechaFormateada = dateFormat.format(timestamp);
            }

            IncidenciaTutoriaDTO incidencia = new IncidenciaTutoriaDTO(
                    row[0] != null ? row[0].toString() : null, // CodigoUniversitario
                    row[1] != null ? row[1].toString() : null, // Apellidos
                    row[2] != null ? row[2].toString() : null, // Alumno
                    row[3] != null ? row[3].toString() : null, // Incidencia
                    row[4] != null ? row[4].toString() : null, // Observacion
                    fechaFormateada // Fecha
            );
            incidenciaList.add(incidencia);
        }
        return incidenciaList;
    } finally {
        em.close();
    }
}
}