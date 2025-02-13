/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author san21
 */
public class PanelTutorDAO extends JpaPadre {

    public PanelTutorDAO(String empresa) {
        super(empresa);
    }

    public String existeTutoriaXFacultad(String codiDocente) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder squery = new StringBuilder();
            squery.append("SELECT DISTINCT t.CodigoDocente, sa.Anio, sa.Semestre ");  // Espacio al final
            squery.append("FROM SemestreAcademico sa ");  // Espacio al final
            squery.append("INNER JOIN Tutoria t ON sa.Semestre = t.Semestre ");  // Espacio al final
            squery.append("AND sa.Anio = t.Anio ");  // Espacio al final
            squery.append("AND t.Estado = 'S' ");  // Espacio al final
            squery.append("AND t.CodigoDocente = ? ");  // Espacio al final
            squery.append("AND sa.Activo = 1");  // No se necesita espacio aquí

            Query query = em.createNativeQuery(squery.toString());
            query.setParameter(1, codiDocente);

            List<Object[]> resultList = query.getResultList();

            JSONObject json = new JSONObject();
            if (!resultList.isEmpty()) {
                Object[] result = resultList.get(0);  // Tomamos el primer resultado
                json.put("Resultado", "ok");
                json.put("CodigoDocente", result[0]);
                json.put("Anio", result[1]);
                json.put("Semestre", result[2]);
            } else {
                json.put("Resultado", "Error");
            }

            return json.toString();

        } catch (JSONException e) {
            return "{\"Resultado\":\"Error\",\"mensaje\":\"" + e.getMessage() + "\"}";
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
