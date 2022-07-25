package com.example.licenta_ioana_banu;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String username;
    public String password;
    public String nume,prenume;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public User(String username, String password, String nume, String prenume) {
        this.username = username;
        this.password = password;
        this.nume = nume;
        this.prenume = prenume;

    }
}
