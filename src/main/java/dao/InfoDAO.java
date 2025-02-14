/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import org.json.JSONObject;

/**
 *
 * @author USER
 */
public class InfoDAO {

    @Transactional
    public String getSQLServerInfo(EntityManager entityManager) {
        EntityManager em = entityManager;
        try {
            String query = "SELECT SERVERPROPERTY('ProductVersion') AS [SQLServer.ProductVersion], "
                    + "SERVERPROPERTY('ProductLevel') AS [SQLServer.ProductLevel], "
                    + "SERVERPROPERTY('Edition') AS [SQLServer.Edition] "
                    + "FOR JSON PATH, WITHOUT_ARRAY_WRAPPER;";

            List<String> result = em.createNativeQuery(query).getResultList();

            return result.isEmpty() ? "{}" : result.get(0);
        } catch (Exception ex) {
            return "{}";
        }
    }

    @Transactional
    public String getTLSStatus(EntityManager entityManager) {
        EntityManager em = entityManager;
        try {
            String query = "DECLARE @RegPath VARCHAR(500); "
                    + "SET @RegPath = 'System\\CurrentControlSet\\Control\\SecurityProviders\\SCHANNEL\\Protocols\\TLS 1.0\\Server'; "
                    + "DECLARE @Enabled INT; "
                    + "EXEC master.dbo.xp_regread 'HKEY_LOCAL_MACHINE', @RegPath, 'Enabled', @Enabled OUTPUT; "
                    + "SELECT 'TLS 1.0' as Protocol, "
                    + "CASE "
                    + "    WHEN @Enabled = 1 THEN 'Habilitado' "
                    + "    WHEN @Enabled = 0 THEN 'Deshabilitado' "
                    + "    ELSE 'No configurado' "
                    + "END as Status "
                    + "FOR JSON PATH, WITHOUT_ARRAY_WRAPPER;";

            List<String> result = em.createNativeQuery(query).getResultList();

            return result.isEmpty() ? "{}" : result.get(0);
        } catch (Exception ex) {
            return "{}";
        }
    }

    public static void main(String[] args) {
    }

}
