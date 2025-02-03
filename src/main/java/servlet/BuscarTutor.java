/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;
import dao.TutorDAO;
import dto.TutorDto;
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
 * @author Carlos
 */
@WebServlet(name = "BuscarTutor", urlPatterns = {"/BuscarTutor"})
public class BuscarTutor extends HttpServlet {

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

            // Obtener el nombre del alumno desde la solicitud
            String nombreAlumno = request.getParameter("nombre");

            // Validar si el nombre del alumno es proporcionado
            if (nombreAlumno == null || nombreAlumno.trim().isEmpty()) {
                response.getWriter().write("Error: El nombre del alumno es necesario.");
                return;
            }

            // Instanciar el DAO
            String empr = (String) session.getAttribute("empr");
            if (empr == null) {
                response.getWriter().write("Error: No se ha especificado la base de datos.");
                return;
            }

            TutorDAO tutorDAO = new TutorDAO(empr);
            out.print(tutorDAO.obtenerTutoresPorAnioSemestre(0, 0, empr));

        } catch (IOException e) {
            response.getWriter().write("{\"resultado\":\"Error\", \"message\":\"" + e.getMessage() + "\"}");
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
