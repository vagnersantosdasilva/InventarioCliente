package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import Entidades.Som;
import Entidades.Video;
import Seguranca.Identidade;
import Util.Log;
import Util.ManipuladorArquivos;

public class SomDAO 
{

	
	public void gerarRelatorioSom()
	{
		Process inventarioSistema;
		try {
			inventarioSistema = Runtime.getRuntime().exec("Wscript scripts/som.vbs");
			inventarioSistema.waitFor();
		} catch (Exception e) {
			System.out.println("Erro ao tentar executar script Som.vbs");
			System.out.println(e.getMessage());
			Log.escrever("[SomDAO:gerarRelatorioSom] :"+e.getMessage());
		}
		
	}
	public Som getComando()
	{
		Som temp = obterInventarioSom("repositorio/som.ivt");
		Som definitivo =obterInventarioSom("repositorioConsolidado/som.ivt");
		
		if (temp.equals(definitivo))
		{
			temp.setComando("manter");
			return temp;
		}
		else
		{
			temp.setComando("atualizar");
			return temp;
		}
		
		
	}
	
	public Som obterInventarioSom()
	{
		return obterInventarioSom("repositorio/som.ivt");
	}
	public Som obterInventarioSom(String caminho)
	{
		Identidade id =Identidade.getInstance();
		Som som =new Som();
		String codigoMaquina=id.getIdentidade();
		try{
			Reader r = new FileReader(caminho);
			BufferedReader br = new BufferedReader(r);
			String linha;
			som.setCodigoMaquina(codigoMaquina);
			while((linha=br.readLine())!=null)
			{
				if(linha.indexOf("Caption")>=0) som.setNome(linha.split(":")[1].trim());
				if (linha.indexOf("Manufacture")>=0)som.setFabricante(linha.split(":")[1].trim());
				if (linha.indexOf("InstallDate")>=0)som.setDatainstalacao(linha.split(":")[1].trim());
				if(linha.indexOf("Status:")>=0)som.setStatus(linha.split(":")[1].trim());
			}
			br.close();
			return som;
		}catch(Exception e){
			
				System.out.println("Erro ao tentar obter informações de Sistema Operacional: SODAO" );
				System.out.println(e.getMessage());
				Log.escrever("[SomDAO:obterInventarioSom] :"+e.getMessage());
		}
		return som;
	}
	public boolean atualizarListaConsolidada()
	{
		if (ManipuladorArquivos.sobrescrever("repositorio/som.ivt", "repositorioConsolidado/som.ivt")) return true;
		return false;
	}

}
