package com.example.customePassordUpdate;

import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class CustomeUpdatePasswordFactory implements RequiredActionFactory {

    public static final String PROVIDER_ID = "custom-update-password";

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return new CustomeUpdatePassword();
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // Initialize any required configuration
    }
    

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // No post-initialization required
    }

    @Override
    public void close() {
        // No specific resources to close
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayText() {
        return "Custom Update Password";
    }
}
