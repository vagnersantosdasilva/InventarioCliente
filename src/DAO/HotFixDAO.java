package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import Entidades.AdaptadorDeRede;
import Entidades.HotFixWindows;
import Entidades.Software;
import Seguranca.Identidade;
import Util.Log;
import Util.ManipuladorArquivos;

public class HotFixDAO 
{
	private List listaDeHotFixs ;
	public void gerarRelatorio()
	{
		try 
		{
			Util.Executar.executarComando("wscript scripts/hotfix.vbs");
		}
		catch (Exception e) 
		{
			System.out.println("Erro {HotFixDAO [gerarRelatorio] }");
			System.out.println(e.getMessage());
			Log.escrever("[HotFixDAO:gerarRelatorio] :"+e.getMessage());
		}
	}
	public boolean existe(List lista,HotFixWindows hotfix)
	{
		for (int i=0; i<=lista.size()-1;i++)
		{
			HotFixWindows hotFixWindows = new HotFixWindows();
			hotFixWindows = (HotFixWindows)lista.get(i);
			if (hotFixWindows.equals(hotfix)) return true;
		}
		return false;
	}
	public List gerarListaDeAcoes()
	{
		List listaTemporaria = getListaDeHotFixs("repositorio/hotfix.ivt");
		List listaConsolidada = getListaDeHotFixs("repositorioConsolidado/hotfix.ivt");
		List listaDefinitiva = new ArrayList();
		
		for (int i=0;i<=listaConsolidada.size()-1;i++) 
		{
			HotFixWindows hotfix = new HotFixWindows ();
			hotfix=(HotFixWindows)listaConsolidada.get(i);
			if (!(existe(listaTemporaria, hotfix)))
			{
				hotfix.setComando("excluir");
				listaDefinitiva.add(hotfix);
			}
		}
		for(int i=0;i<=listaTemporaria.size()-1;i++)
		{
			HotFixWindows hotfix = new HotFixWindows ();
			hotfix = (HotFixWindows)listaTemporaria.get(i);
			if(existe(listaConsolidada,hotfix))
			{
				hotfix.setComando("manter");
				//listaDefinitiva.add(hotfix);   reduzir o peso da lista
			}
			else
			{
				hotfix.setComando("incluir");
				listaDefinitiva.add(hotfix);
			}
		}
		return listaDefinitiva;
	}
			
	
	//'Caption/CSName/Description/FixComments/HotFixID/InstallDate/InstalledBy/InstalledOn/Name/ServicePackInEffect/Status
	public List getListaDeHotFixs()
	{
		return getListaDeHotFixs("repositorio/hotfix.ivt");
	}
	public List getListaDeHotFixs(String caminho)
	{
		try
		{ 
			 Identidade id =Identidade.getInstance();
			 Reader r = new FileReader(caminho);
			 BufferedReader br = new BufferedReader(r);
			 String linha;
			 listaDeHotFixs= new ArrayList(); 
			 String codigoMaquina =id.getIdentidade();
			 while((linha=br.readLine())!=null)
			 {
			
				 HotFixWindows hotfix = new HotFixWindows();
				 
				 hotfix.setCaption(linha.split("#")[0].trim());
				 hotfix.setCodigoMaquina(codigoMaquina);
				 hotfix.setCsName(linha.split("#")[1].trim());
				 hotfix.setDescription(linha.split("#")[2].trim());
				 hotfix.setFixComments(linha.split("#")[3].trim());
				 hotfix.setHotFixID(linha.split("#")[4].trim());
				 hotfix.setInstallDate(linha.split("#")[5].trim());
				 hotfix.setInstalledBy(linha.split("#")[6].trim());
				 hotfix.setInstalledOn(linha.split("#")[7].trim());
				 hotfix.setName(linha.split("#")[8].trim());
				 hotfix.setServicePackInEffect(linha.split("#")[9].trim());
				 hotfix.setStatus(linha.split("#")[10].trim()); 
				 listaDeHotFixs.add(hotfix);
				 
				 
			 }
			 br.close();
			 r.close();
		return listaDeHotFixs;
	}
	catch(Exception e)
	{
		System.out.println("Erro : { HotFixDAO[getListaDeHotFixs] }");
		System.out.println(e.getMessage());
		e.printStackTrace();
		Log.escrever("[HotFixDAO:getListaDeHotFixs] :"+e.getMessage());
		
	}
		return listaDeHotFixs;
	}
	
	public boolean atualizarListaConsolidada()
	{
		if (ManipuladorArquivos.sobrescrever("repositorio/hotfix.ivt", "repositorioConsolidado/hotfix.ivt")) return true;
		return false;
	}
}
