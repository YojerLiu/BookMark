package com.example.bookmark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    List<String> URLs = new ArrayList<>();

    public User() {}

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, List<String> URLs) {
        this.id = id;
        this.username = username;
        this.URLs = URLs;
    }

    public User(int id, String username, String password, List<String> URLs) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.URLs = URLs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public List<String> getURLs() {
        return URLs;
    }

    public void setURLs(List<String> URLs) {
        this.URLs = URLs;
    }

    @Override
    public String toString()
    {
        return "[id:" + id + "|username:" + getUsername() + "|password:" + getPassword() + "]";
    }

    public boolean isSame(User user)
    {
        return username.equals(user.getUsername()) && password.equals(user.getPassword());
    }
}
