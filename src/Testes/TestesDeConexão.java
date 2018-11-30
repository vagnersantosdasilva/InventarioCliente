package Testes;

import static org.junit.Assert.*;

import org.junit.Test;

import Controle.Conexao;
import DAO.MaquinaDAO;
import DAO.PropriedadesDAO;
import Entidades.Maquina;
import Entidades.Propriedades;

public class TestesDeConexão {
	Conexao conexao = new Conexao();
	PropriedadesDAO dao = new PropriedadesDAO();
	Propriedades prop=dao.getPropriedades();
	MaquinaDAO maquinaDAO = new MaquinaDAO();
	Maquina maquina=maquinaDAO.gerarListasDeAcoes();
	
	@Test
	public void testeDeEnvio() 
	{
		//assertEquals(true,conexao.enviar(prop, maquina));
	}
	
	@Test
	public void atualizaçãoAposEnvioPositivo()
	{
		if (conexao.enviar(prop, maquina))
		{
			maquinaDAO.atualizarRelatoriosConsolidados();
			System.out.println("Registros enviados e atualizados");
		}
		
	}
	
	
	
	
}
