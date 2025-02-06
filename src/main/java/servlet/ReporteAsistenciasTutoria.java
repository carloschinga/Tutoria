/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import com.google.gson.Gson;
import dao.ReporteAsistencias;
import dto.ReporteActividad;
import dto.ReporteAsistenciaTutoria;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author san21
 */
@WebServlet(name = "ReporteAsistenciasTutoria", urlPatterns = {"/ReporteAsistenciasTutoria"})
public class ReporteAsistenciasTutoria extends HttpServlet {

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
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession(true);
            if (session == null || session.getAttribute("empr") == null) {
                response.getWriter().write("{\"resultado\":\"ERROR\", \"message\":\"No estás autenticado. Inicia sesión.\"}");
                return;
            }

            // Obtener los parámetros de la solicitud
            String codiAño = request.getParameter("anio");
            String codiSemestre = request.getParameter("semestre");
            String sesion = request.getParameter("sesion");
            String codiDoce= session.getAttribute("codigoDocente").toString();

            // Validar los parámetros
            if (codiAño == null || codiSemestre == null || sesion == null) {
                response.getWriter().write("{\"resultado\":\"ERROR\", \"message\":\"Faltan parámetros para generar el reporte.\"}");
                return;
            }

            // Obtener la base de datos desde la sesión
            String empr = (String) session.getAttribute("empr");
            if (empr == null) {
                response.getWriter().write("{\"resultado\":\"ERROR\", \"message\":\"No se ha especificado con qué BD trabajar.\"}");
                return;
            }

            // Instanciar el DAO y obtener datos
            ReporteAsistencias reporteasistenciasdDAO = new ReporteAsistencias(empr);
            List<ReporteAsistenciaTutoria> listaReportes = reporteasistenciasdDAO.obtenerAsistenciaPorDocente(codiDoce,codiAño,codiSemestre,sesion);

            

            // Enviar respuesta en formato JSON
            out.println("{\"resultado\":\"OK\", \"lista\":" + new Gson().toJson(listaReportes) + "}");
        } catch (IOException e) {
            out.println("{\"resultado\":\"ERROR\", \"message\":\"" + e.getMessage() + "\"}");
        }

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