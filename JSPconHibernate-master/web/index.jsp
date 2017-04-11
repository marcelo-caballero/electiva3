<%-- 
    Document   : index
    Created on : 10-abr-2014, 11:02:20
    Author     : Rodrigo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agenda de Clientes</title>
    </head>
    <body>
        
        <%@include file="/WEB-INF/loguearse.jsp" %>
        <h1>Agenda de Clientes</h1>
        <ul type="square">
            <li><a href="AgrEditCiudad.jsp">Agregar</a></li>
            <li><a href="ListaClientes.jsp">Lista de Clientes</a></li>
            <li><a href="ListaCiudades.jsp">Lista de Ciudades</a></li>
            <li><a href="ListaUsuarios.jsp">Lista de Usuarios</a></li>
        </ul>
    </body>
</html>