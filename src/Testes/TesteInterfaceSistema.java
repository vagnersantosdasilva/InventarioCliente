package Testes;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import DAO.PlacaMaeDAO;
import DAO.ProcessadoresDAO;
import DAO.UnidadeArmazenamentoDAO;
import Entidades.CPU;
import Entidades.PlacaMae;
import Entidades.UnidadeArmazenamento;

public class TesteInterfaceSistema {

	PlacaMae placa;
	CPU processador;
	UnidadeArmazenamento unidade;
	
	
	//O Teste seguinte só faz sentido na máquina que foi gerado o teste inicialmente
	@Test
	public void gerarEObterRelatorioPlacaMae() {
		PlacaMaeDAO placaDAO = new PlacaMaeDAO();
		placaDAO.gerarRelatorioPlacaMae();
		placa =placaDAO.getPlacaMae();
		
		assertEquals("Gigabyte Technology Co., Ltd.",placa.getFabricante());
		assertEquals("M68MT-S2P",placa.getModelo());
		assertEquals("",placa.getSerial());
		assertEquals("OK",placa.getStatus());
	}
	
	@Test
	public void gerarRelatorioEObterInfoCPU(){
		
		ProcessadoresDAO processadorDAO = new ProcessadoresDAO();
		processadorDAO.gerarRelatorioProcessadores();
		List<CPU> listaProcessadores =processadorDAO.getListaProcessadores();
		
		for(CPU processador:listaProcessadores)
		{
			assertEquals("AMD Phenom(tm) II X4 840 Processor",processador.getNome());
			assertEquals("AuthenticAMD",processador.getFabricante());
			assertEquals("4",processador.getNumeroNucleos());
			assertEquals("4",processador.getNumeroProcessadoresLogicos());
			assertEquals("3200",processador.getFrequenciaMaxima());
			assertEquals("64",processador.getArquitetura());
			assertEquals("OK",processador.getStatus());
			
//			System.out.println(processador.getNome());
//			System.out.println(processador.getFabricante());
//			System.out.println(processador.getNumeroDeNucleos());
//			System.out.println(processador.getNumeroDeProcessadoresLogicos());
//			System.out.println(processador.getFrequenciaMaxima());
//			System.out.println(processador.getArquitetura());
//			System.out.println(processador.getStatus());
			
		}
	}
		
		@Test
		public void gerarRelatorioObterListaHD()
		{
			
			UnidadeArmazenamentoDAO unidadeDAO = new UnidadeArmazenamentoDAO();
			unidadeDAO.gerarRelatorioDeDiscos();
			List<UnidadeArmazenamento> listaHds = unidadeDAO.getListaDeDiscos();
			for (UnidadeArmazenamento disco :listaHds)
			{
				System.out.println("Nome :"+disco.getNome());
				System.out.println("Tamanho :"+disco.getTamanho());
				System.out.println("Tipo de Interface :"+disco.getTipoDeInterface());
				System.out.println("Tipo de Medea :"+disco.getTipoDeMidea());
				System.out.println("Status :"+disco.getStatus());
			}
			
		}
		
	
}
