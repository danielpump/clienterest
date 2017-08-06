/**
 * 
 */
package com.rest.client.integration.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
@Service
public class PlacaCarroRestClient {

	private RestTemplate template;
	private HttpHeaders jsonHeader;

	@PostConstruct
	public void init() {
		template = new RestTemplate();
		jsonHeader = new HttpHeaders();
		jsonHeader.setContentType(MediaType.APPLICATION_JSON);
	}

	public Map<String, String> consultarPorStatus(String status)
			throws JsonParseException, JsonMappingException, IOException {
		final String uri = "http://localhost:8080/placa/consultar?status=" + status;

		String result = template.getForObject(uri, String.class);

		return new ObjectMapper().readValue(result, HashMap.class);
	}

	public Map<String, String> consultarPorPlaca(String placa)
			throws JsonParseException, JsonMappingException, IOException {
		final String uri = "http://localhost:8080/placa/consultar?numero=" + placa;

		String result = template.getForObject(uri, String.class);

		return new ObjectMapper().readValue(result, HashMap.class);
	}

	public Map<String, String> cadastrar(String placa, String status)
			throws JsonParseException, JsonMappingException, IOException {
		final String url = "http://localhost:8080/placa/cadastrar";

		HashMap<String, String> objeto = new HashMap<>();

		objeto.put("numero", placa);
		objeto.put("status", status);

		String jsonParaEnviar = new ObjectMapper().writeValueAsString(objeto);

		HttpEntity<String> entity = new HttpEntity<String>(jsonParaEnviar, jsonHeader);

		ResponseEntity<String> postForEntity = template.postForEntity(url, entity, String.class);

		return new ObjectMapper().readValue(postForEntity.getBody(), HashMap.class);
	}

	public Map<String, String> atualizarPorPlaca(String placa, String status)
			throws JsonParseException, JsonMappingException, IOException {
		final String url = "http://localhost:8080/placa/atualizar?numero=" + placa;

		HashMap<String, String> objeto = new HashMap<>();

		objeto.put("status", status);

		String jsonParaEnviar = new ObjectMapper().writeValueAsString(objeto);

		HttpEntity<String> entity = new HttpEntity<String>(jsonParaEnviar, jsonHeader);

		ResponseEntity<String> postForEntity = template.postForEntity(url, entity, String.class);

		return new ObjectMapper().readValue(postForEntity.getBody(), HashMap.class);
	}

	public Map<String, String> excluirPorPlaca(String placa)
			throws JsonParseException, JsonMappingException, IOException {
		final String url = "http://localhost:8080/placa/excluir?numero=" + placa;

		template.delete(url, new HashMap<>());

		return new ObjectMapper().readValue("{\"excluido\":\"OK\"}", HashMap.class);
	}

}
