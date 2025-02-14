/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.InfoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author USER
 */
@WebServlet(name = "Info", urlPatterns = {"/info"})
public class Info extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            EntityManagerFactory emf = null;
            EntityManager em = null;
            try {
                emf = Persistence.createEntityManagerFactory("com.mycompany_tutoria_war_1.0-SNAPSHOTPU");
                em = emf.createEntityManager();

                InfoDAO inf = new InfoDAO();
                String version = inf.getSQLServerInfo(em);
                String estadotls = inf.getTLSStatus(em);
                String resultado = version + estadotls;
               
                // Separar los dos objetos JSON en resultado
                int splitIndex = resultado.lastIndexOf("}{");
                String json1 = resultado.substring(0, splitIndex + 1);
                String json2 = resultado.substring(splitIndex + 1);

                // Convertir en JSONObject
                JSONObject jsonVersion = new JSONObject(version);
                JSONObject jsonPart1 = new JSONObject(json1);
                JSONObject jsonPart2 = new JSONObject(json2);

                // Combinar los objetos
                JSONObject mergedJson = new JSONObject();
                mergedJson.put("SQLServer", jsonVersion.getJSONObject("SQLServer"));
                mergedJson.put("Protocol", jsonPart2.getString("Protocol"));
                mergedJson.put("Status", jsonPart2.getString("Status"));
                mergedJson.put("resultado", "ok");

                // Convertir a string JSON
                String jsonFinal = mergedJson.toString(4); // Formato con indentación de 4 espacios

                out.println(jsonFinal);

            } catch (PersistenceException e) {
                JSONObject mergedJson = new JSONObject();
                mergedJson.put("resultado", "error");
                mergedJson.put("mensaje", e.getMessage());
                out.println(mergedJson);
            } finally {
                if (em != null) {
                    em.close();
                }
                if (emf != null) {
                    emf.close();
                }
            }
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
