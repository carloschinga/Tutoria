/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import com.google.gson.Gson;
import dao.ReporteAlumnosjpaController;
import dao.ReporteTutoresjpaController;
import dto.ReporteAlumnos;
import dto.ReporteTutores;
import dto.ReporteTutoresParametro;
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
 * @author Carlos
 */
@WebServlet(name = "ReporteListaAlumnos", urlPatterns = {"/ReporteListaAlumnos"})
public class ReporteListaAlumnos extends HttpServlet {

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
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession(true);
            if (session == null || session.getAttribute("empr") == null) {
                response.getWriter().write("Error: No estás autenticado. Inicia sesión.");
                return;
            }

            // Obtener los parámetros de la solicitud
            String escuela = request.getParameter("escuela");
            String codiEscuela = request.getParameter("codiEscuela");
            String codiAño = request.getParameter("codiAño");
            String codiSemestre = request.getParameter("codiSemestre");
            String codigoTutor = request.getParameter("codigoTutor"); // Nuevo parámetro

            // Validar los parámetros
            if (codiEscuela == null || codiAño == null || codiSemestre == null || escuela == null || codigoTutor == null) {
                response.getWriter().write("Faltan parámetros para generar el reporte.");
                return;
            }

            // Crear DTO para los parámetros
            ReporteTutoresParametro reporteTutoresParametro = new ReporteTutoresParametro();
            reporteTutoresParametro.setCodiEscuela(codiEscuela);
            reporteTutoresParametro.setCodiAño(codiAño);
            reporteTutoresParametro.setCodiSemestre(codiSemestre);
            reporteTutoresParametro.setEscuela(escuela);
            reporteTutoresParametro.setCodigoTutor(codigoTutor); // Se almacena el tutor

            // Obtener la base de datos desde la sesión
            String empr = (String) session.getAttribute("empr");
            if (empr == null) {
                response.getWriter().write("No se ha especificado con qué BD trabajar.");
                return;
            }

            // Instanciar el DAO y obtener datos
            ReporteAlumnosjpaController reporteAlumnosDAO = new ReporteAlumnosjpaController(empr);
            List<ReporteAlumnos> listaReportes = reporteAlumnosDAO.getReportData(reporteTutoresParametro);

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
