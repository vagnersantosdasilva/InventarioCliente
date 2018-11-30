package Testes;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import DAO.PropriedadesDAO;
import Entidades.Propriedades;

public class TestePropriedades {

	PropriedadesDAO dao = new PropriedadesDAO();
	@Test
	public void retornoDeProrpriedadesDAO() 
	{
		
		Propriedades propriedades = (Propriedades)dao.getPropriedades();
		propriedades.setTempoDeAtualizacao(Util.Propriedades.lePropriedades("propriedades/propriedades_sistema.cfg", "tempoRefresh"));
		assertEquals("10.0.0.100", propriedades.getServidor());
		assertEquals("1050", propriedades.getPorta());
		//assertEquals("C:\\Windows", propriedades.getWinDir());
		assertEquals("3000", propriedades.getTempoDeAtualizacao());
		
		
		
	}
	@Test
	public void testeObtençaoVariaveisAmbiente()
	{
		
			//dao.gravarVariaveisDoSistema();
			//dao.gravarDatasDeUltimaAtualizacao();
			//System.out.println(System.getenv("ProgramFiles"));
			//System.out.println(System.getenv("ProgramFiles(x86)"));
			//System.out.println(System.getenv("WinDir"));
			//System.out.println(System.getenv("teste"));
			assertEquals(false, dao.modificado("winUpdate"));
			
		
	}

}
