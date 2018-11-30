package Testes;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import DAO.AdaptadoresDeRedeDAO;
import DAO.CPUDAO;
import DAO.CdromDAO;
import DAO.HotFixDAO;
import DAO.MemoriasDAO;
import DAO.PlacaMaeDAO;
import DAO.SODAO;
import DAO.SoftwareDAO;
import DAO.SomDAO;
import DAO.UnidadeArmazenamentoDAO;
import DAO.VideoDAO;
import Entidades.AdaptadorDeRede;
import Entidades.CPU;
import Entidades.Cdrom;
import Entidades.HotFixWindows;
import Entidades.Memoria;
import Entidades.PlacaMae;
import Entidades.SO;
import Entidades.Software;
import Entidades.Som;
import Entidades.UnidadeArmazenamento;
import Entidades.Video;
import Util.Conversor;
import Util.CoparadorDeArquivos;

public class TesteComparadorArquivos {
	
	CoparadorDeArquivos coparador = new CoparadorDeArquivos();

	@Test
	public void test() {
	//	assertEquals (true,coparador.equals("repositorio/programas.ivt", "repositorioConsolidado/programas.ivt"));
	}
	
	/*
	@Test
	public void retornoListaDefinitiva()
	{
		SoftwareDAO dao = new SoftwareDAO();
		//dao.gerarRelatorio();
		
		List lista =dao.gerarListaDeAcoes();
		//List lista =dao.getListaDeSoftwares();
		
		//assertEquals(true, dao.existeSoftwareEmLista(lista, "Saraiva Reader ALPHA_7RC11b165"));
		System.out.println("Tamanho Lista Definitiva :"+lista.size());
		for(int i=0;i<=lista.size()-1;i++)
		{
			Software sof = (Software)lista.get(i);
			System.out.println(i+" - "+sof.getComando()+" - "+sof.getNome());
		}
		
	}*/
	@Test
	public void retornoListaDefinitivaAdaptadoresRede()
	{
		AdaptadoresDeRedeDAO dao = new AdaptadoresDeRedeDAO();
		//dao.gerarRelatorio();
		
		List lista =dao.gerarListaDeAcoes();
		//List lista =dao.getListaDeSoftwares();
		
		//assertEquals(true, dao.existeSoftwareEmLista(lista, "Saraiva Reader ALPHA_7RC11b165"));
		System.out.println("Tamanho Lista Definitiva :"+lista.size());
		for(int i=0;i<=lista.size()-1;i++)
		{
			AdaptadorDeRede adapt = (AdaptadorDeRede)lista.get(i);
			System.out.println(i+" - "+adapt.getComando()+" - "+adapt.getNome()+" MAC: "+adapt.getMacAdress());
		}
		
	}
	
	@Test
	public void retornoListaDefinitivaCDROM()
	{
		CdromDAO dao = new CdromDAO();
		//dao.gerarRelatorio();
		
		List lista =dao.gerarListaDeAcoes();
		//List lista =dao.getListaDeSoftwares();
		
		//assertEquals(true, dao.existeSoftwareEmLista(lista, "Saraiva Reader ALPHA_7RC11b165"));
		System.out.println("Tamanho Lista Definitiva :"+lista.size());
		for(int i=0;i<=lista.size()-1;i++)
		{
			Cdrom cdrom = (Cdrom)lista.get(i);
			System.out.println(i+" - "+cdrom.getComando()+" - "+cdrom.getNome()+" Unidade :"+cdrom.getDrive());
		}
		
	}
	
	@Test
	public void retornoListaDefinitivaHotFix()
	{
		HotFixDAO dao = new HotFixDAO();
		//dao.gerarRelatorio();
		
		List lista =dao.gerarListaDeAcoes();
		//List lista =dao.getListaDeSoftwares();
		
		//assertEquals(true, dao.existeSoftwareEmLista(lista, "Saraiva Reader ALPHA_7RC11b165"));
		System.out.println("Tamanho Lista Definitiva :"+lista.size());
		for(int i=0;i<=lista.size()-1;i++)
		{
			HotFixWindows hotfix = (HotFixWindows)lista.get(i);
			System.out.println(i+" - "+hotfix.getComando()+" - "+hotfix.getHotFixID());
		}
		
	}
	
	@Test
	public void retornoListaDefinitivaMemorias()
	{
		MemoriasDAO dao = new MemoriasDAO();
		List lista = dao.gerarListaDeAcoes();
		
		System.out.println("Tamanho Lista Definitiva :"+lista.size());
		for(int i=0;i<=lista.size()-1;i++)
		{
			Memoria _memoria = (Memoria)lista.get(i);
			System.out.println(i+" - "+_memoria.getComando()+" - "+_memoria.getCodigoSlot());
		}
	}
	
	
	@Test
	public void retornoCPU()
	{
			CPUDAO dao = new CPUDAO();
			CPU cpu = new CPU();
			cpu = dao.getComando();
			System.out.println("Processador :"+cpu.getNome()+" - Comando : "+cpu.getComando());
	}
	
	@Test
	public void retornoAcoesPlacaMae()
	{
		PlacaMaeDAO dao = new PlacaMaeDAO();
		dao.gerarRelatorioPlacaMae();
		PlacaMae placa =dao.getComando();
		System.out.println("Codigo :"+placa.getCodigoMaquina()+" - "+placa.getFabricante()+" - Comando : "+placa.getComando());
		
	}
	
	@Test
	public void retornoAcoesSO()
	{
		SODAO dao = new SODAO();
		dao.gerarRelatorioSO();
		SO so =dao.getComando();
		System.out.println("Codigo :"+so.getCodigoMaquina()+" - "+so.getNome()+" - Comando : "+so.getComando());
	}
	
	@Test
	public void retornoSom()
	{
		SomDAO dao = new SomDAO();
		dao.gerarRelatorioSom();
		Som som = dao.getComando();
		System.out.println("Drive SOM - "+som.getNome()+" - Comando :"+som.getComando());
	}
	@Test
	public void retornoDiscos()
	{
		UnidadeArmazenamentoDAO dao  = new UnidadeArmazenamentoDAO();
		//dao.gerarRelatorioDeDiscos();
		List lista = dao.getListaDeAcoes();
		for (int i=0;i<=lista.size()-1;i++)
		{
			UnidadeArmazenamento unidade = new UnidadeArmazenamento();
			unidade = (UnidadeArmazenamento)lista.get(i);
			System.out.println(unidade.getCodigoDrive()+" - "+unidade.getNome()+" - Comando - "+unidade.getComando());
		}
		
	}
	
	@Test
	public void retornoVideo()
	{
		VideoDAO dao = new VideoDAO();
		dao.gerarRelatorioVideo();
		Video video = new Video();
		
		video = dao.getComando();
		System.out.println("Drive Video : "+video.getNome()+" - Comando : "+video.getComando());
	}
	

}
