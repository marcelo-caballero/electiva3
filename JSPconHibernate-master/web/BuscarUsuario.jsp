<%-- 
    Document   : BuscarUsuario
    Created on : 10-abr-2017, 11:54:47
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buscar Usuario</title>
    </head>
    <body>
        <%@include file="/WEB-INF/loguearse.jsp" %>
        <%
            String cuenta = "";
            if (request.getParameter("id")!=null)
            {
                cuenta = request.getParameter("id");
            }
            UsuarioManager um = new UsuarioManager();
            Usuario usuario = null;
            if(cuenta.length() > 0)
            {
                usuario = um.getUsuario(cuenta);
            }
            else
            {
                usuario = new Usuario();
            }
            pageContext.setAttribute( "user", usuario );
        %>
        <h1>Buscar Ciudad</h1>
           <form action="UsuarioController" method="post">
               <table frame="box">
                  <tr>
                      <th>Cuenta:</th>
                      <td>
                          <c:if test="${user.cuenta.length() > 0}"> ${user.cuenta} </c:if>
                          <c:if test="${user.cuenta.length() == 0}"> n/a </c:if>
                          
                      </td>
                  </tr>
                  <tr>
                      <th>Nombre cuenta:</th>
                      <td><input name="usuarioCuenta" value="${user.cuenta}" type="text" /></td>
                  </tr>                 
              </table>
                  <input type="submit" value="Buscar" name="searchUsuarios" />
          </form>

    </body>
</html>