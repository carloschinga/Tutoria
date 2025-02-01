package dao;

import dto.TutorDto;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class TutorDAO extends JpaPadre {

    public TutorDAO(String empresa) {
        super(empresa); // Llamamos al constructor de la clase JpaPadre
    }

    // Método para obtener el EntityManager desde la configuración de persistence.xml
    public EntityManager getEntityManager() {
        return super.getEntityManager(); // Se aprovecha el EntityManager proporcionado por JpaPadre
    }

    // Método para obtener el tutor del alumno según el nombre del alumno
    public TutorDto getTutorByAlumno(String nombreAlumno) {
       String query = "SELECT DISTINCT " +
               "    a.Nombre AS Alumno, " +
               "    UPPER(CONCAT(s.APaterno, ' ', s.AMaterno, ' ', s.Nombre1, ' ', s.Nombre2)) AS TUTOR, " +
               "    m.Ciclo AS ciclo " +
               "FROM " +
               "    Tutoria t " +
               "    INNER JOIN Docente d ON d.CodigoDocente = t.CodigoDocente " +
               "    INNER JOIN Sujeto s ON d.CodigoSujeto = s.CodigoSujeto " +
               "    INNER JOIN Matriculas m " +
               "        ON t.CodigoUniversitario = m.CodigoUniversitario " +
               "        AND m.Anio = t.Anio " +
               "        AND m.Semestre = t.Semestre " +
               "    INNER JOIN Escuelas e " +
               "        ON e.CodigoEscuela = SUBSTRING(t.CodigoUniversitario, 1, 4) " +
               "    INNER JOIN ( " +
               "        SELECT " +
               "            a.CodigoUniversitario, " +
               "            UPPER(CONCAT(a.Nombres, ' ', a.Apellidos)) AS Nombre " +
               "        FROM Alumnos a " +
               "    ) a ON a.CodigoUniversitario = t.CodigoUniversitario " +
               "WHERE a.Nombre LIKE ?";


        // Obtener el EntityManager de JPA
        EntityManager em = getEntityManager();
        
        // Preparar la consulta usando JPA (consulta nativa SQL)
        Query nativeQuery = em.createNativeQuery(query);

        // Setear el parámetro de la consulta (nombre del alumno)
        nativeQuery.setParameter(1, "%" + nombreAlumno + "%");

        // Ejecutar la consulta y obtener el resultado
        List<Object[]> result = nativeQuery.getResultList();

        if (result.isEmpty()) {
            return null; // Si no se encuentra ningún tutor, retornamos null
        }

        // Tomar el primer resultado
        Object[] tutorData = result.get(0);  // Primer resultado

        // Crear el objeto TutorDTO con los datos obtenidos
        TutorDto tutor = new TutorDto();
        tutor.setAlumno(tutorData[0].toString());  // Nombre del alumno
        tutor.setTutor(tutorData[1].toString());   // Nombre del tutor
        tutor.setCiclo(tutorData[2].toString());   // Nombre del ciclo

        return tutor;  // Retornar el DTO con los datos del tutor
    }
}

