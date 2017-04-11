<%-- 
    Document   : AgrEditUsuario
    Created on : 10-abr-2017, 13:23:30
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar / Editar Usuario</title>
    </head>
    <body>
        <%@include file="/WEB-INF/loguearse.jsp" %>
        <%
            String cuenta = null; 
            if (request.getParameter("id")!=null)
            {
                 cuenta = request.getParameter("id");
            }
            UsuarioManager um = new UsuarioManager();
            Usuario usuario = null;
            if(cuenta != null)
            {
                usuario = um.getUsuario(cuenta);
            }
            else
            {
                usuario = new Usuario();
            }
            pageContext.setAttribute( "user", usuario );  
        %>
        <h1>Agregar / Editar Ciudad</h1>
           <form action="UsuarioController" method="post">
               <table frame="box">
                  <tr>
                      <%if (usuario.getCuenta() != null){%>
                        <th>Cuenta</th>
                      
                        <td>

                             ${user.cuenta} 

                            <input name="usuarioCuenta" value="${user.cuenta}" type="hidden" />
                        </td>
                      <%}%>
                  </tr>
                  <% if (usuario.getCuenta() == null){%>
                    <tr>
                        <th>Cuenta:</th>
                        <td><input name="usuarioCuenta" value="${user.cuenta}" type="text" /></td>
                    </tr>
                  <%}%>
                  <tr>
                      <th>Contraseña:</th>
                      <td><input name="usuarioContrasena" value="${user.contrasena}" type="text" /></td>
                  </tr>                
              </table>
                  <input type="submit" value="Guardar y volver" name="saveUsuario" />
                  <input type="submit" value="Guardar y agregar" name="saveAddUsuario" />
          </form>

    </body>
</html>
