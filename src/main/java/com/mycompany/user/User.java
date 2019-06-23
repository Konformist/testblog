/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.user;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;

/**
 *
 * @author dev
 */
public class User {
  private int id = 0;
  private String login = "";
  private String pass = "";
  private String name = "";
  private String phone = "";
  private String email = "";
  
  public static String DATABASE_URL = "jdbc:mysql://localhost/testadmin";
  public static String DATABASE_TABLE = "users";
  public static Properties DATABASE_PROPS = new Properties();
  
  public static void addPropertyDB () {
    DATABASE_PROPS.setProperty("user", "admin");
    DATABASE_PROPS.setProperty("password", "admin");
    DATABASE_PROPS.setProperty("serverTimezone", "UTC");
  }
  
  public static String hashPass(String pass) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] digest = md.digest(pass.getBytes());
      
      BigInteger hashInt = new BigInteger(1, digest);
      String myHash = hashInt.toString(16);
      
      while (myHash.length() < 32) {
        myHash = "0" + myHash;
      }
      
      return myHash;
    }
    catch (NoSuchAlgorithmException err) {
      System.out.println(err.getMessage());
      return "";
    }
  }
  
  public User() {
  }
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getLogin() {
    return this.login;
  }
  
  public void setLogin(String login) {
    this.login = login;
  }
  
  public String getPass() {
    return this.pass;
  }
  
  public void setPass(String pass) {
    this.pass = pass;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getPhone() {
    return this.phone;
  }
  
  public void setPhone(String phone) {
    this.phone = phone;
  }
  
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public boolean load(int id) {
    if (id == 0)
      return false;
    
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    User.addPropertyDB();
    
    try {
      conn = DriverManager.getConnection(User.DATABASE_URL, User.DATABASE_PROPS);
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT * FROM " + User.DATABASE_TABLE + " WHERE `id`='" + id + "'");
      rs.first();
      
      this.id = id;
      this.login = rs.getString("login");
      this.pass = rs.getString("pass");
      this.name = rs.getString("name");
      this.phone = rs.getString("phone");
      this.email = rs.getString("email");
      return true;
    }
    catch (SQLException ex) {
      System.out.println("SQLException: " + ex.getMessage());
      return false;
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
  
  public void save() {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    User.addPropertyDB();
    
    try {
      conn = DriverManager.getConnection(User.DATABASE_URL, User.DATABASE_PROPS);
      stmt = conn.createStatement();
      
      if ((this.id != 0) & stmt.execute("SELECT * FROM " + User.DATABASE_TABLE + " WHERE id=" + this.id)) {
        stmt.executeUpdate(
            "UPDATE `" + User.DATABASE_TABLE + "` SET "
                + "`login` = '" + this.login + "', "
                + "`name` = '" + this.name + "', "
                + "`date_update` = CURRENT_DATE(), "
                + "`phone` = '" + this.phone + "', "
                + "`email` = '" + this.email + "' "
                + "WHERE `" + User.DATABASE_TABLE + "`.`id` = " + this.id
        );
      }
      else {
        stmt.executeUpdate(
            "INSERT INTO `" + User.DATABASE_TABLE + "` "
                + "(`id`, `login`, `pass`, `name`, `date_reg`, `date_update`, `phone`, `email`) "
                + "VALUES "
                + "(NULL, '" + this.login + "', '" + User.hashPass(this.pass) + "', '" + this.name + "', CURRENT_DATE(), CURRENT_DATE(), '" + this.phone + "', '" + this.email + "')"
        );
      }
    } catch (SQLException ex) {
      System.out.println("SQLException: " + ex.getMessage());
    } finally {
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
  
  public static String getAuth(String login) {
    if (login.isEmpty())
      return null;
    
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    User.addPropertyDB();
    
    try {
      conn = DriverManager.getConnection(User.DATABASE_URL, User.DATABASE_PROPS);
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT pass FROM " + User.DATABASE_TABLE + " WHERE `login`='" + login + "'");
      rs.first();
      
      return rs.getString("pass");
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
  
  public String validate() {
    String ret = "";
    
    if (this.name == null || this.name.isEmpty())
      ret += "Name is empty\n";
    
    if (this.phone == null || this.phone.isEmpty())
      ret += "Phone is empty\n";
    
    if (this.email == null || this.email.isEmpty())
      ret += "Email is empty\n";
    
    return ret;
  }
}
