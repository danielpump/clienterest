/**
 * 
 */
package com.rest.client.integration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.rest.client.integration.rest.PlacaCarroRestClient;

/**
 * Classe de integração entre a aplicação cliente e as interfaces de implementação dos tipos de serviço
 * 
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
@Service
public class PlacaCarroIntegracao {

	/**
	 * Cliente REST da integração<br>
	 * TODO Extrair para um interface para facilitar a troca do tipo de tecnologia do cliente
	 */
	@Autowired
	private PlacaCarroRestClient client;

	/**
	 * Consulta a quantidade de itens cadastrados em um determinado status
	 * 
	 * @param status Status que será consultado
	 * @return Mapa com os valores do JSON da integração
	 * @exception RuntimeException Encapsulamento da exceção lançada da integração
	 */
	public Map<String, Object> consultarPorStatus(String status) {
		try {
			return client.consultarPorStatus(status);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Consulta um veículo pela placa
	 * 
	 * @param placa Placa a ser consultada
	 * @return Mapa com os valores do JSON da integração
	 * @exception RuntimeException Encapsulamento da exceção lançada da integração
	 */
	public Map<String, String> consultarPorPlaca(String placa) {
		try {
			return client.consultarPorPlaca(placa);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Realiza o cadastro de veículo com os dados passados
	 * 
	 * @param placa Placa a ser cadastrada
	 * @param status Status da placa
	 * @return Mapa com os valores do JSON da integração
	 * @exception RuntimeException Encapsulamento da exceção lançada da integração
	 */
	public Map<String, String> cadastrar(String placa, String status) {
		try {
			return client.cadastrar(placa, status);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Atualiza o status de uma placa
	 * 
	 * @param placa Placa que será atualizada
	 * @param status Status para o qual vai ser atualizado
	 * @return Mapa com os valores do JSON da integração
	 * @exception RuntimeException Encapsulamento da exceção lançada da integração
	 */
	public Map<String, String> atualizar(String placa, String status) {
		try {
			return client.atualizarPorPlaca(placa, status);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Remove um veículo da aplicação
	 * 
	 * @param placa Placa do veículo que será removido
	 * @return Mapa com os valores do JSON da integração
	 * @exception RuntimeException Encapsulamento da exceção lançada da integração
	 */
	public Map<String, String> excluir(String placa) {
		try {
			return client.excluirPorPlaca(placa);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Configuracao.class);

		PlacaCarroIntegracao bean = ctx.getBean(PlacaCarroIntegracao.class);

		System.out.println(bean.consultarPorStatus("ok").get("status").toString());
		System.out.println(bean.consultarPorPlaca("ADJ5785").get("status"));
		System.out.println(bean.cadastrar("vvv1235", "bloqueado").get("status"));
		System.out.println(bean.atualizar("vvv1235", "ok").get("status"));
		System.out.println(bean.consultarPorPlaca("vvv1235").get("status"));
		System.out.println(bean.excluir("vvv1235").get("excluido"));
		System.out.println(bean.consultarPorStatus("ok").get("status"));
		// bean.consultarPorStatus("ok");

	}

}
