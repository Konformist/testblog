package com.mycompany.users;

import com.mycompany.user.User;

import java.sql.*;
import java.util.ArrayList;

public class Users {
  public static ArrayList<User> getUsers() {
    Connection conn;
    Statement stmt = null;
    ResultSet rs = null;
    User.addPropertyDB();
  
    try {
      ArrayList<User> users = new ArrayList<>();
      conn = DriverManager.getConnection(User.DATABASE_URL, User.DATABASE_PROPS);
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT * FROM " + User.DATABASE_TABLE);
      
      while (rs.next()) {
        User user = new User();
        
        user.setName(rs.getString("name"));
        user.setPhone(rs.getString("phone"));
        user.setEmail(rs.getString("email"));
        
        users.add(user);
      }
      
      return users;
    }
    catch (SQLException ex) {
      System.out.println("SQLException: " + ex.getMessage());
  
      return null;
    }
    finally {
      if (rs != null) {
        try { rs.close(); }
        catch (SQLException sqlEx) { } // ignore
      
        rs = null;
      }
    
      if (stmt != null) {
        try { stmt.close(); }
        catch (SQLException sqlEx) { } // ignore
      
        stmt = null;
      }
    }
  }
}
