package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.json.JSONException;
import org.json.JSONObject;

public class PanelDirectorDAO extends JpaPadre {

    public PanelDirectorDAO(String empresa) {
        super(empresa);
    }

    public String existeDirectorTutoriaXFacultad(String codiAdm, String codiFacu, String codiDocente) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder squery = new StringBuilder();
            squery.append("SELECT a.AdmLogin, ar.AccesoA, d.CodigoDocente ");
            squery.append("FROM Sujeto s ");
            squery.append("INNER JOIN Administradores a ON a.AdmLogin = s.DNI ");
            squery.append("INNER JOIN Docente d ON d.CodigoSujeto = s.CodigoSujeto ");
            squery.append("LEFT JOIN Administrativos ad ON ad.CodigoSujeto = s.CodigoSujeto ");
            squery.append("INNER JOIN Administradores_Rol ar ON ar.AdmCodigo = a.AdmCodigo AND ar.Habilitado = 1 ");
            squery.append("INNER JOIN Rol r ON r.CodigoRol = ar.CodigoRol ");
            squery.append("WHERE ar.CodigoRol = 21 ");
            squery.append("AND a.AdmLogin = ? ");
            squery.append("AND ar.AccesoA = ? ");
            squery.append("AND d.CodigoDocente = ?");

            Query query = em.createNativeQuery(squery.toString());
            query.setParameter(1, codiAdm);
            query.setParameter(2, codiFacu);
            query.setParameter(3, codiDocente);

            List<Object[]> resultList = query.getResultList();

            JSONObject json = new JSONObject();
            if (!resultList.isEmpty()) {
                Object[] result = resultList.get(0);
                json.put("Resultado", "ok");
                json.put("AdmLogin", result[0]);
                json.put("AccesoA", result[1]);
                json.put("CodigoDocente", result[2]);
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
