/**
 * 
 */
package com.rest.client.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Indica as configurações do Spring
 * 
 * @author Daniel Ferraz
 * @since 9 de ago de 2017
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.rest.client" })
public class Configuracao {
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
