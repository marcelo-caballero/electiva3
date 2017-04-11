<%-- 
    Document   : AgrEditCliente
    Created on : 10-abr-2014, 10:33:10
    Author     : Rodrigo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar / Editar Cliente</title>
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
        <h1>Agregar / Editar Cliente</h1>
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
                      <td><input name="clienteNombre" value="${cliente.nombre}" type="text" /></td>
                  </tr>
                  <tr>
                      <th>Documento:</th>
                      <td><input name="clienteDocumento" value="${cliente.documento}" type="text" size="20"/></td>
                  </tr>
                  <tr>
                      <th>Dirección:</th>
                      <td><input name="clienteDireccion" value="${cliente.direccion}" type="text" size="60"/></td>
                  </tr>
                  <tr>
                      <th>Teléfono:</th>
                      <td><input name="clienteTelefono" value="${cliente.telefono}" type="text" size="20"/></td>
                  </tr>
                  <tr>
                      <th>Email:</th>
                      <td><input name="clienteEmail" value="${cliente.email}" type="text" size="60"/></td>
                  </tr>
                  <tr>
                      <th>Ciudad</th>
                      <td>
                          <select name="clienteCiudadId">
                              <c:forEach var="ciudad" items="${listaCiudades}">
                                  <option <c:if test="${ciudad.id == cliente.ciudad.id}"> selected="selected" </c:if> value="${ciudad.id}"> ${ciudad.nombre}</option>
                              </c:forEach>
                          </select>
                      </td>
                  </tr>
              </table>
                  <input type="submit" value="Guardar y volver" name="saveCliente" />
                  <input type="submit" value="Guardar y agregar" name="saveAddCliente" />
          </form>

    </body>
</html>
