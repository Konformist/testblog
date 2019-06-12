/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.user;

/**
 *
 * @author dev
 */
public class User {
    private String name = "";
    private String phone = "";
    private String email = "";
    
    public User(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public User() {
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getPhone() {
        return this.phone;
    }
    
    public String getEmail() {
        return this.email;
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