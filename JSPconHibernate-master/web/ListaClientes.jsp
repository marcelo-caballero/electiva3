<%-- 
    Document   : ListaClientes
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
        <title>Lista de clientes</title>
    </head>
    <body>
        <%@include file="/WEB-INF/loguearse.jsp" %>
        <%
            int porPagina = 0;
            int pagina = 0;
            int totalClientes = 0;
            int totalPaginas = 0;
            String mensajeError = "No se encontraron clientes para visualizar";
            boolean norecords = false;
            boolean verTodos = false;

            if (request.getParameter("porPagina")!=null)
            {
                porPagina = Integer.parseInt(request.getParameter("porPagina"));
            }
            else
                porPagina = 8;
            if (request.getParameter("pagina")!=null)
            {
                pagina = Integer.parseInt(request.getParameter("pagina"));
            }
            if(request.getParameter("norecords")!=null)
            {
                mensajeError = (String)session.getAttribute("mess");
                norecords = true;
            }

            List<Cliente> listaClientes = (List<Cliente>) session.getAttribute("listaClientes");
            if((listaClientes == null)&&!norecords)
            {
                ClienteManager cm = new ClienteManager();
                listaClientes = cm.getClientes();

                if(listaClientes.size()>0)
                {
                    session.setAttribute("listaClientes", listaClientes);
                }
            }
            if(!norecords)
            {
                totalClientes = listaClientes.size();
                if(porPagina == 0) {
                    porPagina = totalClientes;
                    verTodos = true;
                }
                totalPaginas = (int) (totalClientes / porPagina) - 1;

                if(totalClientes % porPagina > 0)
                    totalPaginas = totalPaginas + 1;
            }

            int hasta = (pagina + 1)*porPagina;
            int desde = pagina * porPagina;

            if(totalClientes <= hasta)
                hasta = totalClientes;

            List<Cliente> clientesPagina = null; 
            
            if(listaClientes != null){
                clientesPagina = listaClientes.subList(desde, hasta);
            }
            pageContext.setAttribute("clientesPagina", clientesPagina);
            pageContext.setAttribute("pagina", pagina);
            pageContext.setAttribute("totalPaginas", totalPaginas);
            pageContext.setAttribute("msjError", mensajeError);

        %>
        <h1>Lista de Clientes</h1>
        <ul type="circle">
            <li><a href="index.jsp">Inicio</a></li>
            <li><a href="AgrEditCliente.jsp">Agregar Cliente</a></li>
            <li><a href="BuscarCliente.jsp">Buscar Cliente</a></li>
        </ul>
        <c:if test="${empty clientesPagina}">
            <table frame="border" width="80%" border="1" >
                <tr><th>Aviso</th></tr>
                <tr>
                    <td colspan="7">${msjError}</td>
                </tr>
            </table>
        </c:if>
        <c:if test="${!empty clientesPagina}">
          <table frame="border" width="80%" border="1" >
            <tbody>
              <tr>
                <th>#</th>
                <th>Id</th>
                <th>Nombre</th>
                <th>Documento</th>
                <th>Telefono</th>
                <th>Direccion</th>
                <th>Email</th>
                <th>Ciudad</th>
              </tr>
                <c:set var="contador" value="1" scope="page"/>
                <c:forEach var="item" items="${clientesPagina}">
                    <c:if test="${contador%2 == 0}"><tr bgcolor="silver"></c:if>
                    <c:if test="${contador%2 != 0}"><tr bgcolor="white"></c:if>
                        <td><a href="EliminarCliente.jsp?id=${item.id}">Eliminar</a></td>
                        <td><a href="AgrEditCliente.jsp?id=${item.id}">${item.id}</a></td>
                        <td> ${item.nombre} </td>
                        <td> ${item.documento} </td>
                        <td> ${item.telefono} </td>
                        <td> ${item.direccion} </td>
                        <td> ${item.email} </td>
                        <td> ${item.ciudad.nombre} </td>
                    </tr>
                    <c:set var="contador" value="${contador+1}" />
                </c:forEach>

            </tbody>
          </table>
          <c:if test="${pagina >0}">
                <a href="ListaClientes.jsp?pagina=0"> <b>[Inicio]</b> </a>
                <a href="ListaClientes.jsp?pagina=${pagina-1}"> <b>[Anterior]</b> </a>
          </c:if>
          <c:if test="${pagina < totalPaginas}">
                <a href="ListaClientes.jsp?pagina=${pagina +1}"> <b>[Siguiente]</b> </a>
                <a href="ListaClientes.jsp?pagina=${totalPaginas}"> <b>[Fin]</b> </a>
          </c:if>
        </c:if>

    </body>
</html>
