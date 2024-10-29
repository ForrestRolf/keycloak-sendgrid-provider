package org.forrest.keycloak.sendgrid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendgridMail {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Attachment {
        String contentId;
        String content;
        String type;
        String filename;
        String disposition;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Personalization {
        String to;
        Map<String, Object> dynamicTemplateData;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class From {
        String email;
    }

    String templateId;
    From from;
    List<Personalization> personalizations;
    List<Attachment> attachments;
}
