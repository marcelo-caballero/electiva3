package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
//import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UsuarioManager;
import model.Usuario;

/**
 *
 * @author Acer
 */
public class LoginController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  /*  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        String usu, pass;
        usu = request.getParameter("user");
        pass = request.getParameter("password");
        PersistenciaBD p = PersistenciaBD.getInstancia();
        
        Usuario usuario = p.getUsuario(usu, pass);
        if(usuario != null){
            sesion.setAttribute("usuario", usu);
            response.sendRedirect("index.jsp");
            
        }else{
            request.setAttribute("mensaje","Error de contraseña y/o cuenta");
            request.getRequestDispatcher("index.jsp").forward(request, response);
           
        }
    }*/
   
    


    @Override
    //Cierra sesion
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession sesion = request.getSession();
       // String contexto = request.getContextPath();
         if(sesion.getAttribute("usuario") == null){
              request.setAttribute("mensaje","Inicie sesion");
              request.getRequestDispatcher("login.jsp").forward(request, response);
                  
         }else{
              sesion.removeAttribute("usuario");
              request.setAttribute("mensaje","Sesion cerrada");
              request.getRequestDispatcher("login.jsp").forward(request, response);
         }
    }


    @Override
    //Inicia Sesion 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession sesion = request.getSession();
        //String contexto = request.getContextPath();
        String usuarioCuenta, usuarioContraseña;
        UsuarioManager usuarioManager = new UsuarioManager();
        
        usuarioCuenta = request.getParameter("user");
        usuarioContraseña = request.getParameter("password");
        
        if(usuarioCuenta  != null && usuarioContraseña != null){
            
                Usuario usuario = usuarioManager.getUsuario(usuarioCuenta, usuarioContraseña);
                System.out.print(usuario);
                if(sesion.getAttribute("usuario") != null){
                    request.setAttribute("mensaje","Sesion ya iniciada");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                if(usuario != null){
                        sesion.setAttribute("usuario", usuario);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    

                }else{
                    request.setAttribute("mensaje","Error de contraseña y/o cuenta");
                    request.getRequestDispatcher("login.jsp").forward(request, response);

                }
       
        }else{
            request.setAttribute("mensaje","No se ha recibido parametros");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }


    @Override
    public String getServletInfo() {
        return "Inicia o cierra sesion de usuario";
    }

}
