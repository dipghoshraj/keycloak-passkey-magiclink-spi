package com.example.customePassordUpdate;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.services.messages.Messages;
import org.keycloak.services.validation.Validation;
// import org.keycloak.sessions.AuthenticationSessionModel;
// import org.keycloak.services.managers.AuthenticationManager;

import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CustomeUpdatePassword implements RequiredActionProvider {

    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        // You can add custom logic here if needed
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
        context.challenge(context.form()
                .setAttribute("realm", context.getRealm())
                .setAttribute("user", context.getUser())
                .createForm("custom-update-password.ftl"));
    }

    @Override
    public void processAction(RequiredActionContext context) {
        String password = context.getHttpRequest().getDecodedFormParameters().getFirst("password");
        String passwordConfirm = context.getHttpRequest().getDecodedFormParameters().getFirst("password-confirm");

        UserModel user = context.getUser();

        if (Validation.isBlank(password)) {
            context.challenge(context.form()
                    .setAttribute("realm", context.getRealm())
                    .setAttribute("user", context.getUser())
                    .setError(Messages.MISSING_PASSWORD)
                    .createForm("custom-update-password.ftl"));
            return;
        }

        if (!password.equals(passwordConfirm)) {
            context.challenge(context.form()
                    .setAttribute("realm", context.getRealm())
                    .setAttribute("user", context.getUser())
                    .setError(Messages.INVALID_PASSWORD_CONFIRM)
                    .createForm("custom-update-password.ftl"));
            return;
        }

        try {
            user.credentialManager().updateCredential(UserCredentialModel.password(passwordConfirm, false));
            // context.getEvent().success();

            // Log out the user
            context.getSession().sessions().removeUserSessions(context.getRealm(), context.getUser());

            // // Redirect to login page

            // Response response = context.form().createForm(passwordConfirm);
            // context.challenge(response);

            URI loginUri = context.getUriInfo().getBaseUriBuilder()
                    .path("realms")
                    .path(context.getRealm().getName())
                    .path("protocol")
                    .path("openid-connect")
                    .path("auth")
                    .queryParam("client_id", context.getAuthenticationSession().getClient().getClientId())
                    .queryParam("redirect_uri", context.getAuthenticationSession().getRedirectUri())
                    .queryParam("response_type", "code")
                    .queryParam("scope", "openid")
                    .queryParam("code_challenge_method", "S256")
                    .queryParam("code_challenge", generateCodeChallenge(password))
                    .build();

            // Perform the actual redirect to the login page
            Response response = Response.seeOther(loginUri).build();
            context.challenge(response);
        } catch (Exception e) {
            context.challenge(context.form()
                    .setAttribute("realm", context.getRealm())
                    .setAttribute("user", context.getUser())
                    .setError(Messages.INVALID_PASSWORD)
                    .createForm("custom-update-password.ftl"));
        }
    }

    private String generateCodeChallenge(String codeVerifier) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(codeVerifier.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate code challenge", e);
        }
    }

    @Override
    public void close() {
        // No specific resources to close
    }
}
