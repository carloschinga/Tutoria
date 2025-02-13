/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author USER
 */
public class AlumnoDAO extends JpaPadre {

    public AlumnoDAO(String empresa) {
        super(empresa);
    }

    @Override
    public EntityManager getEntityManager() {
        return super.getEntityManager();
    }

    public JSONArray obtenerAlumnosXFacultad(String nombreAlumno, String codiFacu) {
        EntityManager em = getEntityManager();
        try {
            String nombreAlumnoLike = "%" + nombreAlumno + "%";

            String sql = "SELECT CodigoUniversitario, CONCAT(Apellidos, ' ', Nombres) as Alumno "
                    + "FROM Alumnos "
                    + "WHERE CodigoUniversitario LIKE ? "
                    + "AND (Apellidos LIKE ? OR Nombres LIKE ?) "
                    + "ORDER BY Apellidos, Nombres";

            Query query = em.createNativeQuery(sql);
            query.setParameter(1, codiFacu + "%");
            query.setParameter(2, nombreAlumnoLike);
            query.setParameter(3, nombreAlumnoLike);

            // Obtener la lista de resultados correctamente
            List<Object[]> results = query.getResultList();

            // Crear un JSONArray correctamente
            JSONArray jsonArray = new JSONArray();
            for (Object[] result : results) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("CodigoUniversitario", result[0]); // Primer campo
                jsonObject.put("Alumno", result[1]); // Segundo campo
                jsonArray.put(jsonObject); // Agregar al JSONArray
            }

            return jsonArray;

        } catch (JSONException ex) { // Capturar errores generales
            ex.printStackTrace(); // Imprimir el error en la consola para depuración
            return new JSONArray(); // Devolver un JSON vacío en caso de error
        } finally {
            em.close();
        }
    }

}
