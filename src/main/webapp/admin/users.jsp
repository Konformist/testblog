<%-- 
    Document   : user
    Created on : 1 июн. 2019 г., 19:03:33
    Author     : dev
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.mycompany.user.User" %>

<%
  ArrayList<User> users = (ArrayList) request.getAttribute("users");
%>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Users</title>
    <link rel="stylesheet" href="/css/users.css" />
  </head>
  <body>
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Phone</th>
          <th>Email</th>
        </tr>
      </thead>
      <tbody>
      <% for (User user : users) { %>
        <tr>
          <td><%= user.getName() %></td>
          <td><%= user.getPhone() %></td>
          <td><%= user.getEmail() %></td>
        </tr>
      <% } %>
      </tbody>
    </table>
  </body>
</html>
