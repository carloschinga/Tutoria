/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.IncidenciasTutoriaJpaController;
import dao.SemestreAcademicoJpaController;
import dto.IncidenciasTutoria;
import dto.IncidenciasTutoriaPK;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import org.json.JSONObject;
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
@WebServlet(name = "IncidenciasTutoriaCRUD", urlPatterns = {"/IncidenciasTutoriaCRUD"})
public class IncidenciasTutoriaCRUD extends HttpServlet {

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
                    IncidenciasTutoriaJpaController dao = new IncidenciasTutoriaJpaController(empr);

                    switch (opcion) {
                        case "1": //Agregar
                                StringBuilder sb = new StringBuilder();
                                try (BufferedReader reader = request.getReader()) {
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        sb.append(line);
                                    }
                                }
                                String body = sb.toString();
                                JSONObject jsonObj = new JSONObject(body);
                                String coduniv = request.getParameter("coduniv");
                                String codsede = request.getParameter("codsede");
                                String tipo = request.getParameter("tipo");
                                SemestreAcademicoJpaController semesdao = new SemestreAcademicoJpaController(empr);
                                Object[] semestre = semesdao.obtenerSemestreActual();
                                int item = dao.obtenerUltItm(coduniv, codsede, (String) semestre[0], (Character) semestre[1]) + 1;
                                if (item > 0) {
                                    IncidenciasTutoriaPK objpk = new IncidenciasTutoriaPK(codsede, coduniv, (String) semestre[0], (Character) semestre[1], item);
                                    IncidenciasTutoria obj = new IncidenciasTutoria(objpk);
                                    String codigodocente = session.getAttribute("codigoDocente").toString();
                                    obj.setCodigoDocente(Integer.parseInt(codigodocente));
                                    obj.setCodigoIncidencia(Integer.parseInt(tipo));
                                    obj.setEstado("S");
                                    obj.setFecha(new Date());
                                    obj.setObservacion(URLDecoder.decode(jsonObj.getString("observacion"), "UTF-8"));
                                    dao.create(obj);
                                    out.print("{\"resultado\":\"ok\"}");
                                } else {
                                    out.print("{\"resultado\":\"error\",\"mensaje\":\"errsql\"}");
                                }
                            break;
                        case "2": //Lista las incidencias de un alumno
                                  coduniv = request.getParameter("coduniv");
                                 codsede = request.getParameter("codsede");

                                out.print("{\"data\":" + dao.ListarIncidencias(coduniv, codsede) + ",\"resultado\":\"ok\"}");
                       
                            break;
                        case "3": //modificar
                                  sb = new StringBuilder();
                                try (BufferedReader reader = request.getReader()) {
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        sb.append(line);
                                    }
                                }
                                 body = sb.toString();
                                 jsonObj = new JSONObject(body);
                                 coduniv = request.getParameter("coduniv");
                                 codsede = request.getParameter("codsede");
                                 tipo = request.getParameter("tipo");
                                 item = Integer.parseInt(request.getParameter("item"));
                                 semesdao = new SemestreAcademicoJpaController(empr);
                                 semestre = semesdao.obtenerSemestreActual();
                                IncidenciasTutoriaPK objpk = new IncidenciasTutoriaPK(codsede, coduniv, (String) semestre[0], (Character) semestre[1], item);
                                IncidenciasTutoria obj = dao.findIncidenciasTutoria(objpk);
                                String codigodocente = session.getAttribute("codigoDocente").toString();
                                obj.setCodigoDocente(Integer.parseInt(codigodocente));
                                obj.setCodigoIncidencia(Integer.parseInt(tipo));
                                obj.setFecha(new Date());
                                obj.setObservacion(URLDecoder.decode(jsonObj.getString("observacion"), "UTF-8"));
                                dao.edit(obj);
                                out.print("{\"resultado\":\"ok\"}");
                           
                            break;
                        case "4"://eliminar
                                 coduniv = request.getParameter("coduniv");
                                 codsede = request.getParameter("codsede");
                                 item = Integer.parseInt(request.getParameter("item"));
                                 semesdao = new SemestreAcademicoJpaController(empr);
                                 semestre = semesdao.obtenerSemestreActual();
                                 objpk = new IncidenciasTutoriaPK(codsede, coduniv, (String) semestre[0], (Character) semestre[1], item);
                                 obj = dao.findIncidenciasTutoria(objpk);
                                obj.setEstado("N");
                                dao.edit(obj);
                                out.print("{\"resultado\":\"ok\"}");
                           
                            break;
                        default:
                            out.print("{\"resultado\":\"error\",\"mensaje\":\"noproce\"}");
                    }
                } else {
                    out.print("{\"resultado\":\"error\",\"mensaje\":\"nosession\"}");
                }
            } catch (Exception e) {
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
