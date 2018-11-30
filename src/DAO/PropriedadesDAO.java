package DAO;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import Entidades.Propriedades;

public class PropriedadesDAO 
{
	
	Propriedades propriedades = new Propriedades();
	
	public Propriedades getPropriedades()
	{
		propriedades.setServidor(Util.Propriedades.lePropriedades("propriedades/propriedades_sistema.cfg","servidor"));
		propriedades.setPorta(Util.Propriedades.lePropriedades("propriedades/propriedades_sistema.cfg","porta"));
		propriedades.setProgramFiles(Util.Propriedades.lePropriedades("propriedades/variaveis_ambiente.cfg", "programFiles"));
		propriedades.setProgramFilesx86(Util.Propriedades.lePropriedades("propriedades/variaveis_ambiente.cfg", "programFilesx86"));
		propriedades.setWinDir(Util.Propriedades.lePropriedades("propriedades/variaveis_ambiente.cfg", "winDir"));
		propriedades.setWinUpdate(Util.Propriedades.lePropriedades("propriedades/variaveis_ambiente.cfg", "winUpdate"));
		propriedades.setSetupAct(Util.Propriedades.lePropriedades("propriedades/variaveis_ambiente.cfg", "setupAct"));
		propriedades.setTempoDeAtualizacao(Util.Propriedades.lePropriedades("propriedades/propriedades_sistema.cfg", "tempoRefresh"));
		return propriedades;
	}
	
	public void gravarVariaveisDoSistema() 
	{
		String programFiles = converterBarraInvertidaParaBarra(System.getenv("ProgramFiles"));
		String windir = converterBarraInvertidaParaBarra(System.getenv("WinDir"));
		String programFilesx86 = converterBarraInvertidaParaBarra(System.getenv("ProgramFiles(x86)"));
		String winUpdate = converterBarraInvertidaParaBarra(windir+"/WindowsUpdate.log");
		String setupAct=converterBarraInvertidaParaBarra(windir+"/setupact.log");
		
		List lista  = new ArrayList();
		
		lista.add("programFiles="+programFiles);
		lista.add("programFilesx86="+programFilesx86);
		lista.add("windir="+windir);
		lista.add("winUpdate="+winUpdate);
		lista.add("setupAct="+setupAct);
		
		Util.ManipuladorArquivos.salvarListaEmArquivos(lista, "propriedades/variaveis_ambiente.cfg");
		
	}
	
	public void gravarDatasDeUltimaAtualizacao( )
	{
		String programFiles = converterBarraInvertidaParaBarra(System.getenv("ProgramFiles"));
		String windir = converterBarraInvertidaParaBarra(System.getenv("WinDir"));
		String programFilesx86 = converterBarraInvertidaParaBarra(System.getenv("ProgramFiles(x86)"));
		
		String dataProgramFiles = Util.PropriedadesDeCaminho.getDataModificacao(programFiles);
		String dataProgramFilesx86 = Util.PropriedadesDeCaminho.getDataModificacao(programFilesx86);
		String dataWinUpdate = Util.PropriedadesDeCaminho.getDataModificacao(windir+"/WindowsUpdate.log");
		String dataSetupAct = Util.PropriedadesDeCaminho.getDataModificacao(windir+"/Setupact.log");
		
		List lista = new ArrayList();
		lista.add("programFiles="+dataProgramFiles);
		lista.add("programFilesx86="+dataProgramFilesx86);
		lista.add("winUpdate="+dataWinUpdate);
		lista.add("setupAct="+dataSetupAct);
		Util.ManipuladorArquivos.salvarListaEmArquivos(lista, "propriedades/monitor_mudancas.cfg");
		
	}
	
	public boolean modificado(String propriedade)
	{ 
		
		String caminho = Util.Propriedades.lePropriedades("propriedades/variaveis_ambiente.cfg",propriedade);
		String antes = Util.Propriedades.lePropriedades("propriedades/monitor_mudancas.cfg",propriedade);
		String agora = Util.PropriedadesDeCaminho.getDataModificacao(caminho);
		//System.out.println(propriedade+" - Antes  - "+antes);
		//System.out.println(propriedade+" - Depois - "+antes);
		System.out.println(propriedade +" - Atual -"+agora);
		System.out.println(propriedade +" - Antes -"+agora);
		if (agora.equals(antes)) return false;
		return true;
	}
	
	private String converterBarraInvertidaParaBarra(String caminho)
	{
		if (caminho.indexOf("\\")>=0) return caminho.replace("\\","/");
		return caminho;
	}
	

}
