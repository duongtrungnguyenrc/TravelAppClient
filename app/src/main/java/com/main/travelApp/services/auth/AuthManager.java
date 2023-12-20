package com.main.travelApp.services.auth;

import com.main.travelApp.models.AuthInstance;
import com.main.travelApp.models.User;

public class AuthManager {
    private static AuthManager instance = null;
    private AuthInstance authInstance = null;

    public static synchronized AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    public void setAuthInstance(AuthInstance authInstance) {
        this.authInstance = authInstance;
    }

    public User getCurrentUser() {
        if (this.authInstance != null) {
            return this.authInstance;
        }
        return null;
    }

    public String getAccessToken() {
        if (this.authInstance != null) {
            return this.authInstance.getAccessToken();
        }
        return null;
    }
}
