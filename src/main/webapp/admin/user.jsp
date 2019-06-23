<%-- 
    Document   : user
    Created on : 1 июн. 2019 г., 19:03:33
    Author     : dev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User</title>
        <link rel="stylesheet" href="/css/user.css" />
    </head>
    <body>
        <form class="user-form" action="/admin/user/${user.getId() != 0 ? user.getId() : ""}" method="POST">
            <div class="form-field">
                <span>Login:</span>
                <input name="login" value="${user.getLogin()}" />
            </div>
            <div class="form-field">
                <span>Name:</span>
                <input name="name" value="${user.getName()}" />
            </div>
            <div class="form-field">
                <span>Phone:</span>
                <input name="phone" value="${user.getPhone()}" />
            </div>
            <div class="form-field">
                <span>Email:</span>
                <input name="email" value="${user.getEmail()}" />
            </div>
            <button>Save</button>
        </form>
    </body>
</html>
