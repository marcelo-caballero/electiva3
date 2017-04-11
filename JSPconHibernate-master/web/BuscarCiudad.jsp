<%-- 
    Document   : BuscarCliente
    Created on : 10-abr-2014, 10:33:10
    Author     : Rodrigo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buscar Ciudad</title>
    </head>
    <body>
        <%@include file="/WEB-INF/loguearse.jsp" %>
        <%
            Long id = (long)0;
            if (request.getParameter("id")!=null)
            {
                id = Long.parseLong(request.getParameter("id"));
            }
            CiudadManager um = new CiudadManager();
            Ciudad ciudad = null;
            if(id > 0)
            {
                ciudad = um.getCiudad(id);
            }
            else
            {
                ciudad = new Ciudad();
            }
            pageContext.setAttribute( "ciudad", ciudad );
        %>
        <h1>Buscar Ciudad</h1>
           <form action="CiudadController" method="post">
               <table frame="box">
                  <tr>
                      <th>Id:</th>
                      <td>
                          <c:if test="${ciudad.id != 0}"> ${ciudad.id} </c:if>
                          <c:if test="${ciudad.id == 0}"> n/a </c:if>
                          <input name="ciudadId" value="${ciudad.id}" type="hidden" />
                      </td>
                  </tr>
                  <tr>
                      <th>Nombre Ciudad:</th>
                      <td><input name="ciudadNombre" value="${ciudad.nombre}" type="text" /></td>
                  </tr>                 
              </table>
                  <input type="submit" value="Buscar" name="searchCiudades" />
          </form>

    </body>
</html>
