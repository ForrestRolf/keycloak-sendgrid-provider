package org.forrest.keycloak.template;

import org.forrest.keycloak.sendgrid.Templates;
import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.email.EmailTemplateProvider;
import org.keycloak.email.EmailTemplateProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SendgridEmailTemplateProviderFactory implements EmailTemplateProviderFactory {
    private static final String ID = "sendgrid-email-template-provider";
    private static final Logger logger = Logger.getLogger(SendgridEmailTemplateProviderFactory.class);
    protected Templates templates;

    @Override
    public EmailTemplateProvider create(KeycloakSession keycloakSession) {
        return new SendgridEmailTemplateProvider(keycloakSession, templates);
    }

    @Override
    public void init(Config.Scope scope) {
        Templates templates = Templates.builder()
                .smtpTest(scope.get("template-smtp-test"))
                .passwordReset(scope.get("template-password-reset"))
                .event(scope.get("template-event"))
                .verifyEmail(scope.get("template-verify-email"))
                .confirmIdentityBrokerLink(scope.get("template-confirm-identity-broker-link"))
                .emailUpdateConfirmation(scope.get("template-email-update-confirmation"))
                .build();
        logger.infof("Sendgrid email templates %s", templates);
        this.templates = templates;
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
