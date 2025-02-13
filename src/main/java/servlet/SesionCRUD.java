package servlet;

import dao.SesionjpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SesionCRUD", urlPatterns = {"/SesionCRUD"})
public class SesionCRUD extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(true);
            Object emprObj = session.getAttribute("empr");
            if (emprObj != null) {
                String empresa = emprObj.toString();
                SesionjpaController dao = new SesionjpaController(empresa);
                String opcion = request.getParameter("opcion");

                switch (opcion) {
                    case "1": // Obtener sesiones 
                        String codiSeme= request.getParameter("semestre");
                        String codigoDoc= session.getAttribute("codigoDocente").toString();
                        out.print(dao.obtenerSesionesUnicas(codigoDoc,codiSeme));
                        break;
                    case "2": // Otras posibles operaciones futuras
                       String codiSemetre= request.getParameter("semestre");
                        String codigoDocente= request.getParameter("codigoDocente");
                        out.print(dao.obtenerSesionesUnicasdocente(codigoDocente,codiSemetre));
                        break;
                    default:
                        out.print("{\"resultado\":\"error\",\"mensaje\":\"noproce\"}");
                        break;
                }
            } else {
                out.print("{\"resultado\":\"error\",\"mensaje\":\"nosession\"}");
            }
        } catch (Exception e) {
            response.getWriter().print("{\"resultado\":\"error\",\"mensaje\":\"Error general\"}");
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
        return "Sesion CRUD Servlet";
    }
}