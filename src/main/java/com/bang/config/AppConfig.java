package com.bang.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
 
@Configuration
@Import({PersistenceConfig.class, WebConfig.class})
public class AppConfig {
 
}
