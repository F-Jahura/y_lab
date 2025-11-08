package org.fatema.service;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private Map<String, String> users = new HashMap<>();
    private String currentUser = null;

    public AuthService() {
        users.put("admin", "password");
        users.put("root", "qwerty");
    }

    public boolean login(String username, String password) {
        String pass = users.get(username);
        if (pass != null && pass.equals(password)) {
            currentUser = username;
            logAction("User " + username + " enter in system");
            return true;
        }
        return false;
    }

    public void logout() {
        logAction("User " + currentUser + " get out from system");
        currentUser = null;
    }

    public boolean isAuthorized() {
        return currentUser != null;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    private void logAction(String action) {
        System.out.println("Log: " + action);
    }
}
