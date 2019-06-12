/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import com.mycompany.user.User;

/**
 *
 * @author dev
 */
@WebServlet(urlPatterns = {"/user/*", "/user-save/"})
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

        if (path.contains("/user-save/"))
            request.getRequestDispatcher("/view/user-save.jsp").forward(request, response);
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
        if (id == 0) {
            return new User();
        }
        else {
            User user = new User("name", "phone", "email");
            user.setId(id);
            return user;
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

        if (path.contains("/user/")) {
            int id = this.getId(path);
            User user = this.getUser(id);

            request.setAttribute("user", user);
            request.getRequestDispatcher("/view/user.jsp").forward(request, response);
        }
        else {
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

        if (path.contains("/user/")) {
            response.sendRedirect("/user-save/");
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
