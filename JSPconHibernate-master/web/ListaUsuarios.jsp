<%-- 
    Document   : ListaUsuarios
    Created on : 10-abr-2017, 1:05:18
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
        <title>Lista de usuarios</title>
    </head>
    <body>
        <%@include file="/WEB-INF/loguearse.jsp" %>
        <%
            int porPagina = 0;
            int pagina = 0;
            int totalUsuarios = 0;
            int totalPaginas = 0;
            String mensajeError = "No se encontraron usuarios para visualizar";
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

            List<Usuario> listaUsuarios = (List<Usuario>) session.getAttribute("listaUsuarios");
            if((listaUsuarios == null)&&!norecords)
            {
                UsuarioManager cm = new UsuarioManager();
                listaUsuarios = cm.getUsuarios();

                if(listaUsuarios.size()>0)
                {
                    session.setAttribute("listaUsuarios", listaUsuarios);
                }
            }
            if(!norecords)
            {
                totalUsuarios = listaUsuarios.size();
                if(porPagina == 0) {
                    porPagina = totalUsuarios;
                    verTodos = true;
                }
                totalPaginas = (int) (totalUsuarios / porPagina) - 1;

                if(totalUsuarios % porPagina > 0)
                    totalPaginas = totalPaginas + 1;
            }

            int hasta = (pagina + 1)*porPagina;
            int desde = pagina * porPagina;

            if(totalUsuarios <= hasta)
                hasta = totalUsuarios;

            List<Usuario> usuariosPagina = null;
            
            if(listaUsuarios != null){
                usuariosPagina = listaUsuarios.subList(desde, hasta);
            }
            pageContext.setAttribute("usuariosPagina", usuariosPagina);
            pageContext.setAttribute("pagina", pagina);
            pageContext.setAttribute("totalPaginas", totalPaginas);
            pageContext.setAttribute("msjError", mensajeError);

        %>
        <h1>Lista de Usuarios</h1>
        <ul type="circle">
            <li><a href="index.jsp">Inicio</a></li>
            <li><a href="AgrEditUsuario.jsp">Agregar Usuario</a></li>
            <li><a href="BuscarUsuario.jsp">Buscar Usuario</a></li>
        </ul>
        <c:if test="${empty usuariosPagina}">
            <table frame="border" width="80%" border="1" >
                <tr><th>Aviso</th></tr>
                <tr>
                    <td colspan="7">${msjError}</td>
                </tr>
            </table>
        </c:if>
        <c:if test="${!empty usuariosPagina}">
          <table frame="border" width="80%" border="1" >
            <tbody>
              <tr>
                <th>#</th>
                
                <th>Cuenta</th>
                <th>Contraseña</th>
                
              </tr>
                <c:set var="contador" value="1" scope="page"/>
                <c:forEach var="item" items="${usuariosPagina}">
                    <c:if test="${contador%2 == 0}"><tr bgcolor="silver"></c:if>
                    <c:if test="${contador%2 != 0}"><tr bgcolor="white"></c:if>
                        <td><a href="EliminarUsuario.jsp?id=${item.cuenta}">Eliminar</a></td>
                        <td><a href="AgrEditUsuario.jsp?id=${item.cuenta}">${item.cuenta}</a></td>
                        
                        <td> ${item.contrasena} </td>
                    </tr>
                    <c:set var="contador" value="${contador+1}" />
                </c:forEach>

            </tbody>
          </table>
          <c:if test="${pagina >0}">
                <a href="ListaUsuarios.jsp?pagina=0"> <b>[Inicio]</b> </a>
                <a href="ListaUsuarios.jsp?pagina=${pagina-1}"> <b>[Anterior]</b> </a>
          </c:if>
          <c:if test="${pagina < totalPaginas}">
                <a href="ListaUsuarios.jsp?pagina=${pagina +1}"> <b>[Siguiente]</b> </a>
                <a href="ListaUsuarios.jsp?pagina=${totalPaginas}"> <b>[Fin]</b> </a>
          </c:if>
        </c:if>

    </body>
</html>
