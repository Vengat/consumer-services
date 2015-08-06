/**
 * 
 */
package com.bang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.bang.aspects.SendSimpleMessage;

/**
 * @author vengat.r
 *
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class AspectConfig {
	
	@Bean
	public SendSimpleMessage sendSimpleMessage() {
		return new SendSimpleMessage();
	}

}
