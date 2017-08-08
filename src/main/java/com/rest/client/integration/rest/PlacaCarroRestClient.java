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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
@Service
public class PlacaCarroRestClient {

	private static final String SERVER = "http://localhost:8081";
	private static final String RAIZ = "/placa";

	private static final String SERVICO_CONSULTAR = "/consultar";
	private static final String SERVICO_CADASTRAR = "/cadastrar";
	private static final String SERVICO_ATUALIZAR = "/atualizar";
	private static final String SERVICO_EXCLUIR = "/excluir";

	private static final String FIM_URI = "?";

	private static final String PARAMETRO_STATUS = "status=";
	private static final String PARAMETRO_NUMERO = "numero=";

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
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(SERVER).append(RAIZ).append(SERVICO_CONSULTAR).append(FIM_URI).append(PARAMETRO_STATUS)
				.append(status);
		final String uri = stringBuilder.toString();

		String result = requisicaoGet(uri);

		return new ObjectMapper().readValue(result, HashMap.class);
	}

	private String requisicaoGet(final String uri) {
		try {
			String result = template.getForObject(uri, String.class);
			return result;
		} catch (HttpClientErrorException e) {
			throw new RuntimeException(e.getResponseBodyAsString());
		}
	}

	public Map<String, String> consultarPorPlaca(String placa)
			throws JsonParseException, JsonMappingException, IOException {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(SERVER).append(RAIZ).append(SERVICO_CONSULTAR).append(FIM_URI).append(PARAMETRO_NUMERO)
				.append(placa);
		final String uri = stringBuilder.toString();

		String result = requisicaoGet(uri);

		return new ObjectMapper().readValue(result, HashMap.class);
	}

	public Map<String, String> cadastrar(String placa, String status)
			throws JsonParseException, JsonMappingException, IOException {

		final String uri = new StringBuilder().append(SERVER).append(RAIZ).append(SERVICO_CADASTRAR).toString();

		HttpEntity<String> entity = criaHttpEntityParaPost(placa, status);

		ResponseEntity<String> postForEntity = requisicaoPost(uri, entity);

		return new ObjectMapper().readValue(postForEntity.getBody(), HashMap.class);
	}

	private ResponseEntity<String> requisicaoPost(final String uri, HttpEntity<String> entity) {
		try {
			ResponseEntity<String> postForEntity = template.postForEntity(uri, entity, String.class);
			return postForEntity;
		} catch (HttpClientErrorException e) {
			throw new RuntimeException(e.getResponseBodyAsString());
		}
	}

	private HttpEntity<String> criaHttpEntityParaPost(String placa, String status) throws JsonProcessingException {
		HashMap<String, String> objeto = new HashMap<>();

		if (placa != null)
			objeto.put("numero", placa);

		objeto.put("status", status);

		String jsonParaEnviar = new ObjectMapper().writeValueAsString(objeto);

		HttpEntity<String> entity = new HttpEntity<String>(jsonParaEnviar, jsonHeader);
		return entity;
	}

	public Map<String, String> atualizarPorPlaca(String placa, String status)
			throws JsonParseException, JsonMappingException, IOException {
		final String uri = new StringBuilder().append(SERVER).append(RAIZ).append(SERVICO_ATUALIZAR).append(FIM_URI)
				.append(PARAMETRO_NUMERO).append(placa).toString();

		HttpEntity<String> entity = criaHttpEntityParaPost(null, status);

		ResponseEntity<String> postForEntity = requisicaoPost(uri, entity);

		return new ObjectMapper().readValue(postForEntity.getBody(), HashMap.class);
	}

	public Map<String, String> excluirPorPlaca(String placa)
			throws JsonParseException, JsonMappingException, IOException {
		final String uri = new StringBuilder().append(SERVER).append(RAIZ).append(SERVICO_EXCLUIR).append(FIM_URI)
				.append(PARAMETRO_NUMERO).append(placa).toString();

		requisicaoDelete(uri);

		return new ObjectMapper().readValue("{\"excluido\":\"SIM\"}", HashMap.class);
	}

	private void requisicaoDelete(final String uri) {
		try {
			template.delete(uri, new HashMap<>());
		} catch (HttpClientErrorException e) {
			throw new RuntimeException(e.getResponseBodyAsString());
		}
	}

}
