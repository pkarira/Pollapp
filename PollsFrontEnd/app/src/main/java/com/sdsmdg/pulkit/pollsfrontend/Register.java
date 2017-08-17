package com.sdsmdg.pulkit.pollsfrontend;

/**
 * Created by pulkit on 18/8/17.
 */

public class Register {

    String name, pass, email;

    public Register(String name, String pass, String email) {
        this.name = name;
        this.pass = pass;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
