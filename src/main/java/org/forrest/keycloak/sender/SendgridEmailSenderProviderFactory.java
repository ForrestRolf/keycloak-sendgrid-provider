package org.forrest.keycloak.sender;

import org.keycloak.Config;
import org.keycloak.email.EmailSenderProvider;
import org.keycloak.email.EmailSenderProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SendgridEmailSenderProviderFactory implements EmailSenderProviderFactory {
    private static final String ID = "sendgrid-email-sender-provider";
    private String apiKey;

    @Override
    public EmailSenderProvider create(KeycloakSession keycloakSession) {
        return new SendgridEmailSenderProvider(keycloakSession, apiKey);
    }

    @Override
    public void init(Config.Scope scope) {
        this.apiKey = scope.get("api-key");
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return ID;
    }
}
