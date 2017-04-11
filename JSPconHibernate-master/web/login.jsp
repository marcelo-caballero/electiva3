<%-- 
   Pagina de logueo de usuario

--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <% if(request.getAttribute("mensaje") != null){%>
            <script> alert('<%=request.getAttribute("mensaje")%>');</script>
            <%request.removeAttribute("mensaje");}%>
        
    </head>
    <body>
       
        <br><br><br>
        <h1>Conectarse</h1>
        
        <form action="LoginController" method="post">
            <input type="text" name="user" maxlength="20" size="20" pattern="[a-zA-Z]+" required/>
            <input type="password" name="password"
                   maxlength="8" size="20" pattern="[a-zA-Z0-9]+" required/>
           
            <input type="submit" value="Enviar" />
        </form>
    </body>
</html>
