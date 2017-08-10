/**
 * 
 */
package com.rest.client.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

/**
 * Cenários de testes para a integração
 * 
 * @author Daniel Ferraz
 * @since 9 de ago de 2017
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Configuracao.class })
public class PlacaCarroIntegracaoTest {

	@Autowired
	private PlacaCarroIntegracao integracao;

	@Autowired
	private RestTemplate operations;

	private MockRestServiceServer mockServer;

	@Before
	public void setUp() {
		mockServer = MockRestServiceServer.createServer(operations);
	}

	@Test
	public void testeDeConsultaDeVeiculoPorPlaca() {

		mockServer.expect(requestTo("http://localhost:8081/placa/consultar?numero=AAA1111"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess("{\"numero\":\"AAA1111\",\"status\":\"OK\"}", MediaType.APPLICATION_JSON_UTF8));

		Map<String, String> veiculo = integracao.consultarPorPlaca("AAA1111");

		mockServer.verify();
		assertThat(veiculo.get("numero")).isEqualTo("AAA1111");
		assertThat(veiculo.get("status")).isEqualTo("OK");

	}

	@Test
	public void testeDeConsultaDeQuantidadeDeVeiculoPorStatus() {

		mockServer.expect(requestTo("http://localhost:8081/placa/consultar?status=OK"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess("{\"status\":\"OK\",\"quantidade\":5}", MediaType.APPLICATION_JSON_UTF8));

		Map<String, Object> veiculo = integracao.consultarPorStatus("OK");

		mockServer.verify();
		assertThat(veiculo.get("quantidade")).isEqualTo(5);
		assertThat(veiculo.get("status")).isEqualTo("OK");

	}

	@Test
	public void testeDeExclusaoDeVeiculoPorPlaca() {

		mockServer.expect(requestTo("http://localhost:8081/placa/excluir?numero=AAA1111"))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withSuccess("{\"numero\":\"AAA1111\",\"status\":\"OK\"}", MediaType.APPLICATION_JSON_UTF8));

		Map<String, String> veiculo = integracao.excluir("AAA1111");

		mockServer.verify();
		assertThat(veiculo.get("excluido")).isEqualTo("SIM");

	}

	@Test
	public void testeDeAtualizacaoDeVeiculoPorPlaca() {

		mockServer.expect(requestTo("http://localhost:8081/placa/atualizar?numero=AAA1111"))
				.andExpect(method(HttpMethod.POST)).andExpect(content().string("{\"status\":\"OK\"}"))
				.andRespond(withSuccess("{\"numero\":\"AAA1111\",\"status\":\"OK\"}", MediaType.APPLICATION_JSON_UTF8));

		Map<String, String> veiculo = integracao.atualizar("AAA1111", "OK");

		mockServer.verify();
		assertThat(veiculo.get("numero")).isEqualTo("AAA1111");
		assertThat(veiculo.get("status")).isEqualTo("OK");

	}
	
	@Test
	public void testeDeCadastroDeVeiculo() {

		mockServer.expect(requestTo("http://localhost:8081/placa/cadastrar"))
				.andExpect(method(HttpMethod.POST)).andExpect(content().string("{\"numero\":\"AAA1111\",\"status\":\"OK\"}"))
				.andRespond(withSuccess("{\"numero\":\"AAA1111\",\"status\":\"OK\"}", MediaType.APPLICATION_JSON_UTF8));

		Map<String, String> veiculo = integracao.cadastrar("AAA1111", "OK");

		mockServer.verify();
		assertThat(veiculo.get("numero")).isEqualTo("AAA1111");
		assertThat(veiculo.get("status")).isEqualTo("OK");

	}

}
