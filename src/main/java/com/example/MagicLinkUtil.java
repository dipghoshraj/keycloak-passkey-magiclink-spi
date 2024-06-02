package com.example;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.email.EmailException;
import org.keycloak.email.EmailTemplateProvider;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.services.validation.Validation;

import com.google.common.collect.ImmutableList;

// import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MagicLinkUtil {

    public static void sendMagicLink(RequiredActionContext context) {
        String email = context.getUser().getEmail();
        
        if (!Validation.isBlank(email)) {
            String magicLink = generateMagicLink(context);
            sendEmail(email, magicLink, context);
            context.challenge(context.form().createForm("magic-link-sent.ftl"));
        } else {
            // context.failure(context.form().setError("Invalid email").createForm("error.ftl"));
        }
    }

    private static String generateMagicLink(RequiredActionContext context) {
        String token = context.generateCode();
        return "https://your-app.com/magic-link?token=" + token;
    }

    private static void sendEmail(String email, String magicLink, RequiredActionContext context) {
        try {

            EmailTemplateProvider emailProvider = context.getSession().getProvider(EmailTemplateProvider.class);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("link", magicLink);
            System.out.println(magicLink);

            RealmModel realm = context.getRealm();
            UserModel user = context.getUser();

            List<Object> subjAttr = ImmutableList.of(realm.getName());

            emailProvider
                .setRealm(realm)
                .setUser(user)
                .send("magicLinkContinuationSubject", subjAttr, "magic-link.ftl", attributes);

        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
