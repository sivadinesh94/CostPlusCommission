package com.costcommission.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EnvConfiguration {

    @Value("${smtp.email.username}")
    private String emailUserName;

    @Value("${smtp.email.password}")
    private String emailPassword;

    @Value("${smtp.email.host}")
    private String emailHost;

    @Value("${smtp.email.port}")
    private String smtpPort;

    @Value("${smtp.from.mail}")
    private String fromMail;

}
