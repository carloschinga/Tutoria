/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.IncidenciaTutoriaDTO;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author san21
 */
public class ReporteIncidenciasDirectorjpaController extends JpaPadre {
    public ReporteIncidenciasDirectorjpaController(String empresa) {
        super(empresa);
    }
    
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }
    
    public List<IncidenciaTutoriaDTO> obtenerIncidencias(String anio, String semestre,String codidocente) {
        String sql = "SELECT " +
             "i.Fecha AS Fecha, " +
             "i.Observacion AS Observacion, " +
             "t.Incidencia AS Incidencia, " +
             "t.CodigoIncidencia AS CodigoIncidencia, " +
             "UPPER(CONCAT(su.APaterno, ' ', su.AMaterno, ' ', su.Nombre1, ' ', su.Nombre2)) AS Tutor " +
             "FROM " +
             "IncidenciasTutoria i " +
             "INNER JOIN SemestreAcademico s ON i.Semestre = s.Semestre " +
             "AND s.Semestre = ? " +
             "AND s.Anio = ? " +
             "AND s.Activo = 1 " +
             "AND i.CodigoDocente = ? " +
             "INNER JOIN TipoIncidencia t ON t.CodigoIncidencia = i.CodigoIncidencia " +
             "INNER JOIN Docente d ON d.CodigoDocente = i.CodigoDocente " +
             "INNER JOIN Sujeto su ON su.CodigoSujeto = d.CodigoSujeto " +
             "WHERE i.Estado = 'S' " +
             "ORDER BY i.Fecha;";

                     
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, semestre);
            query.setParameter(2, anio);
            query.setParameter(3, codidocente);
            
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
                        row[2] != null ? row[2].toString() : null,  // Incidencia
                        row[3] != null ? row[3].toString() : null,   // CodigoIncidencia
                        row[4] != null ? row[4].toString() : null   // CodigoIncidencia
                        
                );
                incidenciaList.add(incidencia);
            }
            return incidenciaList;
        } finally {
            em.close();
        }
    }
}