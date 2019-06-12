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
@WebServlet(urlPatterns = {"/user/*", "/user-save"})
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
        User user = new User();
        String path = request.getRequestURI();

        if (path.contains("/user/")) {
            Pattern pattern = Pattern.compile("\\d+$");
            Matcher matcher = pattern.matcher(path);

            if (matcher.find()) {
                String id = path.substring(matcher.start(), matcher.end());

                String name = request.getParameter("name");

                request.setAttribute("name", id);
                request.setAttribute("phone", "12312312");
                request.setAttribute("email", "dwqqwe");

                request.getRequestDispatcher("/view/user-save.jsp").forward(request, response);
            }
            else {
                String name = request.getParameter("name");

                //request.setAttribute("name", user.getName());
                //request.setAttribute("phone", user.getPhone());
                //request.setAttribute("name", name);
                //request.setAttribute("email", user.getEmail());
                //request.setAttribute("valid", user.validate());

                request.getRequestDispatcher("/view/user.jsp").forward(request, response);
            }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
