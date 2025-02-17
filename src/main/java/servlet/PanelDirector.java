/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.PanelDirectorDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author USER
 */
@WebServlet(name = "paneldirector", urlPatterns = {"/paneldirector"})
public class PanelDirector extends HttpServlet {

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

            String admLogin = request.getParameter("admLogin");
            String codiFacu = request.getParameter("codiFacu");
            String codidoce = request.getParameter("codiDocente");

            try {
                PanelDirectorDAO pdDAO = new PanelDirectorDAO("a");
                String resultado = pdDAO.existeDirectorTutoriaXFacultad(admLogin, codiFacu, codidoce);
                JSONObject jsonobj = new JSONObject(resultado);

                if (jsonobj.getString("Resultado").equals("ok")) {

                    HttpSession sesion = request.getSession(true);
                    sesion.setAttribute("director", admLogin);
                    sesion.setAttribute("facultad", codiFacu);
                    sesion.setAttribute("codigoDocente", codidoce);
                    sesion.setAttribute("empr", "a");
                    response.sendRedirect("paneldirector.html");

                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Código 403
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Acceso Denegado</title>");
                    out.println("<style>");
                    out.println("body { font-family: Arial, sans-serif; text-align: center; padding: 50px; }");
                    out.println("h2 { color: red; }");
                    out.println("</style>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h2>¡Acceso Denegado!</h2>");
                    out.println("<p>No tienes permiso para acceder a este módulo.</p>");
                    out.println("</body>");
                    out.println("</html>");
                }
            } catch (Exception ex) {
                out.print(ex.getMessage());
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
