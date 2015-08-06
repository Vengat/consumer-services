package com.bang.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.bang.aspects.SendSimpleMessage;
 
@Configuration
@ComponentScan
@Import({PersistenceConfig.class, WebConfig.class, AspectConfig.class})
public class AppConfig {
	
 
}
