package org.forrest.keycloak.sender;

import org.keycloak.Config;
import org.keycloak.email.EmailSenderProvider;
import org.keycloak.email.EmailSenderProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SendgridEmailSenderProviderFactory implements EmailSenderProviderFactory {
    @Override
    public EmailSenderProvider create(KeycloakSession keycloakSession) {
        return new SendgridEmailSenderProvider(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "sendgrid-email-sender-provider";
    }
}
