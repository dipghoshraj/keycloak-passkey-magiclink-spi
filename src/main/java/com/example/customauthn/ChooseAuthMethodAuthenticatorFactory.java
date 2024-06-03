package com.example.customauthn;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
// import org.keycloak.provider.ProviderFactory;

import java.util.List;

public class ChooseAuthMethodAuthenticatorFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "choose-auth-method-authenticator";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new ChooseAuthMethodAuthenticator();
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return ProviderConfigurationBuilder.create().build();
    }

    @Override
    public String getHelpText() {
        return "Custom Authenticator to choose between WebAuthn and OTP";
    }

    @Override
    public String getDisplayType() {
        return "Choose Auth Method Authenticator";
    }

    @Override
    public String getReferenceCategory() {
        return "Choose Auth Method Authenticator";
    }

    @Override
    public Requirement[] getRequirementChoices() {
        return new Requirement[]{Requirement.REQUIRED};
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // Initialize any required configuration
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // Perform post-initialization tasks if needed
    }

    @Override
    public void close() {
        // Cleanup resources if needed
    }
}
