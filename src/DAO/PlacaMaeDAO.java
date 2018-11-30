package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import Entidades.PlacaMae;
import Entidades.PlacaMae;
import Seguranca.Identidade;
import Util.Log;
import Util.ManipuladorArquivos;

public class PlacaMaeDAO 
{
	public void gerarRelatorioPlacaMae()
	{
		try 
		{
			Process processo = Runtime.getRuntime().exec("wscript scripts/placa.vbs");
			processo.waitFor();
		} catch (Exception e) {
			System.out.println("Erro ao tetar executar script - PlacaMae.vbs: PlacaMaeDAO");
			System.out.println(e.getMessage());
			Log.escrever("[PlacaMaeDAO:gerarRelatorioPlacaMae] :"+e.getMessage());
		}
	}
	
	public PlacaMae getComando() 
	{
		PlacaMae temp = new PlacaMae();
		PlacaMae definitivo = new PlacaMae();
		temp = getPlacaMae("repositorio/PlacaMae.ivt");
		definitivo = getPlacaMae("repositorioConsolidado/PlacaMae.ivt");
		
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
	public PlacaMae getPlacaMae()
	{
		return getPlacaMae("repositorio/placaMae.ivt");
	}
	
	public PlacaMae getPlacaMae(String caminho)
	{
		Identidade id =Identidade.getInstance();
		PlacaMae placa = new PlacaMae();
		try{
			Reader r = new FileReader(caminho);
			BufferedReader br = new BufferedReader(r);
			String linha;
			placa.setCodigoMaquina(id.getIdentidade());
			while((linha=br.readLine())!=null)
			{
				if (linha.indexOf("Manufacturer")>=0) placa.setFabricante(linha.split(":")[1].trim()); 
				if (linha.indexOf("Product")>=0) placa.setModelo(linha.split(":")[1].trim());
				if (linha.indexOf("Serial")>=0) placa.setSerial(linha.split(":")[1].trim());
				if (linha.indexOf("Status")>=0) placa.setStatus(linha.split(":")[1].trim());
			}
			br.close();
			r.close();
			return placa;
		}
		catch(Exception e)
		{
			System.out.println("Erro ao tentar obter informações de PlacaMae: PlacaMaeDAO" );
			System.out.println(e.getMessage());
			Log.escrever("[PlacaMaeDAO:getPlacaMae] :"+e.getMessage());
					
		}
		return placa; 
	}
	public boolean atualizarListaConsolidada()
	{
		if (ManipuladorArquivos.sobrescrever("repositorio/placaMae.ivt", "repositorioConsolidado/placaMae.ivt")) return true;
		return false;
	}

}
