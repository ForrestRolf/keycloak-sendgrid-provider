# Keycloak SendGrid Provider

Use sendgrid instead of smtp to send emails in keycloak

# Build from source

```shell
mvn clean package
```

Or download directly from [Release](https://github.com/ForrestRolf/keycloak-sendgrid-provider/releases)

# Configurations

**Enable provider**

| Item                                                                                          | Value                            |
|:----------------------------------------------------------------------------------------------|:---------------------------------|
| `--spi-email-sender-provider`                                                                 | sendgrid-email-sender-provider   |
| `--spi-email-template-provider`                                                               | sendgrid-email-template-provider |
| `--spi-email-sender-sendgrid-email-sender-provider-api-key`                                   |                                  |

**Templates**

| Item                                                                                          | Value                            | TemplateData                                                  |
|:----------------------------------------------------------------------------------------------|:---------------------------------|:--------------------------------------------------------------|
| `--spi-email-template-sendgrid-email-template-provider-template-smtp-test`                    |                                  | `welcome`                                                     |
| `--spi-email-template-sendgrid-email-template-provider-template-password-reset`               |                                  | `link`,`linkExpiration`,`firstName`,`lastName`                |
| `--spi-email-template-sendgrid-email-template-provider-template-event`                        |                                  | `eventType`,`userId`,`details`                                |
| `--spi-email-template-sendgrid-email-template-provider-template-verify-email`                 |                                  | `link`,`linkExpiration`,`firstName`,`lastName`                |
| `--spi-email-template-sendgrid-email-template-provider-template-confirm-identity-broker-link` |                                  | `link`,`linkExpiration`,`firstName`,`lastName`                |
| `--spi-email-template-sendgrid-email-template-provider-template-email-update-confirmation`    |                                  | `link`,`linkExpiration`,`firstName`,`lastName`,`confirmation` |
| `--spi-email-template-sendgrid-email-template-provider-template-org-invite-email`             |                                  | `link`,`linkExpiration`,`firstName`,`lastName`,`orgName`      |

> Note: If templateId is not configured, no email will be sent and no error will be reported.

# Deploy

* Copy target/sendgrid-provider-jar-with-dependencies.jar to /opt/keycloak/providers/sendgrid-provider.jar
* Restart Keycloak