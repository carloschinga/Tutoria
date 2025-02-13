/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.ReporteAlumnos;
import dto.ReporteTutoresParametro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class ReporteAlumnosjpaController extends JpaPadre {

    public ReporteAlumnosjpaController(String empresa) {
        super(empresa); // Llamamos al constructor de la clase JpaPadre
    }

    // Método para obtener el EntityManager desde la configuración de persistence.xml
    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager(); // Se aprovecha el EntityManager proporcionado por JpaPadre
    }

    // Método para obtener los datos del reporte usando los parámetros del DTO
    public List<ReporteAlumnos> getReportData(ReporteTutoresParametro params) {
        String query = "SELECT DISTINCT "
                + "    UPPER(CONCAT(a.Nombres, ' ', a.Apellidos)) AS NombreAlumno, "
                + "    m.Ciclo AS Ciclo, "
                + "    a.CodigoUniversitario AS Codigo_Universitario "
                + "FROM "
                + "    Tutoria t "
                + "INNER JOIN "
                + "    Matriculas m "
                + "    ON t.CodigoUniversitario = m.CodigoUniversitario "
                + "    AND m.Anio = t.Anio "
                + "    AND m.Semestre = t.Semestre "
                + "    AND m.TipoMatricula <> 10 "
                + "INNER JOIN "
                + "    Escuelas e "
                + "    ON e.CodigoEscuela = SUBSTRING(t.CodigoUniversitario, 1, 4) "
                + "INNER JOIN "
                + "    (SELECT "
                + "         d.CodigoDocente, "
                + "         UPPER(CONCAT(s.APaterno, ' ', s.AMaterno, ' ', s.Nombre1, ' ', s.Nombre2)) AS Nombre "
                + "     FROM "
                + "         Sujeto s "
                + "     INNER JOIN "
                + "         Docente d "
                + "         ON s.CodigoSujeto = d.CodigoSujeto "
                + "    ) do "
                + "    ON t.CodigoDocente = do.CodigoDocente "
                + "INNER JOIN "
                + "    Alumnos a "
                + "    ON t.CodigoUniversitario = a.CodigoUniversitario "
                + "WHERE "
                + "    t.Estado = 'S' "
                + "   and  t.CodigoUniversitario LIKE ? "
                + "    AND t.Anio = ? "
                + "    AND t.Semestre = ? "
                + "    AND t.CodigoDocente = ?"
                + "    order by NombreAlumno,Ciclo,Codigo_Universitario";

        // Obtener el EntityManager de JPA
        EntityManager em = getEntityManager();
        try {
            // Preparar la consulta usando JPA
            Query nativeQuery = em.createNativeQuery(query);

            // Setear los parámetros de la consulta
            nativeQuery.setParameter(1, "%" + params.getCodiEscuela() + "%");  // Asumí que codiEscuela es un substring
            nativeQuery.setParameter(2, params.getCodiAño());
            nativeQuery.setParameter(3, params.getCodiSemestre());
            nativeQuery.setParameter(4, params.getCodigoTutor()); // Se agrega el parámetro del tutor

            // Ejecutar la consulta y obtener la lista de resultados
            return nativeQuery.getResultList();  // Devuelve una lista de resultados
        } finally {
            em.close();
        }
    }
}
