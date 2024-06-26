package com.example.chooseCompletion;
// import java.util.Collections;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
// import org.keycloak.models.KeycloakSession;
// import org.keycloak.theme.freemarker.DefaultFreeMarkerProvider;
import org.keycloak.models.UserModel;

// import java.io.IOException;
// import java.io.StringWriter;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.UUID;
// import javax.ws.rs.core.Response;
// import java.util.Locale;



import org.keycloak.services.messages.Messages;
// import org.keycloak.theme.Theme;
// import freemarker.template.Template;
// import freemarker.template.TemplateException;



public class ChooseCompletion implements RequiredActionProvider {

    
    public static final String PROVIDER_ID = "Choose_complition";


    @Override
    public void evaluateTriggers(RequiredActionContext requiredActionContext) {

    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
        jakarta.ws.rs.core.Response challenge = context.form().createForm("choose-completion-method.ftl");
        context.challenge(challenge);
    }

    @Override
    public void processAction(RequiredActionContext context) {
        String selectedMethod = context.getHttpRequest().getDecodedFormParameters().getFirst("completion_method");

        UserModel user = context.getUser();


        user.removeRequiredAction("webauthn-register");
        // user.removeRequiredAction("magic-link");

        if ("email".equals(selectedMethod)) {
            user.addRequiredAction(UserModel.RequiredAction.VERIFY_EMAIL);
            context.success();
        } else if ("webauthn".equals(selectedMethod)) {
            context.getUser().addRequiredAction("webauthn-register");
            context.success();
        } else if ("magic-link".equals(selectedMethod)) {
            // String magicLinkToken = UUID.randomUUID().toString();
            // context.getAuthenticationSession().setAuthNote("magicLinkToken", magicLinkToken);
            // context.getAuthenticationSession().setAuthNote("magicLinkEmail", user.getEmail());

            // MagicLinkAuthenticator authenticator = new MagicLinkAuthenticator();
            // authenticator.authenticate(context.AuthenticationFlowContext());

            context.success();

            // Here you would send an email with the magic link
            // context.success();
        } else {
            context.challenge(context.form().setError(Messages.INVALID_REQUEST).createForm("choose-completion-method.ftl"));
        }
    }

    @Override
    public void close() {

    }
}
