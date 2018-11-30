package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;
import Entidades.Software;
import Seguranca.Identidade;
import Util.Log;
import Util.ManipuladorArquivos;
import Util.PropriedadesDeCaminho;

public class SoftwareDAO 
{
	private Software software;
	//private List listaDeSoftwares = new ArrayList();
	private List listaTemporaria = new ArrayList();
	private List listaConsolidada= new ArrayList();
	
	private int quantidadeSoftware;
	Identidade id =Identidade.getInstance();
	
	public void gerarRelatorio()
	{
		
		try {
			Util.Executar.executarComando("wscript scripts/listaProto32.vbs");
			Util.Executar.executarComando("wscript scripts/listaProto64.vbs");
			Util.Executar.executarComando("scripts/progPerfil32.bat");
		} catch (Exception e) {
			System.out.println("Erro {SoftwareDAO [gerarRelatorio] }");
			System.out.println(e.getMessage());
			Log.escrever("[SoftwareDAO:gerarRelatorio] :"+e.getMessage());
		}
	}
	
	public boolean existeSoftwareEmLista(List lista,Software nome)
	{
		for (int i=0; i<=lista.size()-1;i++)
		{
			Software software =new Software();
			software = (Software)lista.get(i);
			if (software.equals(nome)) return true;
		}
		return false;
	}
	/** marcar registros a serem mantidos , excluidos ou incluidos para lançamento no banco **/
	@SuppressWarnings("unchecked")
	public List gerarListaDeAcoes()
	{
		@SuppressWarnings("rawtypes")
		List listaDefinitiva = new ArrayList();
		 listaTemporaria = getListaDeSoftwares("repositorio/programas.ivt");
		 listaConsolidada= getListaDeSoftwares("repositorioConsolidado/programas.ivt");
		for (int i=0;i<=listaConsolidada.size()-1;i++) 
		{
			Software soft = new Software ();
			soft=(Software)listaConsolidada.get(i);
			if (!(existeSoftwareEmLista(listaTemporaria, soft)))
			{
				soft.setComando("excluir");
				listaDefinitiva.add(soft);
			}
		}
		
		for(int i=0;i<=listaTemporaria.size()-1;i++)
		{
			Software soft = new Software();
			soft = (Software)listaTemporaria.get(i);
			if(existeSoftwareEmLista(listaConsolidada,soft))
			{
				soft.setComando("manter");
				//listaDefinitiva.add(soft); reduzir o peso da lista que será enviadao ao servidor
			}
			else
			{
				soft.setComando("incluir");
				listaDefinitiva.add(soft);
			}
		}
		return listaDefinitiva;
	}
	
	public int getQuantidadeSoftware()
	{
		return quantidadeSoftware;
	}
	public List getListaDeSoftwares()
	{
		return getListaDeSoftwares("repositorio/programas.ivt");
	}
	
	@SuppressWarnings("unchecked")
	public List getListaDeSoftwares(String caminho)
	{
		List listaDeSoftwares = new ArrayList();
		try 
		{
			 Reader r = new FileReader(caminho);
			 BufferedReader br = new BufferedReader(r);
			 String linha;
			 listaDeSoftwares = new ArrayList();
			 int codigo=0;
			 String codigoMaquina =id.getIdentidade();
			 while((linha=br.readLine())!=null)
			 {
				 codigo++;
				 software = new Software();
				 software.setCodigoMaquina(codigoMaquina);
				 software.setCodigoSoftware(codigo);
				 software.setNome(linha.split(";")[0].trim());
				 software.setArquitetura(linha.split(";")[1].trim());
				 //software.setDataInstalacao(linha.split(";")[2].trim());
				 if (linha.split(";")[2].equals(" ")) software.setDataInstalacao(PropriedadesDeCaminho.getDataModificacao(linha.split(";")[3].trim()));
				 else software.setDataInstalacao(linha.split(";")[2].trim());
				 software.setInstallLocation((linha.split(";")[3].trim()));
				 software.setUninstallString((linha.split(";")[4].trim()));
				 listaDeSoftwares.add(software);
				 quantidadeSoftware++;
			 }
			 br.close();
			 r.close();
			 
			return listaDeSoftwares;
		}
		catch(Exception e)
		{
			System.out.println("Erro : { SoftwareDAO[getListaDeSoftware]["+caminho+"] "+e.getMessage()+ " }");
			e.printStackTrace();
			Log.escrever("[SoftwareDAO:getListaDeSoftwares] :"+e.getMessage());
			
		}
		return listaDeSoftwares;
	}
	
	public boolean atualizarListaConsolidada()
	{
		if (ManipuladorArquivos.sobrescrever("repositorio/programas.ivt", "repositorioConsolidado/programas.ivt")) return true;
		return false;
	}

	@SuppressWarnings("unused")
	private String converteBarraParaBarraInvertida(String caminho) {
		return caminho.replaceAll("\\", "/");
	}
}
