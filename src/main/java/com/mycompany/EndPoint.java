/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;

import com.mycompany.user.User;
import com.mycompany.users.Users;

/**
 *
 * @author dev
 */
@WebServlet(urlPatterns = {"/"})
public class EndPoint extends HttpServlet {
  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String path = request.getRequestURI();
    
    request.getRequestDispatcher("/view/404.jsp").forward(request, response);
  }
  
  private int getId(String path) {
    Pattern pattern = Pattern.compile("\\d+$");
    Matcher matcher = pattern.matcher(path);
    int id = 0;
    
    if (matcher.find())
      id = Integer.parseInt(path.substring(matcher.start(), matcher.end()));
    
    return id;
  }
  
  private User getUser(int id) {
    User user = new User();
    user.load(id);
    return user;
  }
  
  private void setUser(int id, HttpServletRequest request) {
    User user = new User();
    
    if (id != 0)
      user.setId(id);
    
    user.setLogin(request.getParameter("login"));
    user.setPass(request.getParameter("pass"));
    user.setName(request.getParameter("name"));
    user.setPhone(request.getParameter("phone"));
    user.setEmail(request.getParameter("email"));
    
    user.save();
  }
  
  private boolean isAuth (String auth) {
    if (auth != null) {
      String passBase64 = auth.split(" ") [1];
      byte[] passByte = Base64.decodeBase64(passBase64);
      String passString = new String(passByte);
    
      String login = passString.split(":")[0];
      String pass = passString.split(":")[1];
    
      String hashPass = User.getAuth(login);
    
      return hashPass != null && User.hashPass(pass).equals(hashPass);
    } else {
      return false;
    }
  }
  
  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String path = request.getRequestURI();
  
    if (path.contains("/admin/")) {
      if (!isAuth(request.getHeader("Authorization"))) {
        response.setHeader("WWW-Authenticate", "Basic realm=\"MyRealm\"");
        response.setStatus(401);
        return;
      }
    
      if (path.contains("/user/")) {
        int id = this.getId(path);
        User user = this.getUser(id);
  
        request.setAttribute("user", user);
        request.getRequestDispatcher("/admin/user.jsp").forward(request, response);
      } else if (path.contains("/users/")) {
        request.setAttribute("users", Users.getUsers());
        request.getRequestDispatcher("/admin/users.jsp").forward(request, response);
      } else {
        request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
      }
    } else {
      processRequest(request, response);
    }
  }
  
  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String path = request.getRequestURI();
    
    if (path.contains("/admin/")) {
      if (!isAuth(request.getHeader("Authorization"))) {
        response.setHeader("WWW-Authenticate", "Basic realm=\"MyRealm\"");
        response.setStatus(401);
        return;
      }
  
      if (path.contains("/user/")) {
        this.setUser(this.getId(path), request);
        response.sendRedirect("/admin/user-save/");
      }
      else {
        response.sendRedirect("/admin/layout/");
      }
    }
    else {
      processRequest(request, response);
    }
  }
  
  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>
}
