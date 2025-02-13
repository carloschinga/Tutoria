/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.TutoriaJpaController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author shaho
 */
@WebServlet(name = "TutoriaCRUD", urlPatterns = {"/TutoriaCRUD"})
public class TutoriaCRUD extends HttpServlet {

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
            try {
                HttpSession session = request.getSession(true);
                Object emprObj = session.getAttribute("empr");
                if (emprObj != null) {
                    String empr = emprObj.toString();
                    String opcion = request.getParameter("opcion");
                    TutoriaJpaController dao = new TutoriaJpaController(empr);

                    switch (opcion) {
                        case "1": //Insertar tutores
                            String docente = request.getParameter("docente");
                            StringBuilder sb = new StringBuilder();
                            try (BufferedReader reader = request.getReader()) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            }
                            String body = sb.toString();
                            if (!"".equals(body)) {
                                String resultado = dao.insertarRegistrosDesdeJson(body, docente);
                                if (resultado.equals("S")) {
                                    out.print("{\"resultado\":\"ok\"}");
                                } else {
                                    out.print("{\"resultado\":\"error\",\"mensaje\":\"errorsql\"}");
                                }
                            } else {
                                out.print("{\"resultado\":\"error\",\"mensaje\":\"faltadata\"}");
                            }

                            break;
                        case "2": //Modificar tutores
                            sb = new StringBuilder();
                            try (BufferedReader reader = request.getReader()) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            }
                            body = sb.toString();
                            if (!"".equals(body)) {
                                String resultado = dao.modificarlista(body);
                                if (resultado.equals("S")) {
                                    out.print("{\"resultado\":\"ok\"}");
                                } else {
                                    out.print("{\"resultado\":\"error\",\"mensaje\":\"errorsql\"}");
                                }
                            } else {
                                out.print("{\"resultado\":\"error\",\"mensaje\":\"faltadata\"}");
                            }

                            break;
                        case "3": //Listar tutorados por docente
                            docente = session.getAttribute("codigoDocente").toString();
                            String codigoDocente = session.getAttribute("codigoDocente").toString();
                            out.print("{\"data\":" + dao.listarPorDocente(Integer.parseInt(codigoDocente)) + ",\"resultado\":\"ok\"}");

                            break;
                        default:
                            out.print("{\"resultado\":\"error\",\"mensaje\":\"noproce\"}");
                            break;
                    }
                } else {
                    out.print("{\"resultado\":\"error\",\"mensaje\":\"nosession\"}");
                }
            } catch (IOException | NumberFormatException e) {
                out.print("{\"resultado\":\"error\",\"mensaje\":\"Error general\"}");
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
