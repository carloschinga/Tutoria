/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.AsistenciaTutoriaJpaController;
import dao.SemestreAcademicoJpaController;
import java.io.BufferedReader;
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
 * @author shaho
 */
@WebServlet(name = "AsistenciaTutoriaCRUD", urlPatterns = {"/AsistenciaTutoriaCRUD"})
public class AsistenciaTutoriaCRUD extends HttpServlet {

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
                    AsistenciaTutoriaJpaController dao = new AsistenciaTutoriaJpaController(empr);

                    switch (opcion) {
                        case "1": //Lista de alumnos por sesion, asistencias, lista todos los almunos
                            String sesion = request.getParameter("sesion");
                            String codigodocente = session.getAttribute("codigoDocente").toString();

                            out.print("{\"data\":" + dao.BuscarAsistencias(Integer.parseInt(codigodocente), Integer.parseInt(sesion)) + ",\"resultado\":\"ok\"}");
                            break;
                        case "2"://cabecera para cuando se entra a la gestion de sesiones
                            sesion = request.getParameter("sesion");
                            codigodocente = session.getAttribute("codigoDocente").toString();

                            SemestreAcademicoJpaController semesdao = new SemestreAcademicoJpaController(empr);
                            Object[] semestre = semesdao.obtenerSemestreActual();

                            out.print(dao.ContarAsistenciasPorActividad(codigodocente, (String) semestre[0], (Character) semestre[1], Integer.parseInt(sesion)));
                            break;
                        case "3"://Asignar alumnos para las actividades
                            codigodocente = session.getAttribute("codigoDocente").toString();
                            sesion = request.getParameter("sesion");
                            StringBuilder sb = new StringBuilder();
                            try (BufferedReader reader = request.getReader()) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            }
                            String body = sb.toString();
                            semesdao = new SemestreAcademicoJpaController(empr);
                            semestre = semesdao.obtenerSemestreActual();
                            if (!"".equals(body)) {
                                String resultado = dao.AsignarAlumnos(body, codigodocente, (String) semestre[0], (Character) semestre[1], sesion);
                                if (resultado.equals("S")) {
                                    out.print("{\"resultado\":\"ok\"}");
                                } else {
                                    out.print("{\"resultado\":\"error\",\"mensaje\":\"errorsql\"}");
                                }
                            } else {
                                out.print("{\"resultado\":\"error\",\"mensaje\":\"faltadata\"}");
                            }
                            break;
                        case "4": //Lista de alumnos por sesion, asistencias, solo alumnos asignados
                            sesion = request.getParameter("sesion");
                            codigodocente = session.getAttribute("codigoDocente").toString();

                            out.print("{\"data\":" + dao.BuscarAsistenciassoloasignados(Integer.parseInt(codigodocente), Integer.parseInt(sesion)) + ",\"resultado\":\"ok\"}");
                            break;
                        case "5"://registro de asistencias para las actividades
                            codigodocente = session.getAttribute("codigoDocente").toString();
                            sesion = request.getParameter("sesion");
                            sb = new StringBuilder();
                            try (BufferedReader reader = request.getReader()) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            }
                            body = sb.toString();
                            semesdao = new SemestreAcademicoJpaController(empr);
                            semestre = semesdao.obtenerSemestreActual();
                            if (!"".equals(body)) {
                                String resultado = dao.RegistrarAsistencias(body, codigodocente, (String) semestre[0], (Character) semestre[1], sesion);
                                if (resultado.equals("S")) {
                                    out.print("{\"resultado\":\"ok\"}");
                                } else {
                                    out.print("{\"resultado\":\"error\",\"mensaje\":\"errorsql\"}");
                                }
                            } else {
                                out.print("{\"resultado\":\"error\",\"mensaje\":\"faltadata\"}");
                            }
                            break;
                        default:
                            out.print("{\"resultado\":\"error\",\"mensaje\":\"noproce\"}");
                            break;
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
