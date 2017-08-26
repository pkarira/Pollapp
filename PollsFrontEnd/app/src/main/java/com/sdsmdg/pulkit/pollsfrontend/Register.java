package com.sdsmdg.pulkit.pollsfrontend;

/**
 * Created by pulkit on 18/8/17.
 */

public class Register {

    String name, password, email;

    public Register(String name, String pass, String email) {
        this.name = name;
        this.password = pass;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return password;
    }

    public void setPass(String pass) {
        this.password = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
