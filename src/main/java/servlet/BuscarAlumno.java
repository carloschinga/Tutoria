/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.AlumnoDAO;
import dao.TutorDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;

/**
 *
 * @author USER
 */
@WebServlet(name = "BuscarAlumno", urlPatterns = {"/buscaralumno"})
public class BuscarAlumno extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false); // No crear una sesión si no existe

            if (session == null || session.getAttribute("empr") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"resultado\": \"error\", \"mensaje\": \"No estás autenticado. Inicia sesión.\"}");
                return;
            }

            // Obtener parámetros
            String nombreAlumno = request.getParameter("nombre");

            // Validaciones básicas
            if (nombreAlumno == null || nombreAlumno.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"resultado\": \"error\", \"mensaje\": \"El nombre del alumno es obligatorio.\"}");
                return;
            }

            // Obtener la base de datos desde la sesión
            String empr = (String) session.getAttribute("empr");
            String codiFacu = (String) session.getAttribute("facultad");

            // Consultar el tutor
            AlumnoDAO alumnoDAO = new AlumnoDAO(empr);
            JSONArray resultado = alumnoDAO.obtenerAlumnosXFacultad(
                    nombreAlumno,
                    codiFacu
            );

            // Devolver la respuesta en formato JSON
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(resultado.toString());

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"resultado\": \"error\", \"mensaje\": \"" + e.getMessage() + "\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Para que POST y GET funcionen igual
    }

    @Override
    public String getServletInfo() {
        return "Servlet para buscar el tutor de un alumno por año y semestre.";
    }
}
