package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import Entidades.SO;
import Seguranca.Identidade;
import Util.Log;
import Util.ManipuladorArquivos;

public class SODAO 
{
	private String caminho="repositorio/so.ivt";
	
	public void gerarRelatorioSO()
	{
		Process inventarioSistema;
		try {
			inventarioSistema = Runtime.getRuntime().exec("Wscript scripts/so.vbs");
			inventarioSistema.waitFor();
		} catch (Exception e) {
			System.out.println("Erro ao tentar executar script so.vbs");
			System.out.println(e.getMessage());
			Log.escrever("[SODAO:gerarRelatorioSO] :"+e.getMessage());
		}
		
	}
	
	public SO getComando()
	{
		SO temp = getRelatorioSO("repositorio/so.ivt");
		SO definitivo = getRelatorioSO("repositorioConsolidado/so.ivt");
		
		if (temp.equals(definitivo))
		{
			temp.setComando("manter");
			return temp;
		}
		else{
			temp.setComando("atualizar");
			return temp;
		}
		
	
	}
	
	public SO getRelatorioSO()
	{
		return getRelatorioSO("repositorio/so.ivt");
	}
	
	public SO getRelatorioSO(String caminho)
	{
		SO so = new SO();
		Identidade id =Identidade.getInstance();
		try{
			Reader r = new FileReader(caminho);
			BufferedReader br = new BufferedReader(r);
			String linha;
			
			while((linha=br.readLine())!=null)
			{
				so.setCodigoMaquina(id.getIdentidade());
				so.setArquitetura(System.getProperty("os.arch"));
				if (linha.indexOf("Nome :")>=0) so.setNome(linha.split(":")[1].trim());  
				if (linha.indexOf("Atualizacao")>=0)so.setAtualizacao(linha.split(":")[1].trim());
				if (linha.indexOf("Versao")>=0)so.setVersao(linha.split(":")[1].trim());
				if(linha.indexOf("HostName")>=0)so.setHostname(linha.split(":")[1].trim());
				if(linha.indexOf("DataInstalacao")>=0)so.setDataInstalacao(linha.split(":")[1].trim());
				if(linha.indexOf("UltimoBoot")>=0)so.setUltimoBoot(linha.split(":")[1].trim());
			}
			br.close();
			r.close();
			return so;
		}catch(Exception e){
			
				System.out.println("Erro ao tentar obter informações de Sistema Operacional: SODAO" );
				System.out.println(e.getMessage());
				Log.escrever("[SODAO:getRelatorioSO] :"+e.getMessage());
		}
		return so; 
	}
	public boolean atualizarListaConsolidada()
	{
		if (ManipuladorArquivos.sobrescrever("repositorio/so.ivt", "repositorioConsolidado/so.ivt")) return true;
		return false;
	}
}
