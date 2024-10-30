package org.forrest.keycloak.sendgrid;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Templates {
    String smtpTest;
    String passwordReset;
    String event;
    String confirmIdentityBrokerLink;
    String verifyEmail;
    String emailUpdateConfirmation;
    String orgInviteEmail;
}
