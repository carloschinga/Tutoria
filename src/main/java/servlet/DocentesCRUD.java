package servlet;

import dao.DocenteJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet para la gestión de docentes.
 */
@WebServlet(name = "DocentesCRUD", urlPatterns = {"/DocentesCRUD"})
public class DocentesCRUD extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false); // No crea una nueva sesión si no existe
            
            if (session == null || session.getAttribute("empr") == null) {
                out.print("{\"resultado\":\"error\",\"mensaje\":\"nosession\"}");
                return;
            }

            try {
                String empr = session.getAttribute("empr").toString();
                String opcion = request.getParameter("opcion");
                DocenteJpaController dao = new DocenteJpaController(empr);

                switch (opcion) {
                    case "1": // Lista de docentes por facultad
                       
                            String facultad = session.getAttribute("facultad") != null ? session.getAttribute("facultad").toString() : "";
                            out.print("{\"data\":" + dao.BuscarFacultad(facultad) + ",\"resultado\":\"ok\"}");
                       
                        break;

                    case "2": // Buscar docente por código
                        Object codigoDocenteObj = session.getAttribute("codigoDocente");
                        if (codigoDocenteObj == null) {
                            out.print("{\"resultado\":\"error\",\"mensaje\":\"codigoDocente no encontrado en la sesión\"}");
                            return;
                        }
                        
                        String codigoDocente = codigoDocenteObj.toString();
                        String nombreDocente = dao.Buscarxcodigo(codigoDocente);

                        if (nombreDocente == null || nombreDocente.isEmpty()) {
                            out.print("{\"resultado\":\"error\",\"mensaje\":\"Docente no encontrado\"}");
                        } else {
                            out.print("{\"resultado\":\"ok\",\"nombre\":\"" + nombreDocente + "\"}");
                        }
                        break;

                    default:
                        out.print("{\"resultado\":\"error\",\"mensaje\":\"noproce\"}");
                        break;
                }
            } catch (Exception e) {
                out.print("{\"resultado\":\"error\",\"mensaje\":\"Error general: " + e.getMessage() + "\"}");
            }
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
        return "Servlet para la gestión de docentes.";
    }
}
