package com.example.studyApi.mail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailMessage {

    private String to;
    private String nickname;
    private String subject;
    private String message;
}
