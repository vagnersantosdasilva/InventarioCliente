package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import Entidades.Video;
import Seguranca.Identidade;
import Util.Log;
import Util.ManipuladorArquivos;

public class VideoDAO {
	
	public void gerarRelatorioVideo()
	{
		Process inventarioSistema;
		try {
			inventarioSistema = Runtime.getRuntime().exec("Wscript scripts/video.vbs");
			inventarioSistema.waitFor();
		} catch (Exception e) {
			System.out.println("Erro ao tentar executar script Video.vbs");
			System.out.println(e.getMessage());
			Log.escrever("[VideoDAO:gerarRelatorioVideo] :"+e.getMessage());
		}
		
	}
	public Video obterInventario()
	{
		return obterInventario("repositorio/video.ivt");
	}
	
	public Video getComando()
	{
		Video temp = obterInventario("repositorio/video.ivt");
		Video definitivo =obterInventario("repositorioConsolidado/video.ivt");
		
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
	
	public Video obterInventario(String caminho)
	{
		Video video =new Video();
		Identidade id =Identidade.getInstance();
		String codigoMaquina = id.getIdentidade();
		try{
			video.setCodigoMaquina(codigoMaquina);
			
			Reader r = new FileReader(caminho);
			BufferedReader br = new BufferedReader(r);
			String linha;
			
			while((linha=br.readLine())!=null)
			{
				if(linha.indexOf("Caption")>=0) video.setNome(linha.split(":")[1].trim());
				if(linha.indexOf("AdapterRAM")>=0)video.setAdapterRAM(linha.split(":")[1].trim());
				if(linha.indexOf("CurrentHorizontalResolution")>=0)video.setCurrentHorizontalResolution(linha.split(":")[1].trim());
				if(linha.indexOf("CurrentVerticalResolution")>=0)video.setCurrentVerticalResolution(linha.split(":")[1].trim());
				if(linha.indexOf("AdapterDACType")>=0)video.setAdapterDACType(linha.split(":")[1].trim());
				if(linha.indexOf("DriverDate")>=0)video.setDriverDate(linha.split(":")[1].trim());
				if(linha.indexOf("DriverVersion")>=0)video.setDriverVersion(linha.split(":")[1].trim());
				if(linha.indexOf("InstalledDisplayDriver")>=0)video.setInstalledDisplayDrivers(linha.split(":")[1].trim());
				if(linha.indexOf("Status")>=0)video.setStatus(linha.split(":")[1].trim());
				if(linha.indexOf("CurrentNumberOfColors")>=0)video.setCurrentNumberOfColors(linha.split(":")[1].trim());
			}
			br.close();
			r.close();
			return video;
		}catch(Exception e){
			
				System.out.println("Erro ao tentar obter informações de Sistema Operacional: SODAO" );
				System.out.println(e.getMessage());
				Log.escrever("[VideoDAO:obterInventario] :"+e.getMessage());
		}
		return video;
	}
	public boolean atualizarListaConsolidada()
	{
		if (ManipuladorArquivos.sobrescrever("repositorio/video.ivt", "repositorioConsolidado/video.ivt")) return true;
		return false;
	}

}
