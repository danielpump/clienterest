/**
 * 
 */
package com.rest.client.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import com.rest.client.integration.rest.PlacaCarroRestClient;

/**
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
@Service
@Configurable
public class PlacaCarroIntegracao {
	
	@Autowired
	private PlacaCarroRestClient client;

	public void consultarPorPlaca(String status) {
		
		client.consultarPorStatus(status);

	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("com.rest");

		PlacaCarroIntegracao bean = ctx.getBean(PlacaCarroIntegracao.class);

		bean.consultarPorPlaca("ok");

	}

}
