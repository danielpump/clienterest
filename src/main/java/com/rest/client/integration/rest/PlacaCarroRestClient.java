/**
 * 
 */
package com.rest.client.integration.rest;

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

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Cliente REST da integração dos serviços de veículo
 * 
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
@Service
public class PlacaCarroRestClient {

	private ObjectMapper objectMapper;
	/**
	 * Endereço do servidor<br>
	 * TODO Abstrair para um property
	 */
	private static final String SERVER = "http://localhost:8081";
	/**
	 * Raiz dos serviços utilizados
	 */
	private static final String RAIZ = "/placa";

	/**
	 * Path do endereço de consulta
	 */
	private static final String SERVICO_CONSULTAR = "/consultar";
	/**
	 * Path do endereço de cadastro
	 */
	private static final String SERVICO_CADASTRAR = "/cadastrar";
	/**
	 * Path do endereço de atualização
	 */
	private static final String SERVICO_ATUALIZAR = "/atualizar";
	/**
	 * Path do endereço de exclusão
	 */
	private static final String SERVICO_EXCLUIR = "/excluir";

	/**
	 * Separação do fim da URL para os parametros
	 */
	private static final String FIM_URI = "?";

	/**
	 * Parametro status
	 */
	private static final String PARAMETRO_STATUS = "status=";
	/**
	 * Parametro numero
	 */
	private static final String PARAMETRO_NUMERO = "numero=";

	private RestTemplate template;
	/**
	 * Cabeçalho padrão das requisições
	 */
	private HttpHeaders jsonHeader;

	/**
	 * Inicializa os atributos da classe
	 */
	@PostConstruct
	public void init() {
		template = new RestTemplate();
		jsonHeader = new HttpHeaders();
		jsonHeader.setContentType(MediaType.APPLICATION_JSON);
		objectMapper = new ObjectMapper();
	}

	/**
	 * Contabiliza a quantidade de registros para um status
	 * 
	 * @param status Status para contabilizar
	 * @return Mapa com os valores do JSON da integração
	 * @throws Exception Lança a exceção da integração
	 */
	public Map<String, String> consultarPorStatus(String status) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(SERVER).append(RAIZ).append(SERVICO_CONSULTAR).append(FIM_URI).append(PARAMETRO_STATUS)
				.append(status);
		final String uri = stringBuilder.toString();

		String result = requisicaoGet(uri);

		return objectMapper.readValue(result, HashMap.class);
	}

	/**
	 * Executa a consulta da requisição GET
	 * 
	 * @param uri URL a ser consultada
	 * @return Resposta da consulta
	 */
	private String requisicaoGet(final String uri) {
		try {
			String result = template.getForObject(uri, String.class);
			return result;
		} catch (HttpClientErrorException e) {
			throw new RuntimeException(e.getResponseBodyAsString());
		}
	}

	/**
	 * Consulta um veículo pela placa
	 * 
	 * @param placa Placa a ser consultada
	 * @return Mapa com os valores do JSON da integração
	 * @throws Exception Lança a exceção da integração
	 */
	public Map<String, String> consultarPorPlaca(String placa) throws Exception {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(SERVER).append(RAIZ).append(SERVICO_CONSULTAR).append(FIM_URI).append(PARAMETRO_NUMERO)
				.append(placa);
		final String uri = stringBuilder.toString();

		String result = requisicaoGet(uri);

		return objectMapper.readValue(result, HashMap.class);
	}

	/**
	 * Realiza o cadastro do veículo com as informações passadas
	 * 
	 * @param placa Placa para o cadastro
	 * @param status Status para o cadastro
	 * @return Mapa com os valores do JSON da integração
	 * @throws Exception Lança a exceção da integração
	 */
	public Map<String, String> cadastrar(String placa, String status) throws Exception {

		final String uri = new StringBuilder().append(SERVER).append(RAIZ).append(SERVICO_CADASTRAR).toString();

		HttpEntity<String> entity = criaHttpEntityParaPost(placa, status);

		ResponseEntity<String> postForEntity = requisicaoPost(uri, entity);

		return objectMapper.readValue(postForEntity.getBody(), HashMap.class);
	}

	/**
	 * Executa a requisição POST
	 * @param uri URL da requisição
	 * @param entity Corpo da requisição
	 * @return Mapa com os valores do JSON da integração
	 * @throws Exception Lança a exceção da integração
	 */
	private ResponseEntity<String> requisicaoPost(final String uri, HttpEntity<String> entity) {
		try {
			ResponseEntity<String> postForEntity = template.postForEntity(uri, entity, String.class);
			return postForEntity;
		} catch (HttpClientErrorException e) {
			throw new RuntimeException(e.getResponseBodyAsString());
		}
	}

	/**
	 * Cria o corpo para a requisição POST
	 * @param placa Placa para o corpo da mensagem (Pode ser null)
	 * @param status Status para o corpo da mensagem
	 * @return Entidade que representa o corpo da mensagem
	 * @throws Exception Exceção lançada na criação do objeto
	 */
	private HttpEntity<String> criaHttpEntityParaPost(String placa, String status) throws Exception {
		HashMap<String, String> objeto = new HashMap<>();

		if (placa != null)
			objeto.put("numero", placa);

		objeto.put("status", status);

		String jsonParaEnviar = objectMapper.writeValueAsString(objeto);

		HttpEntity<String> entity = new HttpEntity<String>(jsonParaEnviar, jsonHeader);
		return entity;
	}

	/**
	 * Atualiza um veículo pela plca
	 * 
	 * @param placa Placa para atualizar
	 * @param status Novo status
	 * @return Mapa com os valores do JSON da integração
	 * @throws Exception Lança a exceção da integração
	 */
	public Map<String, String> atualizarPorPlaca(String placa, String status) throws Exception {
		final String uri = new StringBuilder().append(SERVER).append(RAIZ).append(SERVICO_ATUALIZAR).append(FIM_URI)
				.append(PARAMETRO_NUMERO).append(placa).toString();

		HttpEntity<String> entity = criaHttpEntityParaPost(null, status);

		ResponseEntity<String> postForEntity = requisicaoPost(uri, entity);

		return objectMapper.readValue(postForEntity.getBody(), HashMap.class);
	}

	/**
	 * Exclui um veículo pela placa
	 * 
	 * @param placa Placa para excluir
	 * @return Mapa com os valores do JSON da integração
	 * @throws Exception Lança a exceção da integração
	 */
	public Map<String, String> excluirPorPlaca(String placa) throws Exception {
		final String uri = new StringBuilder().append(SERVER).append(RAIZ).append(SERVICO_EXCLUIR).append(FIM_URI)
				.append(PARAMETRO_NUMERO).append(placa).toString();

		requisicaoDelete(uri);

		return objectMapper.readValue("{\"excluido\":\"SIM\"}", HashMap.class);
	}

	/**
	 * Executa a requisição DELETE
	 * 
	 * @param uri URL da requisição
	 * @return Mapa com os valores do JSON da integração
	 * @throws Exception Lança a exceção da integração
	 */
	private void requisicaoDelete(final String uri) {
		try {
			template.delete(uri, new HashMap<>());
		} catch (HttpClientErrorException e) {
			throw new RuntimeException(e.getResponseBodyAsString());
		}
	}

}
