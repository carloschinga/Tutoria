/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.ActividadTutoriaJpaController;
import dao.SemestreAcademicoJpaController;
import dto.ActividadTutoria;
import dto.ActividadTutoriaPK;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
@WebServlet(name = "ActividadTutoriaCRUD", urlPatterns = {"/ActividadTutoriaCRUD"})
public class ActividadTutoriaCRUD extends HttpServlet {

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
                    ActividadTutoriaJpaController dao = new ActividadTutoriaJpaController(empr);

                    switch (opcion) {
                        case "1": //Agregar Actividad
                            String actividad = request.getParameter("actividad");
                            String tipo = request.getParameter("tipo");
                            String fecha = request.getParameter("fecha");
                            String lugar = request.getParameter("lugar");

                            SemestreAcademicoJpaController semesdao = new SemestreAcademicoJpaController(empr);
                            Object[] semestre = semesdao.obtenerSemestreActual();
                            String codigodocente = session.getAttribute("codigoDocente").toString();
                            int item = dao.obtenerUltItm(Integer.parseInt(codigodocente), (String) semestre[0], (Character) semestre[1]);
                            if (item >= 0) {
                                ActividadTutoriaPK actpk = new ActividadTutoriaPK(Integer.parseInt(codigodocente), (String) semestre[0], (Character) semestre[1], item + 1);
                                ActividadTutoria act = new ActividadTutoria(actpk);
                                act.setActividad(actividad);
                                act.setCodigoTipoActividad(Integer.parseInt(tipo));

                                DateTimeFormatter formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                                LocalDateTime fechaHoraLocalDateTime = LocalDateTime.parse(fecha, formatterInput);
                                Date fechaHoraDate = Date.from(fechaHoraLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
                                act.setFechaActividad(fechaHoraDate);
                                act.setEstado("S");
                                act.setLugar(lugar);
                                dao.create(act);
                                out.print("{\"resultado\":\"ok\"}");
                            } else {
                                out.print("{\"resultado\":\"error\",\"mensaje\":\"errordb\"}");
                            }

                            break;
                        case "2": // listar actividades
                            semesdao = new SemestreAcademicoJpaController(empr);
                            codigodocente = session.getAttribute("codigoDocente").toString();
                            semestre = semesdao.obtenerSemestreActual();
                            String lista = dao.Listar(Integer.parseInt(codigodocente), (String) semestre[0], (Character) semestre[1]);
                            out.print("{\"resultado\":\"ok\",\"data\":" + lista + "}");

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
