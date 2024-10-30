package org.forrest.keycloak.sender;

import okhttp3.*;
import org.keycloak.email.EmailException;
import org.keycloak.email.EmailSenderProvider;
import org.keycloak.models.UserModel;
import org.keycloak.models.KeycloakSession;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.Map;

public class SendgridEmailSenderProvider implements EmailSenderProvider {
    private static final Logger logger = Logger.getLogger(SendgridEmailSenderProvider.class);
    protected final KeycloakSession session;
    protected final String apiKey;
    private static final String sendgridEndpoint = "https://api.sendgrid.com/v3/mail/send";

    SendgridEmailSenderProvider(KeycloakSession session, String apiKey) {
        this.session = session;
        this.apiKey = apiKey;
    }

    @Override
    public void send(Map<String, String> config, UserModel user, String subject, String textBody, String htmlBody) throws EmailException {
        send(config, user.getEmail(), subject, textBody, htmlBody);
    }

    @Override
    public void send(Map<String, String> config, String email, String subject, String textBody, String htmlBody) throws EmailException {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new EmailException("apiKey is required");
        }
        try (Response resp = sendRequest(textBody)) {
            if (resp.code() != 202) {
                if (resp.body() != null) {
                    logger.error(resp.body().string());
                }
                throw new EmailException("Status code: " + resp.code());
            }
        } catch (IOException e) {
            throw new EmailException("Failed to send email", e);
        }
    }

    @Override
    public void close() {

    }

    private Response sendRequest(String json) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request.Builder request = new Request.Builder().url(sendgridEndpoint).post(body);

        request.header("Content-Type", "application/json");
        request.header("Authorization", "Bearer " + apiKey);
        return httpClient.newCall(request.build()).execute();
    }
}
