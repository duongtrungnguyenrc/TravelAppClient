package com.main.travelApp.repositories.interfaces;
import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.AuthInstance;


public interface AuthRepository {
    public void authentication(String email, String password, ActionCallback<AuthInstance> action);
}
