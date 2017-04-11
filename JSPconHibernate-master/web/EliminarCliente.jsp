<%-- 
    Document   : EliminarCliente
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
        <title>Eliminar Cliente</title>
    </head>
    <body>
        <%@include file="/WEB-INF/loguearse.jsp" %>
        <%
            Long id = (long)0;
            if (request.getParameter("id")!=null)
            {
                id = Long.parseLong(request.getParameter("id"));
            }
            ClienteManager um = new ClienteManager();
            Cliente cliente = null;
            if(id > 0)
            {
                cliente = um.getCliente(id);
            }
            else
            {
                cliente = new Cliente();
            }
            pageContext.setAttribute( "cliente", cliente );

            CiudadManager cim = new CiudadManager();
            List <Ciudad> listaCiudades = (List <Ciudad>)cim.getCiudades();
            pageContext.setAttribute( "listaCiudades", listaCiudades );
        %>
        <h1>Eliminar Cliente</h1>
           <form action="ClienteController" method="post">
               <table frame="box">
                  <tr>
                      <th>Id:</th>
                      <td>
                          <c:if test="${cliente.id != 0}"> ${cliente.id} </c:if>
                          <c:if test="${cliente.id == 0}"> n/a </c:if>
                          <input name="clienteId" value="${cliente.id}" type="hidden" />
                      </td>
                  </tr>
                  <tr>
                      <th>Nombre Cliente:</th>
                      <td>${cliente.nombre}</td>
                  </tr>
                  <tr>
                      <th>Documento:</th>
                      <td>${cliente.documento}</td>
                  </tr>
                  <tr>
                      <th>Dirección:</th>
                      <td>${cliente.direccion}</td>
                  </tr>
                  <tr>
                      <th>Teléfono:</th>
                      <td>${cliente.telefono}</td>
                  </tr>
                  <tr>
                      <th>Email:</th>
                      <td>${cliente.email}</td>
                  </tr>
                  <tr>
                      <th>Ciudad</th>
                      <td>${cliente.ciudad.nombre}</td>
                  </tr>
              </table>
                  <input type="submit" value="Eliminar" name="deleteCliente" />
          </form>

    </body>
</html>
