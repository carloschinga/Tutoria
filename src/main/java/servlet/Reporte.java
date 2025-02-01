/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.ReportejpaController;
import dto.ReporteTutores;
import dto.ReporteTutoresParametro;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author san21
 */
@WebServlet(name = "Reporte", urlPatterns = {"/Reporte"})
public class Reporte extends HttpServlet {

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
        try {
            // Verificar si la sesión existe y si el atributo 'empr' está presente
            HttpSession session = request.getSession(true);
            if (session == null || session.getAttribute("empr") == null) {
                // Si no está autenticado, enviar un mensaje de error al usuario
                response.getWriter().write("{\"resultado\":\"error\",\"mensaje\":\"No estás autenticado. Inicia sesión.\"}");
                return;
            }

            // Obtener el atributo 'empr' de la sesión
            String empr = "";
            Object emprObj = session.getAttribute("empr");
            if (emprObj != null) {
                empr = emprObj.toString();
            } else {
                response.getWriter().write("{\"resultado\":\"error\",\"mensaje\":\"No se pudo obtener el identificador de la empresa.\"}");
                return;
            }

            // Obtener los parámetros de la solicitud HTTP
            String codiEscuela = request.getParameter("codiEscuela");
            String codiAño = request.getParameter("codiAño");
            String codiSemestre = request.getParameter("codiSemestre");
            String escuela = request.getParameter("Escuela");

            // Verificar si los parámetros necesarios están presentes
            if (codiEscuela == null || codiAño == null || codiSemestre == null || escuela == null) {
                response.getWriter().write("{\"resultado\":\"error\",\"mensaje\":\"Faltan parámetros en la solicitud.\"}");
                return;
            }

            // Crear DTO con los parámetros recibidos
            ReporteTutoresParametro params = new ReporteTutoresParametro(codiEscuela, codiAño, codiSemestre, escuela);

            // Llamar al DAO para obtener los datos del reporte
            ReportejpaController dao = new ReportejpaController(empr);
            List<ReporteTutores> reportData = dao.getReportData(params);

            if (reportData.isEmpty()) {
                response.getWriter().write("{\"resultado\":\"error\",\"mensaje\":\"No se encontraron datos para el reporte.\"}");
                return;
            }

            // Crear el DataSource para JasperReports
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);

            // Parámetros adicionales para el reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("codiEscuela", codiEscuela);
            parameters.put("codiAño", codiAño);
            parameters.put("codiSemestre", codiSemestre);
            parameters.put("escuela", escuela);

            // Ruta al archivo .jasper
            String reportPath = getServletContext().getRealPath("WEB-INF/reportes/Reporte.jasper");

            // Verificar si el archivo .jasper existe en la ruta
            if (reportPath == null || reportPath.isEmpty()) {
                response.getWriter().write("{\"resultado\":\"error\",\"mensaje\":\"No se encontró el archivo de reporte en la ruta especificada.\"}");
                return;
            }

            // Llenar el reporte con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, dataSource);

            // Configurar la respuesta HTTP para PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=reporteTutores.pdf");

            // Obtener el flujo de salida
            OutputStream outStream = response.getOutputStream();

            // Exportar el reporte a PDF y enviarlo al cliente
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

            // Limpiar el flujo de salida
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Enviar un mensaje de error si ocurre una excepción
            response.getWriter().write("{\"resultado\":\"error\",\"mensaje\":\"Error al generar el reporte: " + e.getMessage() + "\"}");
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
