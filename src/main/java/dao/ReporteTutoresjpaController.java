/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.ReporteTutores;
import dto.ReporteTutoresParametro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author san21
 */
public class ReporteTutoresjpaController extends JpaPadre {

    public ReporteTutoresjpaController(String empresa) {
        super(empresa); // Llamamos al constructor de la clase JpaPadre
    }

    // Método para obtener el EntityManager desde la configuración de persistence.xml
    public EntityManager getEntityManager() {
        return super.getEntityManager(); // Se aprovecha el EntityManager proporcionado por JpaPadre
    }

    // Método para obtener los datos del reporte usando los parámetros del DTO
    public List<ReporteTutores> getReportData(ReporteTutoresParametro params) {
        String query = "SELECT DISTINCT "
                + "    do.Nombre AS nombre, "
                + "    m.Ciclo AS ciclo, "
                + "    do.CodigoDocente AS Codigo_Docente "
                + "FROM "
                + "    Tutoria t "
                + "INNER JOIN "
                + "    Matriculas m "
                + "    ON t.CodigoUniversitario = m.CodigoUniversitario "
                + "    AND m.Anio = t.Anio "
                + "    AND m.Semestre = t.Semestre "
                + "INNER JOIN "
                + "    Escuelas e "
                + "    ON e.CodigoEscuela = SUBSTRING(t.CodigoUniversitario, 1, 4) "
                + "INNER JOIN "
                + "    ( "
                + "        SELECT "
                + "            d.CodigoDocente, "
                + "            UPPER(CONCAT(s.APaterno, ' ', s.AMaterno, ' ', s.Nombre1, ' ', s.Nombre2)) AS Nombre "
                + "        FROM "
                + "            Sujeto s "
                + "        INNER JOIN "
                + "            Docente d "
                + "            ON s.CodigoSujeto = d.CodigoSujeto "
                + "    ) do "
                + "    ON t.CodigoDocente = do.CodigoDocente "
                + "WHERE "
                + "    t.CodigoUniversitario LIKE ? "
                + "    AND t.Anio = ? "
                + "    AND t.Semestre = ?;";

        // Obtener el EntityManager de JPA
        EntityManager em = getEntityManager();
        // Preparar la consulta usando JPA
        Query nativeQuery = em.createNativeQuery(query);

        // Setear los parámetros de la consulta
        nativeQuery.setParameter(1, "%" + params.getCodiEscuela() + "%");  // Asumí que codiEscuela es un substring
        nativeQuery.setParameter(2, params.getCodiAño());
        nativeQuery.setParameter(3, params.getCodiSemestre());

        // Ejecutar la consulta y obtener la lista de resultados
        return nativeQuery.getResultList();  // Devuelve una lista de resultados
    }

}
