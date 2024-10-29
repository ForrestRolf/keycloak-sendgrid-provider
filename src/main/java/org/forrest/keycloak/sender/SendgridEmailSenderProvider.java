package org.forrest.keycloak.sender;

import org.forrest.keycloak.sendgrid.SendgridMail;
import org.keycloak.email.EmailException;
import org.keycloak.email.EmailSenderProvider;
import org.keycloak.models.UserModel;
import org.keycloak.models.KeycloakSession;
import org.jboss.logging.Logger;

import java.util.Map;

public class SendgridEmailSenderProvider implements EmailSenderProvider {
    private static final Logger logger = Logger.getLogger(SendgridEmailSenderProvider.class);
    protected final KeycloakSession session;

    SendgridEmailSenderProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void send(Map<String, String> config, UserModel user, String subject, String textBody, String htmlBody) throws EmailException {
        logger.info("---------------- 1");
        logger.info("Sending email to " + user.getEmail());
        logger.info(config.toString());
        logger.info(subject);
        logger.info(textBody);
        logger.info(htmlBody);
    }

    @Override
    public void send(Map<String, String> config, String email, String subject, String textBody, String htmlBody) throws EmailException {
        logger.info("---------------- 2");
        logger.info("Sending email to " + config.toString());
        logger.info(email);
        logger.info(subject);
        logger.info(textBody);
        logger.info(htmlBody);
    }

    @Override
    public void close() {

    }
}
