package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import Entidades.AdaptadorDeRede;
import Entidades.Cdrom;
import Entidades.Software;
import Entidades.UnidadeArmazenamento;
import Seguranca.Identidade;
import Util.Log;
import Util.ManipuladorArquivos;

public class CdromDAO 
{
		 private  List listaTemporaria=new ArrayList();
		 private List listaConsolidada= new ArrayList();
		 public void gerarRelatorioDeDiscos()
		 {	
			try 
			{
				Process relatorioMemoria = Runtime.getRuntime().exec("wscript scripts/driveCDROM.vbs");
				relatorioMemoria.waitFor();
			} 
			catch (Exception e) 
			{
				System.out.println("Erro ao executar relatório CDROM");
				System.out.println(e.getMessage());
				Log.escrever("[CdromDAO:gerarRelatorioDeDiscos] :"+e.getMessage());
			 }
		  }
		 private boolean existe(List lista,Cdrom cd)
		 {
			 for (int i=0; i<=lista.size()-1;i++)
			 {
				Cdrom cdrom =new Cdrom();
				cdrom = (Cdrom)lista.get(i);
				if (cd.equals(cdrom)) return true;
			 }
			return false;
		 }
		 
		 public List gerarListaDeAcoes()
		 {
			 List listaTemporaria = getListaDeCDs("repositorio/driveCDROM.ivt");
			 List listaConsolidada= getListaDeCDs("repositorioConsolidado/driveCDROM.ivt");
			 List listaDefinitiva = new ArrayList();
			 	
			for (int i=0;i<=listaConsolidada.size()-1;i++) 
			{
				Cdrom cd = new Cdrom ();
				cd=(Cdrom)listaConsolidada.get(i);
				if (!(existe(listaTemporaria, cd)))
				{
					cd.setComando("excluir");
					listaDefinitiva.add(cd);
				}
			}
			
			for(int i=0;i<=listaTemporaria.size()-1;i++)
			{
				Cdrom cd = new Cdrom();
				cd = (Cdrom)listaTemporaria.get(i);
				if(existe(listaConsolidada,cd))
				{
					cd.setComando("manter");
					listaDefinitiva.add(cd);
				}
				else
				{
					cd.setComando("incluir");
					listaDefinitiva.add(cd);
				}
			}
			return listaDefinitiva;
		}
		 
		public List getListaDeCDs()
		{
		 return getListaDeCDs("repositorio/driveCDROM.ivt");
		}
		 
		 public List getListaDeCDs(String caminho)
		 {
			 	List listaDeCDs = new ArrayList();
				List listaAuxiliar = new ArrayList();
				Identidade id =Identidade.getInstance();
				try {
					 
					 Reader r = new FileReader(caminho);
					 BufferedReader br = new BufferedReader(r);
					 String linha;
					 while((linha=br.readLine())!=null)
					 {
						 if (linha.indexOf("Caption#")>=0) listaAuxiliar.add(linha);
						 if (linha.indexOf("Manufacturer#")>=0) listaAuxiliar.add(linha);
						 if (linha.indexOf("MediaType#")>=0) listaAuxiliar.add(linha);
						 if (linha.indexOf("Drive#")>=0) listaAuxiliar.add(linha);
						 if(linha.indexOf("InstallDate#")>=0)listaAuxiliar.add(linha);
						 if (linha.indexOf("Status#")>=0) listaAuxiliar.add(linha);
						
					 }
					 
					 br.close();
					 StringBuilder sb = new StringBuilder();
					 int contadorDeLinhas=0;
					 for(int i=0;i<listaAuxiliar.size();i++)
					 {
						String linhas=(String)listaAuxiliar.get(i);
						if (linhas.split("#")[1]==null) { 
							sb.append("# ");
							contadorDeLinhas++;
						}
						else
						{
							sb.append(linhas.split("#")[1]+"#");
							contadorDeLinhas++;
						}
						
					 }
					 
					 String vetor =sb.toString();
					 int parametro = contadorDeLinhas/6;
					 int elementos=0;
					 
					 for(int i=0 ;i<parametro;i++)
					 {
						//elemento0 =fabricante; elemento1=tamanhopalavra;  elemento2=
						Cdrom unidade = new Cdrom();
						unidade.setCodigoMaquina(id.getIdentidade());
						unidade.setNome(vetor.split("#")[elementos].trim());
						unidade.setFabricante((vetor.split("#")[elementos+1].trim()));
						unidade.setTipoDeMidea(vetor.split("#")[elementos+2].trim());
						unidade.setDrive(vetor.split("#")[elementos+3].trim());
						unidade.setDataInstalacao((vetor.split("#")[elementos+4].trim()));
						unidade.setStatus(vetor.split("#")[elementos+5].trim());
						listaDeCDs.add(unidade);
						elementos=elementos+6;
					 }
					 	
				} catch (Exception e) {
					System.out.println("Erro EM :CdromDAO");
					System.out.println(e.getMessage());
					Log.escrever("[CdromDAO:getListaDeCDs] :"+e.getMessage());
				}
				
				 return listaDeCDs;
			 }
		 
		 public boolean atualizarListaConsolidada()
		 {
			if (ManipuladorArquivos.sobrescrever("repositorio/driveCDROM.ivt", "repositorioConsolidado/driveCDROM.ivt")) return true;
			return false;
		 }
}
