package Testes;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import DAO.LogErroDAO;
import Entidades.LogErro;

public class TestesLogsDeErro {

	LogErroDAO dao = new LogErroDAO();
	@Test
	public void testLeituraDeLogs() {
		dao.executarScriptDeLeituraDeLogs();
		List  listaDeLogs =  dao.getListaDeLogsDeErro();
		
		/*
		for (int i =0 ;i<listaDeLogs.size();i++) {
			LogErro log = (LogErro)listaDeLogs.get(i);
			System.out.println("Máquina			:"+log.getCodigoMaquina());
			System.out.println("Categoria		:"+log.getCategory());
			System.out.println("EventCode		:"+log.getEventCode());
			System.out.println("LogFile			:"+log.getLogFile());
			System.out.println("Message			:"+log.getMessage());
			System.out.println("TimeGenerated	:"+log.getTimeGenerated());
			System.out.println("Type 			:"+log.getType());
			System.out.println("User 			:"+log.getUser());
			System.out.println("");
		}*/
	}
	
	/*Pode haver uma quantidade imensa de logs de erro ! 
	 * Deve-se questionar a possibilidade de limitar o número de registros a 
	 * serem enviados ao servidor por máquina. 
	 */
	@Test
	public void testDecisaoDeInclusaoExclusao() {
		dao.executarScriptDeLeituraDeLogs();
		List listaComAcoes =dao.gerarListaDeAcoes();
		List listaTemporaria=dao.getlistaDeLogsDeErro("repositorio/erros.ivt");
		List listaConsolidada=dao.getlistaDeLogsDeErro("repositorioConsolidado/erros.ivt");
		
		System.out.println("Obtendo Comandos....");
		for (int i =0 ;i<listaComAcoes.size();i++) {
			LogErro log = (LogErro)listaComAcoes.get(i);
			System.out.println("Máquina			:"+log.getCodigoMaquina());
			System.out.println("Type			:"+log.getType());
			System.out.println("Mensagem		:"+log.getMessage());
			System.out.println("Data			:"+log.getTimeGenerated());
			System.out.println("Comando			:"+log.getComando());
			System.out.println("");
		}
		System.out.println("Candidatos a modificação:"+listaComAcoes.size());
		System.out.println("Número de Temporario	:"+listaTemporaria.size());
		System.out.println("Número de Consolidado	:"+listaConsolidada.size());
		
		System.out.println("Atualizando listas...");
		if (dao.atualizarListaConsolidada()) System.out.println("listas Atualizadas...");;
		
		System.out.println("Candidatos a modificação:"+listaComAcoes.size());
		System.out.println("Número de Temporario	:"+listaTemporaria.size());
		System.out.println("Número de Consolidado	:"+listaConsolidada.size());
		assertEquals(0,listaComAcoes.size());
		
	}

}
