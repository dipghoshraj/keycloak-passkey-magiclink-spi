package com.example.customauthn;


import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
// import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
// import org.keycloak.provider.ProviderConfigProperty;
// import org.keycloak.provider.ProviderConfigurationBuilder;

import com.example.auth.MagicLinkAuthenticator;
// import java.util.List;

public class ChooseAuthMethodAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        // Present the user with a choice between WebAuthn and OTP
        context.challenge(context.form().createForm("choose-auth-method.ftl"));
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        String selectedMethod = context.getHttpRequest().getDecodedFormParameters().getFirst("auth_method");
        UserModel user = context.getUser();
        
        if ("webauthn".equals(selectedMethod)) {
            user.addRequiredAction("webauthn-register");
            // context.getAuthenticationSession().setAuthNote("selected_method", "webauthn");
            context.success();
        } else if ("otp".equals(selectedMethod)) {
            user.removeRequiredAction("webauthn-register");
            // context.getAuthenticationSession().setAuthNote("selected_method", "magic link");

            MagicLinkAuthenticator authenticator = new MagicLinkAuthenticator();
            authenticator.authenticate(context);
            // context.success();
        } else {
            context.failure(AuthenticationFlowError.INVALID_USER);
        }
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true; // Adjust as necessary for your requirements
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // Set required actions if necessary
    }

    @Override
    public void close() {
        // Cleanup resources if needed
    }
}
