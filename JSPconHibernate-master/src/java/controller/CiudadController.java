/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.*;


/**
 *
 * @author Rodrigo
 */
public class CiudadController extends HttpServlet {
   
    boolean error=false;
    String mess="";

    private void doAction(HttpServletRequest request, boolean guardar)
    {
        error = false;
        Ciudad ciudad = new Ciudad();
        CiudadManager ciudadManager = new CiudadManager();
        HttpSession session = request.getSession(true);

        if(request.getParameter("ciudadId")!=null)
        {
            Long id=(long)0;
            String param = request.getParameter("ciudadId");

            if(param.length()>0)
                id = Long.parseLong(param);
            if(id!=0)
                ciudad.setId(id);
        }
        if(request.getParameter("ciudadNombre")!=null)
        {
            ciudad.setNombre(request.getParameter("ciudadNombre"));
        }
        if (guardar)
        {
            if(ciudadManager.saveCiudad(ciudad))
            {
                if (session.getAttribute("listaCiudades")!=null)
                {
                    session.setAttribute("listaCiudades", null);
                }
                List<Ciudad> listaCiudades  = ciudadManager.getCiudades();

                if(listaCiudades.size()>0)
                {
                    session.setAttribute("listaCiudades", listaCiudades);
                }
            }
            else
            {
                error = true;
                mess = ciudadManager.mess;
            }
        }
        else //Buscar
        {
            if (session.getAttribute("listaCiudades")!=null)
            {
                session.setAttribute("listaCiudades", null);
            }
            List<Ciudad> listaCiudades  = ciudadManager.searchCiudades(ciudad);

            if(listaCiudades.size()>0)
            {
                session.setAttribute("listaCiudades", listaCiudades);
            }
            else
            {
                error = true;
                mess = "No se encontraron Ciudades";
            }

        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String address="ListaCiudades.jsp";

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if(request.getParameter("saveCiudad")!=null)
        {
            address = "ListaCiudades.jsp";
            doAction(request, true);
            if(error)
            {
                HttpSession session = request.getSession();
                session.setAttribute("mess", mess);
                address = "AgrEditCiudad.jsp?error=1";
            }
        }
        else if (request.getParameter("saveAddCiudad")!=null)
        {
            address = "AgrEditCiudad.jsp";
            doAction(request, true);
            if(error)
            {
                HttpSession session = request.getSession();
                session.setAttribute("mess", mess);
                address = "AgrEditCiudad.jsp?error=1";
            }
        }
        else if (request.getParameter("deleteCiudad")!=null)
        {
            address = "ListaCiudades.jsp";
            if(request.getParameter("ciudadId")!=null)
            {
                Long id = Long.parseLong(request.getParameter("ciudadId")) ;
                CiudadManager em = new CiudadManager();
                Ciudad ciudad = em.getCiudad(id);
                try
                {
                    HttpSession session = request.getSession();
                    if(!em.deleteCiudad(ciudad))
                    {
                        session.setAttribute("mess", mess);
                        address = "EliminarCiudad.jsp?error=1";
                    }
                    if (session.getAttribute("listaCiudades")!=null)
                    {
                        session.setAttribute("listaCiudades", null);
                    }
                    List<Ciudad> listaCiudades  = em.getCiudades();

                    if(listaCiudades.size()>0)
                    {
                        session.setAttribute("listaCiudades", listaCiudades);
                    }
                }
                catch(Exception ex)
                {
                    String msg = ex.getLocalizedMessage();
                    HttpSession session = request.getSession();
                    session.setAttribute("mensajeError", msg);
                    address = "Error.jsp";
                }
            }
        }
        else if (request.getParameter("searchCiudades")!=null)
        {
            address = "ListaCiudades.jsp";
            doAction(request, false);
            if(error)
            {
                HttpSession session = request.getSession();
                session.setAttribute("mess", mess);
                address = "ListaCiudades.jsp?norecords=1";
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);

        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}