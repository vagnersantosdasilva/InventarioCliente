package DAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import Entidades.AdaptadorDeRede;
import Seguranca.Identidade;
import Util.Log;
import Util.ManipuladorArquivos;

public class AdaptadoresDeRedeDAO 
{
	private List listaTemporaria =new ArrayList();
	private List listaConsolidada=new ArrayList();
	public void gerarRelatorioAdaptadores()
	{	
		try 
		{
			Process relatorioMemoria = Runtime.getRuntime().exec("wscript scripts/adaptadorDeRede.vbs");
			relatorioMemoria.waitFor();
		} 
		catch (Exception e) 
		{
			System.out.println("Erro ao executar relatório de Memória RAM");
			System.out.println(e.getMessage());
			Log.escrever("[AdaptadoresDeRedeDAO:gerarRelatorioAdaptadores] :"+e.getMessage());
		}
	  }
	@SuppressWarnings("rawtypes")
	public List getListaDeAdaptadores()
	 {
		 return getlistaDeAdaptadores("repositorio/adaptadoresDeRede.ivt");
	 }
		 
	 @SuppressWarnings("rawtypes")
	 public List getlistaDeAdaptadores(String caminho)
	 {
		 	
		    Identidade id =Identidade.getInstance();
		    List listaAuxiliar = new ArrayList();
		 	List listaDeAdaptadores = new ArrayList();
			try {
				 
				 Reader r = new FileReader(caminho);
				 BufferedReader br = new BufferedReader(r);
				 String linha;
				 while((linha=br.readLine())!=null)
				 {
					 if (linha.indexOf("Name#")>=0) listaAuxiliar.add(linha);
					 if (linha.indexOf("Description#")>=0)listaAuxiliar.add(linha);
					 if (linha.indexOf("Index#")>=0) listaAuxiliar.add(linha);
					 if (linha.indexOf("MACAddress#")>=0) listaAuxiliar.add(linha);
					 if (linha.indexOf("Manufacturer#")>=0) listaAuxiliar.add(linha);
					 if (linha.indexOf("Speed#")>=0) listaAuxiliar.add(linha);
					 if (linha.indexOf("Status#")>=0) listaAuxiliar.add(linha);
					 if (linha.indexOf("TimeOfLastReset#")>=0) listaAuxiliar.add(linha);
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
				 int parametro = contadorDeLinhas/8;
				 int elementos=0;
				 
				 for(int i=0 ;i<parametro;i++)
				 {
					//elemento0 =fabricante; elemento1=tamanhopalavra;  elemento2=
					AdaptadorDeRede unidade = new AdaptadorDeRede();
					unidade.setCodigoMaquina(id.getIdentidade());
					unidade.setNome(vetor.split("#")[elementos].trim());
					unidade.setDescricao(vetor.split("#")[elementos+1].trim());
					unidade.setIndice(vetor.split("#")[elementos+2].trim());
					unidade.setMacAdress((vetor.split("#")[elementos+3].trim()));
					unidade.setFabricante(vetor.split("#")[elementos+4].trim());
					unidade.setVelocidade(vetor.split("#")[elementos+5].trim());
					unidade.setStatus(vetor.split("#")[elementos+6].trim());
					unidade.setUltimoReset(vetor.split("#")[elementos+7].trim());
					listaDeAdaptadores.add(unidade);
					elementos=elementos+8;
				 }
				 	
			} catch (Exception e) {
				System.out.println("Erro EM :AdaptadoresDeRedeDAO");
				System.out.println(e.getMessage());
				Log.escrever("[AdaptadoresDeRedeDAO:getListaDeAdaptadores] :"+e.getMessage());
			}
			 return listaDeAdaptadores;
		 }
	 
	 public List gerarListaDeAcoes()
	 {
		
		 List listaDefinitiva = new ArrayList();
		 listaTemporaria = getlistaDeAdaptadores("repositorio/adaptadoresDeRede.ivt");
		 listaConsolidada =getlistaDeAdaptadores("repositorioConsolidado/adaptadoresDeRede.ivt");
		 		
		for (int i=0;i<=listaConsolidada.size()-1;i++) 
		{
			AdaptadorDeRede adapt = new AdaptadorDeRede ();
			adapt=(AdaptadorDeRede)listaConsolidada.get(i);
			if (!(existe(listaTemporaria, adapt)))
			{
				adapt.setComando("excluir");
				listaDefinitiva.add(adapt);
			}
		}
				
		for(int i=0;i<=listaTemporaria.size()-1;i++)
		{
			AdaptadorDeRede adapt = new AdaptadorDeRede ();
			adapt = (AdaptadorDeRede)listaTemporaria.get(i);
			if(existe(listaConsolidada,adapt))
			{
				adapt.setComando("manter");
			}
			else
			{
				adapt.setComando("incluir");
				listaDefinitiva.add(adapt);
			}
		}
		return listaDefinitiva;
	}
	private boolean existe(List lista, AdaptadorDeRede nome) 
	{
		for (int i=0; i<=lista.size()-1;i++)
		{
			AdaptadorDeRede adapt =new AdaptadorDeRede();
			adapt = (AdaptadorDeRede)lista.get(i);
			if (adapt.equals(nome)) return true;
		}
		return false;
	}
	
	public boolean atualizarListaConsolidada()
	{
		if (ManipuladorArquivos.sobrescrever("repositorio/adaptadoresDeRede.ivt", "repositorioConsolidado/adaptadoresDeRede.ivt")) return true;
		return false;
	}
		 
}

