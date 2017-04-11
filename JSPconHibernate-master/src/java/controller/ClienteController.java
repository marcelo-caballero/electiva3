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
public class ClienteController extends HttpServlet {
   
    boolean error=false;
    String mess="";

    private void doAction(HttpServletRequest request, boolean guardar)
    {
        error = false;
        Cliente cliente = new Cliente();
        ClienteManager clienteManager = new ClienteManager();
        HttpSession session = request.getSession(true);

        if(request.getParameter("clienteId")!=null)
        {
            Long id=(long)0;
            String param = request.getParameter("clienteId");

            if(param.length()>0)
                id = Long.parseLong(param);
            if(id!=0)
                cliente.setId(id);
        }
        if(request.getParameter("clienteNombre").length() > 0)
        {
            cliente.setNombre(request.getParameter("clienteNombre"));
        }
        if(request.getParameter("clienteDocumento").length() > 0)
        {
            cliente.setDocumento(request.getParameter("clienteDocumento"));
        }
        if(request.getParameter("clienteTelefono").length() > 0)
        {
            cliente.setTelefono(request.getParameter("clienteTelefono"));
        }
        if(request.getParameter("clienteEmail").length() > 0)
        {
            cliente.setEmail(request.getParameter("clienteEmail"));
        }
        if(request.getParameter("clienteDireccion").length() > 0)
        {
            cliente.setDireccion(request.getParameter("clienteDireccion"));
        }
        if(request.getParameter("clienteCiudadId").length() > 0)
        {
            Long ciudadId=(long)0;
            String param = request.getParameter("clienteCiudadId");

            if(param.length()>0)
            {
                CiudadManager cm = new CiudadManager();

                ciudadId = Long.parseLong(param);
                cliente.setCiudad(cm.getCiudad(ciudadId));
            }
        }
        if (guardar)
        {
            if(clienteManager.saveCliente(cliente))
            {
                if (session.getAttribute("listaClientes")!=null)
                {
                    session.setAttribute("listaClientes", null);
                }
                List<Cliente> listaClientes  = clienteManager.getClientes();

                if(listaClientes.size()>0)
                {
                    session.setAttribute("listaClientes", listaClientes);
                }
            }
            else
            {
                error = true;
                mess = clienteManager.mess;
            }
        }
        else //Buscar
        {
            if (session.getAttribute("listaClientes")!=null)
            {
                session.setAttribute("listaClientes", null);
            }
            List<Cliente> listaClientes  = clienteManager.searchClientes(cliente);

            if(listaClientes.size()>0)
            {
                session.setAttribute("listaClientes", listaClientes);
            }
            else
            {
                error = true;
                mess = "No se encontraron Clientes";
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
        String address="ListaClientes.jsp";

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if(request.getParameter("saveCliente")!=null)
        {
            address = "ListaClientes.jsp";
            doAction(request, true);
            if(error)
            {
                HttpSession session = request.getSession();
                session.setAttribute("mess", mess);
                address = "AgrEditCliente.jsp?error=1";
            }
        }
        else if (request.getParameter("saveAddCliente")!=null)
        {
            address = "AgrEditCliente.jsp";
            doAction(request, true);
            if(error)
            {
                HttpSession session = request.getSession();
                session.setAttribute("mess", mess);
                address = "AgrEditCliente.jsp?error=1";
            }
        }
        else if (request.getParameter("deleteCliente")!=null)
        {
            address = "ListaClientes.jsp";
            if(request.getParameter("clienteId")!=null)
            {
                Long id = Long.parseLong(request.getParameter("clienteId")) ;
                ClienteManager em = new ClienteManager();
                Cliente cliente = em.getCliente(id);
                try
                {
                    HttpSession session = request.getSession();
                    if(!em.deleteCliente(cliente))
                    {
                        session.setAttribute("mess", mess);
                        address = "EliminarCliente.jsp?error=1";
                    }
                    if (session.getAttribute("listaClientes")!=null)
                    {
                        session.setAttribute("listaClientes", null);
                    }
                    List<Cliente> listaClientes  = em.getClientes();

                    if(listaClientes.size()>0)
                    {
                        session.setAttribute("listaClientes", listaClientes);
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
        else if (request.getParameter("searchClientes")!=null)
        {
            address = "ListaClientes.jsp";
            doAction(request, false);
            if(error)
            {
                HttpSession session = request.getSession();
                session.setAttribute("mess", mess);
                address = "ListaClientes.jsp?norecords=1";
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
