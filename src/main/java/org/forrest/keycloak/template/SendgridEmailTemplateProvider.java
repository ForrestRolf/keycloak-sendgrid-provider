package org.forrest.keycloak.template;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.forrest.keycloak.sendgrid.SendgridMail;
import org.forrest.keycloak.sendgrid.Templates;
import org.jboss.logging.Logger;
import org.keycloak.email.EmailException;
import org.keycloak.email.EmailSenderProvider;
import org.keycloak.email.EmailTemplateProvider;
import org.keycloak.events.Event;
import org.keycloak.models.*;
import org.keycloak.sessions.AuthenticationSessionModel;

import java.util.*;


public class SendgridEmailTemplateProvider implements EmailTemplateProvider {
    private static final Logger logger = Logger.getLogger(SendgridEmailTemplateProvider.class);

    protected KeycloakSession session;
    protected AuthenticationSessionModel authenticationSession;
    protected RealmModel realm;
    protected UserModel user;
    protected final Map<String, Object> attributes = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);;
    protected Templates templates;

    SendgridEmailTemplateProvider(KeycloakSession session, Templates templates) {
        this.session = session;
        this.templates = templates;
    }

    @Override
    public EmailTemplateProvider setAuthenticationSession(AuthenticationSessionModel authenticationSessionModel) {
        this.authenticationSession = authenticationSessionModel;
        return this;
    }

    @Override
    public EmailTemplateProvider setRealm(RealmModel realmModel) {
        this.realm = realmModel;
        return this;
    }

    @Override
    public EmailTemplateProvider setUser(UserModel userModel) {
        this.user = userModel;
        return this;
    }

    @Override
    public EmailTemplateProvider setAttribute(String name, Object value) {
        attributes.put(name, value);
        return this;
    }

    @Override
    public void sendEvent(Event event) throws EmailException {
        Map<String, String> config = realm.getSmtpConfig();
        Map<String, Object> attributes = new HashMap<>(this.attributes);
        attributes.put("eventType", event.getType().toString());
        attributes.put("userId", event.getUserId());
        attributes.put("details", event.getDetails());

        send(config, processTemplate(templates.getEvent(), config.get("from"), user.getEmail(), attributes));
    }

    @Override
    public void sendPasswordReset(String link, long expirationInMinutes) throws EmailException {
        sendLink(link, expirationInMinutes);
    }

    @Override
    public void sendSmtpTestEmail(Map<String, String> config, UserModel userModel) throws EmailException {
        setRealm(session.getContext().getRealm());
        setUser(userModel);
        Map<String, Object> attributes = new HashMap<>(this.attributes);

        attributes.put("welcome", "This is a test email from keycloak");
        send(config, processTemplate(templates.getSmtpTest(), config.get("from"), userModel.getEmail(), attributes));
    }

    @Override
    public void sendConfirmIdentityBrokerLink(String link, long expirationInMinutes) throws EmailException {
        sendLink(link, expirationInMinutes);
    }

    @Override
    public void sendExecuteActions(String link, long expirationInMinutes) throws EmailException {
        sendLink(link, expirationInMinutes);
    }

    @Override
    public void sendVerifyEmail(String link, long expirationInMinutes) throws EmailException {
        sendLink(link, expirationInMinutes);
    }

    @Override
    public void sendOrgInviteEmail(OrganizationModel organizationModel, String link, long expirationInMinutes) throws EmailException {
        this.attributes.put("orgName", organizationModel.getName());
        sendLink(link, expirationInMinutes);
    }

    @Override
    public void sendEmailUpdateConfirmation(String link, long expirationInMinutes, String s1) throws EmailException {
        this.attributes.put("confirmation", s1);
        sendLink(link, expirationInMinutes);
    }

    @Override
    public void send(String subject, String bodyTemplate, Map<String, Object> bodyAttributes) throws EmailException {

    }

    @Override
    public void send(String subject, List<Object> subjectAttributes, String bodyTemplate, Map<String, Object> bodyAttributes) throws EmailException {

    }

    public void send(Map<String, String> config, SendgridMail mail) throws EmailException {
        if (mail.getTemplateId() == null || mail.getTemplateId().isEmpty()) {
            return;
        }

        String body = "";
        try {
            body = objectMapper.writeValueAsString(mail);
        } catch (JsonProcessingException e) {
            throw new EmailException(e.getMessage());
        }

        EmailSenderProvider emailSender = session.getProvider(EmailSenderProvider.class);
        emailSender.send(config, user, "", body, "");
    }

    private void sendLink(String link, long expirationInMinutes) throws EmailException {
        Map<String, String> config = realm.getSmtpConfig();
        Map<String, Object> attributes = new HashMap<>(this.attributes);
        attributes.put("link", link);
        attributes.put("linkExpiration", expirationInMinutes);
        attributes.put("firstName", user.getFirstName());
        attributes.put("lastName", user.getLastName());

        send(config, processTemplate(templates.getPasswordReset(), config.get("from"), user.getEmail(), attributes));
    }

    protected SendgridMail processTemplate(String templateId, String sender, String to, Map<String, Object> attributes) {
        List<SendgridMail.Personalization> personalizations = new ArrayList<>();
        List<SendgridMail.Attachment> attachments = null;
        SendgridMail.Email from = new SendgridMail.Email(sender);
        List<SendgridMail.Email> recipient = new ArrayList<SendgridMail.Email>(){{
            add(new SendgridMail.Email(to));
        }};

        personalizations.add(new SendgridMail.Personalization(recipient, attributes));
        return new SendgridMail(templateId, from, personalizations, attachments);
    }

    @Override
    public void close() {

    }
}
