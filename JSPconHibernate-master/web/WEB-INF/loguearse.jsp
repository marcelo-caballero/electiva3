<%-- 
  Menu de logueo de usuarios
--%>
<div>
    <%@page import="model.Usuario"%>
    <%! Usuario usuario = null;%>
    
    <%  
            if (session.getAttribute("usuario") == null){%>
            <a href="<%=request.getContextPath()%>/login.jsp">Conectarse</a>
        <% }else{
                usuario = (Usuario)session.getAttribute("usuario");%>
            <p><%= usuario.getCuenta()%></p>
            
            
            <a href="LoginController">Salir</a>
        <% }%>
</div>