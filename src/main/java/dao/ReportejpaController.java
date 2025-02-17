/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.ReporteTutores;
import dto.ReporteTutoresParametro;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author san21
 */
public class ReportejpaController extends JpaPadre {

    public ReportejpaController(String empresa) {
        super(empresa);  // Llamamos al constructor de la clase JpaPadre
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();  // Se aprovecha el EntityManager proporcionado por JpaPadre
    }

    // Método para obtener los datos del reporte usando los parámetros del DTO
    public List<ReporteTutores> getReportData(ReporteTutoresParametro params) {
        String query = "SELECT DISTINCT do.Nombre as nombre, m.Ciclo as ciclo "
                + "FROM Tutoria t "
                + "INNER JOIN Matriculas m ON t.CodigoUniversitario = m.CodigoUniversitario "
                + "AND m.Anio = t.Anio AND m.Semestre = t.Semestre "
                + "INNER JOIN Escuelas e ON e.CodigoEscuela = SUBSTRING(t.CodigoUniversitario, 1, 4) "
                + "INNER JOIN ("
                + "    SELECT d.CodigoDocente, UPPER(CONCAT(s.APaterno, ' ', s.AMaterno, ' ', s.Nombre1, ' ', s.Nombre2)) AS Nombre "
                + "    FROM Sujeto s "
                + "    INNER JOIN Docente d ON s.CodigoSujeto = d.CodigoSujeto"
                + ") do ON t.CodigoDocente = do.CodigoDocente "
                + "WHERE t.CodigoUniversitario LIKE ? AND t.Anio = ? AND t.Semestre = ?";

        EntityManager em = getEntityManager();
        try {
            Query nativeQuery = em.createNativeQuery(query);

            // Establecer los parámetros de la consulta
            nativeQuery.setParameter(1, "%" + params.getCodiEscuela() + "%");  // Usamos LIKE para el código de la escuela
            nativeQuery.setParameter(2, params.getCodiAño());  // Año
            nativeQuery.setParameter(3, params.getCodiSemestre());  // Semestre

            // Ejecutar la consulta y obtener los resultados
            List<Object[]> resultList = nativeQuery.getResultList();

            // Convertir el resultado en objetos DTO
            List<ReporteTutores> reportData = new ArrayList<>();
            for (Object[] row : resultList) {
                String nombre = (String) row[0];
                String ciclo = (String) row[1];
                reportData.add(new ReporteTutores(nombre, ciclo));  // Crear un objeto ReporteTutores para cada fila
            }

            return reportData;  // Devolver la lista de objetos
        } finally {
            em.close();
        }
    }
}
