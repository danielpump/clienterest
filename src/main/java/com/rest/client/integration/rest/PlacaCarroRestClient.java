/**
 * 
 */
package com.rest.client.integration.rest;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

/**
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
@Service
public class PlacaCarroRestClient {
	
	private RestTemplate template;
	
	@PostConstruct
	public void init() {
		template = new RestTemplate();
	}
	
	public void consultarPorStatus(String status) {
		final String uri = "http://localhost:8080/placa/consultar?status=" + status;
		
		String result = template.getForObject(uri, String.class);

		System.out.println(result);
	}

}
