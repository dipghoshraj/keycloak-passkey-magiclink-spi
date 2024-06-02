package com.example;
// import static org.keycloak.services.validation.Validation.FIELD_USERNAME;


import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
// import org.keycloak.authentication.authenticators.broker.IdpCreateUserIfUniqueAuthenticator;
import org.keycloak.common.util.SecretGenerator;
import org.keycloak.email.EmailException;
import org.keycloak.email.EmailTemplateProvider;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.*;
// import org.keycloak.services.managers.AuthenticationManager;
// import org.keycloak.services.messages.Messages;
import org.keycloak.sessions.AuthenticationSessionModel;
// import org.keycloak.theme.Theme;

import com.google.common.collect.ImmutableList;
// import com.google.common.collect.Maps;

// import javax.ws.rs.core.Response;
import java.util.HashMap;
// import java.util.Locale;
import java.util.Map;
// import jakarta.ws.rs.core.MultivaluedMap;

import java.util.List;


public class MagicLinkAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        LoginFormsProvider forms = context.form();

        if (user == null) {
            context.failureChallenge(AuthenticationFlowError.INVALID_USER, forms.createLoginUsername());
            return;
        }

        String email = user.getEmail();
        if (email == null) {
            context.failureChallenge(AuthenticationFlowError.INVALID_USER, forms.createLoginUsername());
            return;
        }

        String token = SecretGenerator.getInstance().randomString(24);
        AuthenticationSessionModel authSession = context.getAuthenticationSession();
        authSession.setAuthNote("magic-link-token", token);

        sendMagicLinkEmail(context, user, token);

        // Response challenge = context.form().setSuccess("Magic link sent to your email. Please check your inbox.")
        //     .createForm("magic-link-wait.ftl");
        jakarta.ws.rs.core.Response challenge = context.form().setSuccess("Magic link sent to your email. Please check your inbox.").createForm("magic-link-wait.ftl");
        context.challenge(challenge);
    }

    private void sendMagicLinkEmail(AuthenticationFlowContext context, UserModel user, String token) {
        RealmModel realm = context.getRealm();
        // AuthenticationSessionModel authSession = context.getAuthenticationSession();
        String link = context.getUriInfo().getBaseUriBuilder()
            .path("realms")
            .path(realm.getName())
            .path("magic-link")
            .queryParam("key", token)
            .build()
            .toString();

            try {
                EmailTemplateProvider emailProvider = context.getSession().getProvider(EmailTemplateProvider.class);
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("link", link);
                System.out.println(link);
                
                List<Object> subjAttr = ImmutableList.of(realm.getName());

                emailProvider
                .setRealm(realm)
                .setUser(user)
                .send("magicLinkContinuationSubject", subjAttr, "magic-link-email.ftl", attributes);
            } catch (EmailException e) {
                e.printStackTrace();
            }
    }


    @Override
    public void action(AuthenticationFlowContext context) {
        String token = context.getHttpRequest().getUri().getQueryParameters().getFirst("key");

        LoginFormsProvider forms = context.form();

        if (token == null) {
            context.failureChallenge(AuthenticationFlowError.EXPIRED_CODE, forms.createLoginUsername());
            return;
        }

        String expectedToken = context.getAuthenticationSession().getAuthNote("magic-link-token");
        if (!token.equals(expectedToken)) {
            context.failureChallenge(AuthenticationFlowError.EXPIRED_CODE, forms.createLoginUsername());
            return;
        }

        context.success();
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }

    @Override
    public void close() {
    }
}
