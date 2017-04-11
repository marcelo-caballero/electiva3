/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Usuario;
import model.UsuarioManager;
//import javax.servlet.annotation.WebServlet;


/**
 *
 * @author Acer
 */
//@WebServlet(name="UsuarioController" ,urlPatterns={"/UsuarioController"})
public class UsuarioController extends HttpServlet {

    boolean error=false;
    String mess="";
    
    private void doAction(HttpServletRequest request, boolean guardar)
    {
        error = false;
        Usuario usuario = new Usuario();
        UsuarioManager usuarioManager = new UsuarioManager();
        HttpSession session = request.getSession(true);
        
       

       
        if(request.getParameter("usuarioCuenta").length() > 0)
        {

            usuario.setCuenta(request.getParameter("usuarioCuenta"));
            
        }
        if(request.getParameter("usuarioContrasena") != null)
        {
            if(request.getParameter("usuarioContrasena").length()>0)
                usuario.setContrasena(request.getParameter("usuarioContrasena"));
            
        }
        
        if (guardar)
        {
            if(usuarioManager.saveUsuario(usuario))
            {
               if (session.getAttribute("listaUsuarios")!=null)
                {
                    session.setAttribute("listaUsuarios", null);
                }
                List<Usuario> listaUsuarios  = usuarioManager.getUsuarios();

                if(listaUsuarios.size()>0)
                {
                    session.setAttribute("listaUsuarios", listaUsuarios);
                }
            }
            else
            {
                error = true;
                mess = usuarioManager.mess;
            }
        }
        else //Buscar
        {
            if (session.getAttribute("listaUsuarios")!=null)
            {
                session.setAttribute("listaUsuarios", null);
            }
            List<Usuario> listaUsuarios  = usuarioManager.searchUsuarios(usuario);

            if(listaUsuarios != null)
            {
                session.setAttribute("listaUsuarios", listaUsuarios);
            }
            else
            {
                error = true;
                mess = "No se encontro usuario";
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
        String address="ListaUsuarios.jsp";

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if(request.getParameter("saveUsuario")!=null)
        {
            address = "ListaUsuarios.jsp";
            doAction(request, true);
            if(error)
            {
                HttpSession session = request.getSession();
                session.setAttribute("mess", mess);
                address = "AgrEditUsuario.jsp?error=1";
            }
        }
        else if (request.getParameter("saveAddUsuario")!=null)
        {
            address = "AgrEditUsuario.jsp";
            doAction(request, true);
            if(error)
            {
                HttpSession session = request.getSession();
                session.setAttribute("mess", mess);
                address = "AgrEditUsuario.jsp?error=1";
            }
        }
        else if (request.getParameter("deleteUsuario")!=null)
        {
            address = "ListaUsuarios.jsp";
            if(request.getParameter("usuarioCuenta")!=null )
            {
                String cuenta = request.getParameter("usuarioCuenta") ;
                //String contrasena = request.getParameter("usuarioContrasena");
                UsuarioManager em = new UsuarioManager();
                Usuario usuario = em.getUsuario(cuenta);
                try
                {
                    HttpSession session = request.getSession();
                    if(!em.deleteUsuario(usuario))
                    {
                        session.setAttribute("mess", mess);
                        address = "EliminarUsuario.jsp?error=1";
                    }
                    if (session.getAttribute("listaUsuarios")!=null)
                    {
                        session.setAttribute("listaUsuarios", null);
                    }
                    List<Usuario> listaUsuarios  = em.getUsuarios();

                    if(listaUsuarios.size()>0)
                    {
                        session.setAttribute("listaUsuarios", listaUsuarios);
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
        else if (request.getParameter("searchUsuarios")!=null)
        {
            address = "ListaUsuarios.jsp";
            doAction(request, false);
            if(error)
            {
                HttpSession session = request.getSession();
                session.setAttribute("mess", mess);
                address = "ListaUsuarios.jsp?norecords=1";
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
