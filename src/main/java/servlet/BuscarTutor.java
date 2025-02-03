package servlet;

import dao.TutorDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

@WebServlet(name = "BuscarTutor", urlPatterns = {"/BuscarTutor"})
public class BuscarTutor extends HttpServlet {

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
            String anioStr = request.getParameter("anio");
            String semestreStr = request.getParameter("semestre");

            // Validaciones básicas
            if (nombreAlumno == null || nombreAlumno.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"resultado\": \"error\", \"mensaje\": \"El nombre del alumno es obligatorio.\"}");
                return;
            }

            int anio = 0, semestre = 0;
            try {
                anio = Integer.parseInt(anioStr);
                semestre = Integer.parseInt(semestreStr);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"resultado\": \"error\", \"mensaje\": \"Año y semestre deben ser números válidos.\"}");
                return;
            }

            // Obtener la base de datos desde la sesión
            String empr = (String) session.getAttribute("empr");

            // Consultar el tutor
            TutorDAO tutorDAO = new TutorDAO(empr);
            JSONObject resultado = tutorDAO.obtenerTutoresPorAnioSemestre(anio, semestre, nombreAlumno);

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

