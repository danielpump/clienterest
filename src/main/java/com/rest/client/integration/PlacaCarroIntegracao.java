/**
 * 
 */
package com.rest.client.integration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.rest.client.integration.rest.PlacaCarroRestClient;

/**
 * @author Daniel Ferraz
 * @since 6 de ago de 2017
 *
 */
@Service
public class PlacaCarroIntegracao {

	@Autowired
	private PlacaCarroRestClient client;

	public  Map<String, String> consultarPorStatus(String status) {
		try {
			return client.consultarPorStatus(status);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public  Map<String, String> consultarPorPlaca(String placa) {
		try {
			return client.consultarPorPlaca(placa);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public  Map<String, String> cadastrar(String placa, String status) {
		try {
			return client.cadastrar(placa, status);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			System.out.println(e.getResponseBodyAsString());
			throw new RuntimeException(e);
		}	catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public  Map<String, String> atualizar(String placa, String status) {
		try {
			return client.atualizarPorPlaca(placa, status);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, String> excluir(String placa) {
		try {
			return client.excluirPorPlaca(placa);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("com.rest.client");

		PlacaCarroIntegracao bean = ctx.getBean(PlacaCarroIntegracao.class);

		System.out.println( bean.consultarPorStatus("ok").get("status").toString());
		System.out.println(bean.consultarPorPlaca("ADJ5785").get("status"));
		System.out.println(bean.cadastrar("vvv1235", "bloqueado").get("status"));
		System.out.println(bean.atualizar("vvv1235", "ok").get("status"));
		System.out.println(bean.consultarPorPlaca("vvv1235").get("status"));
		System.out.println(bean.excluir("vvv1235").get("excluido"));
		System.out.println( bean.consultarPorStatus("ok").get("status"));
//		bean.consultarPorStatus("ok");

	}

}
