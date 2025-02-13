package servlet;

import com.google.gson.Gson;
import dao.ReporteIncidenciasTutoria;
import dto.IncidenciaTutoriaDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ReporteIncidencias", urlPatterns = {"/ReporteIncidencias"})
public class ReporteIncidencias extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession(true);
            if (session == null || session.getAttribute("empr") == null) {
                response.getWriter().write("{\"resultado\":\"ERROR\", \"message\":\"No estás autenticado. Inicia sesión.\"}");
                return;
            }

            // Obtener el parámetro de búsqueda
            String termino = request.getParameter("termino");

            // Validar el parámetro
            if (termino == null || termino.trim().isEmpty()) {
                response.getWriter().write("{\"resultado\":\"ERROR\", \"message\":\"Debe ingresar un nombre o apellido.\"}");
                return;
            }

            // Obtener la base de datos desde la sesión
            String empr = (String) session.getAttribute("empr");
            if (empr == null) {
                response.getWriter().write("{\"resultado\":\"ERROR\", \"message\":\"No se ha especificado con qué BD trabajar.\"}");
                return;
            }

            // Instanciar el DAO y obtener datos
            ReporteIncidenciasTutoria reporteIncidenciasDAO = new ReporteIncidenciasTutoria(empr);
            List<IncidenciaTutoriaDTO> listaReportes = reporteIncidenciasDAO.obtenerIncidencias(termino);

            // Enviar respuesta en formato JSON
            out.println("{\"resultado\":\"OK\", \"lista\":" + new Gson().toJson(listaReportes) + "}");
        } catch (IOException e) {
            out.println("{\"resultado\":\"ERROR\", \"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para generar reportes de incidencias de tutoría";
    }
}
