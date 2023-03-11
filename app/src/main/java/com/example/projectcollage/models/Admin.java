package com.example.projectcollage.models;

public class Admin {
    private String email;
    private boolean enableAdmin;

    public Admin(String email, boolean enableAdmin) {
        this.email = email;
        this.enableAdmin = enableAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isEnableAdmin() {

       return enableAdmin;
    }

    public void setEnableAdmin(boolean enableAdmin) {
        this.enableAdmin = enableAdmin;
    }
}
