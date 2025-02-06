/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author san21
 */
public class SesionjpaController extends JpaPadre {

    public SesionjpaController(String empresa) {
        super(empresa);
    }

    public String obtenerSesionesUnicas(String codigoDocente, String codiSeme) {
        EntityManager em = getEntityManager();
        try {
            String queryString = "select Sesion,upper( Actividad ) \n"
                    + "from ActividadTutoria acti \n"
                    + "inner join SemestreAcademico seme on   seme.CodigoSemestre=?  and acti.CodigoDocente=? \n"
                    + "and acti.Anio=seme.Anio and acti.Semestre=seme.Semestre";
            Query query = em.createNativeQuery(queryString);
            query.setParameter(1, codiSeme);
            query.setParameter(2, codigoDocente);

            List<Object[]> resultados = query.getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Object[] fila : resultados) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("Sesion", fila[0]);
                jsonObj.put("Actividad", fila[1]);
                jsonArray.put(jsonObj);
            }
            return jsonArray.toString();
        } catch (Exception e) {
            return "{\"Resultado\":\"Error\",\"mensaje\":\"" + e.getMessage() + "\"}";
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
