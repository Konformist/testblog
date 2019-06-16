/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.user;

import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author dev
 */
public class User {
    private int id = 0;
    private String name = "";
    private String phone = "";
    private String email = "";

    static String DATABASE_URL = "jdbc:mysql://localhost/testadmin";
    static String DATABASE_TABLE = "users";
    static Properties DATABASE_PROPS = new Properties();

    {
        DATABASE_PROPS.setProperty("user", "admin");
        DATABASE_PROPS.setProperty("password", "admin");
        DATABASE_PROPS.setProperty("serverTimezone", "UTC");
    }

    public User() {
    }

    public User(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

        try {
            conn = DriverManager.getConnection(User.DATABASE_URL, User.DATABASE_PROPS);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + User.DATABASE_TABLE + " WHERE id=" + id);
            rs.first();

            this.id = id;
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

        try {
            conn = DriverManager.getConnection(User.DATABASE_URL, User.DATABASE_PROPS);
            stmt = conn.createStatement();

            if ((this.id != 0) & stmt.execute("SELECT * FROM " + User.DATABASE_TABLE + " WHERE id=" + this.id)) {
                stmt.executeUpdate(
                    "UPDATE `" + User.DATABASE_TABLE + "` SET "
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
                    + "(`id`, `name`, `date_reg`, `date_update`, `phone`, `email`) "
                    + "VALUES "
                    + "(NULL, '" + this.name + "', CURRENT_DATE(), CURRENT_DATE(), '" + this.phone + "', '" + this.email + "')"
                );
            }
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
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
