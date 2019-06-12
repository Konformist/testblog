<%-- 
    Document   : user
    Created on : 1 июн. 2019 г., 19:03:33
    Author     : dev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Object name = request.getAttribute("name");
    Object phone = request.getAttribute("phone");
    Object email = request.getAttribute("email");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User</title>
        <link rel="stylesheet" href="/css/user.css" />
    </head>
    <body>
        <form class="user-form" action="/user/" method="POST">
            <div class="form-field">
                <span>Name:</span>
                <input name="name" value="<%= name%>" />
            </div>
            <div class="form-field">
                <span>Phone:</span>
                <input name="phone" value="<%= phone%>" />
            </div>
            <div class="form-field">
                <span>Email:</span>
                <input name="email" value="<%= email%>" />
            </div>
            
            <button>Save</button>
        </form>
    </body>
</html>
