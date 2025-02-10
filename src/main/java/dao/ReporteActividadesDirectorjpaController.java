/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.ReporteActividad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author san21
 */
public class ReporteActividadesDirectorjpaController extends JpaPadre{
 public ReporteActividadesDirectorjpaController(String empresa) {
        super(empresa);
    }

    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    // Método para obtener actividades por semestre
    public List<ReporteActividad> obtenerActividadesPorSemestre(String anio, String semestre,String codidocente) {
      String sql = "SELECT "
           + "act.Actividad AS Actividad, "
           + "act.Lugar AS Lugar, "
           + "UPPER(CONCAT( "
           + "    ISNULL(s.APaterno, ''), ' ', "
           + "    ISNULL(s.AMaterno, ''), ' ', "
           + "    ISNULL(s.Nombre1, ''), ' ', "
           + "    ISNULL(s.Nombre2, '') "
           + ")) AS Tutor "
           + "FROM ActividadTutoria act "
           + "INNER JOIN TipoActividad tact ON tact.CodigoTipoActividad = act.CodigoTipoActividad "
           + "INNER JOIN AsistenciaTutoria ast ON ast.CodigoDocente = act.CodigoDocente "
           + "INNER JOIN Docente d ON d.CodigoDocente = ast.CodigoDocente "
           + "INNER JOIN Sujeto s ON s.CodigoSujeto = d.CodigoSujeto "
           + "WHERE act.Anio = ? "
           + "AND act.Semestre = ? "
           + "AND ast.CodigoDocente = ?;";


        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(sql);

            // Establecer los parámetros
            query.setParameter(1, anio);
            query.setParameter(2, semestre);
            query.setParameter(3, codidocente);

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
