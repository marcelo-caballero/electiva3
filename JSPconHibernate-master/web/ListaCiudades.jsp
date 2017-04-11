<%-- 
    Document   : ListaCiudades
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
        <title>Lista de ciudades</title>
    </head>
    <body>
        <%@include file="/WEB-INF/loguearse.jsp" %>
        <%
            int porPagina = 0;
            int pagina = 0;
            int totalCiudades = 0;
            int totalPaginas = 0;
            String mensajeError = "No se encontraron ciudades para visualizar";
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
            
            List<Ciudad> listaCiudades = (List<Ciudad>) session.getAttribute("listaCiudades");
            if((listaCiudades == null)&&!norecords)
            {
                CiudadManager cm = new CiudadManager();
                listaCiudades = cm.getCiudades();

                if(listaCiudades.size()>0)
                {
                    session.setAttribute("listaCiudades", listaCiudades);
                }
            }
            if(!norecords)
            {
                totalCiudades = listaCiudades.size();
                if(porPagina == 0) {
                    porPagina = totalCiudades;
                    verTodos = true;
                }
                totalPaginas = (int) (totalCiudades / porPagina) - 1;

                if(totalCiudades % porPagina > 0)
                    totalPaginas = totalPaginas + 1;
            }

            int hasta = (pagina + 1)*porPagina;
            int desde = pagina * porPagina;

            if(totalCiudades <= hasta)
                hasta = totalCiudades;

            List<Ciudad> ciudadesPagina = null;
            if(listaCiudades != null){
              ciudadesPagina = listaCiudades.subList(desde, hasta);
            }

            if(hasta==totalCiudades)
                pageContext.setAttribute("ciudadesPagina", listaCiudades);
            else
                pageContext.setAttribute("ciudadesPagina", ciudadesPagina);
            pageContext.setAttribute("pagina", pagina);
            pageContext.setAttribute("totalPaginas", totalPaginas);
            pageContext.setAttribute("msjError", mensajeError);

        %>
        <h1>Lista de Ciudades</h1>
        <ul type="circle">
            <li><a href="index.jsp">Inicio</a></li>
            <li><a href="AgrEditCiudad.jsp">Agregar Ciudad</a></li>
            <li><a href="BuscarCiudad.jsp">Buscar Ciudad</a></li>
        </ul>
        <c:if test="${empty ciudadesPagina}">
            <table frame="border" width="60%" border="1" >
                <tr><th>Aviso</th></tr>
                <tr>
                    <td colspan="7">${msjError}</td>
                </tr>
            </table>
        </c:if>
        <c:if test="${!empty ciudadesPagina}">
          <table frame="border" width="50%" border="1" >
            <tbody>
              <tr>
                <th>#</th>
                <th>Id</th>
                <th>Ciudad</th>
              </tr>
                <c:set var="contador" value="1" scope="page"/>
                <c:forEach var="item" items="${ciudadesPagina}">
                    <c:if test="${contador%2 == 0}"><tr bgcolor="silver"></c:if>
                    <c:if test="${contador%2 != 0}"><tr bgcolor="white"></c:if>
                        <td><a href="EliminarCiudad.jsp?id=${item.id}">Eliminar</a></td>
                        <td><a href="AgrEditCiudad.jsp?id=${item.id}">${item.id}</a></td>
                        <td> ${item.nombre} </td>
                    </tr>
                    <c:set var="contador" value="${contador+1}" />
                </c:forEach>

            </tbody>
          </table>
          <c:if test="${pagina >0}">
                <a href="ListaCiudades.jsp?pagina=0"> <b>[Inicio]</b> </a>
                <a href="ListaCiudades.jsp?pagina=${pagina-1}"> <b>[Anterior]</b> </a>
          </c:if>
          <c:if test="${pagina < totalPaginas}">
                <a href="ListaCiudades.jsp?pagina=${pagina +1}"> <b>[Siguiente]</b> </a>
                <a href="ListaCiudades.jsp?pagina=${totalPaginas}"> <b>[Fin]</b> </a>
          </c:if>
        </c:if>

    </body>
</html>
