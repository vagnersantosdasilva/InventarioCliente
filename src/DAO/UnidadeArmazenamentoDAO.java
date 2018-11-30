package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import Entidades.UnidadeArmazenamento;
import Entidades.UnidadeArmazenamento;
import Seguranca.Identidade;
import Util.Log;
import Util.ManipuladorArquivos;

public class UnidadeArmazenamentoDAO 
{
	
	
	 public void gerarRelatorioDeDiscos()
	 {	
			 try 
			 {
				Process relatorioMemoria = Runtime.getRuntime().exec("wscript scripts/unidadesArmazenamento.vbs");
				relatorioMemoria.waitFor();
			 } catch (Exception e) {
				System.out.println("Erro ao executar relatório de Memória RAM");
				System.out.println(e.getMessage());
				Log.escrever("[UnidadeArmazenamentoDAO:gerarRelatorioDediscos] :"+e.getMessage());
			 }
	  }
	 
	 private boolean existe(List lista, UnidadeArmazenamento unidade)
	 {
		 for (int i=0; i<=lista.size()-1;i++)
		 {
			UnidadeArmazenamento _unidade = new UnidadeArmazenamento();
			_unidade = (UnidadeArmazenamento)lista.get(i);
			if (_unidade.equals(unidade)) return true;
		 }
		return false;
	 }
	 public List getListaDeAcoes()
	 {
			List listaTemporaria  = getListaDeDiscos("repositorio/unidadeArmazenamento.ivt");
			List listaConsolidada = getListaDeDiscos("repositorioConsolidado/unidadeArmazenamento.ivt");
			List listaDefinitiva = new ArrayList();
			
			for (int i=0;i<=listaConsolidada.size()-1;i++) 
			{
				UnidadeArmazenamento unidade = new UnidadeArmazenamento();
				unidade=(UnidadeArmazenamento)listaConsolidada.get(i);
				if (!(existe(listaTemporaria, unidade)))
				{
					unidade.setComando("excluir");
					listaDefinitiva.add(unidade);
				}
			}
			for(int i=0;i<=listaTemporaria.size()-1;i++)
			{
				UnidadeArmazenamento unidade = new UnidadeArmazenamento();
				unidade = (UnidadeArmazenamento)listaTemporaria.get(i);
				if(existe(listaConsolidada,unidade))
				{
					unidade.setComando("manter");
					//listaDefinitiva.add(unidade); redizir o peso da lista
				}
				else
				{
					unidade.setComando("incluir");
					listaDefinitiva.add(unidade);
				}
			}
			return listaDefinitiva;
		}
	 
	 public List getListaDeDiscos()
	 {
		 return getListaDeDiscos("repositorio/unidadeArmazenamento.ivt");
	 }
		 
	 public List getListaDeDiscos(String caminho)
	 {
		 	List listaDeDiscos = new ArrayList();
			List listaAuxiliar = new ArrayList();
			Identidade id =Identidade.getInstance();
			String codigoMaquina = id.getIdentidade();
			try {
				 
				 Reader r = new FileReader(caminho);
				 BufferedReader br = new BufferedReader(r);
				 String linha;
				 while((linha=br.readLine())!=null)
				 {
					 if (linha.indexOf("Nome :")>=0) listaAuxiliar.add(linha);
					 if (linha.indexOf("Tamanho :")>=0) listaAuxiliar.add(linha);
					 if (linha.indexOf("TipoDeMedia :")>=0) listaAuxiliar.add(linha);
					 if (linha.indexOf("TipoDeInterface :")>=0) listaAuxiliar.add(linha);
					 if (linha.indexOf("Status :")>=0) listaAuxiliar.add(linha);
					 
				 }
				 
				 br.close();
				
				 StringBuilder sb = new StringBuilder();
				 int contadorDeLinhas=0;
				 for(int i=0;i<listaAuxiliar.size();i++)
				 {
					String linhas=(String)listaAuxiliar.get(i);
					if (linhas.split(":")[1]==null) { 
						sb.append(": ");
						contadorDeLinhas++;
					}
					else
					{
						sb.append(linhas.split(":")[1]+":");
						contadorDeLinhas++;
					}
					
				 }
				 
				 String vetor =sb.toString();
				 int parametro = contadorDeLinhas/5;
				 int elementos=0;
				 
				 for(int i=0 ;i<parametro;i++)
				 {
					//elemento0 =fabricante; elemento1=tamanhopalavra;  elemento2=
					UnidadeArmazenamento unidade = new UnidadeArmazenamento();
					unidade.setCodigoMaquina(codigoMaquina);
					unidade.setNome(vetor.split(":")[elementos].trim());
					//System.out.println("Tamanho recebido :"+(vetor.split(":")[elementos+1].trim()));
					//System.out.println("Tamanho Convertido "+Util.Conversor.byteParaGiga(vetor.split(":")[elementos+1].trim()).replaceAll(" ",""));
					unidade.setTamanho(vetor.split(":")[elementos+1].trim());
					unidade.setTipoDeMidea(vetor.split(":")[elementos+2].trim());
					unidade.setTipoDeInterface(vetor.split(":")[elementos+3].trim());
					unidade.setStatus(vetor.split(":")[elementos+4].trim());
					unidade.setCodigoDrive(i+1);
					listaDeDiscos.add(unidade);
					elementos=elementos+5;
				 }
				 return listaDeDiscos;
				 	
			} catch (Exception e) {
				System.out.println("Erro EM :UnidadeArmazenamentoDAO");
				System.out.println(e.getMessage());
				e.printStackTrace();
				Log.escrever("[UnidadeArmazenamentoDAO:getListaDeDiscos] :"+e.getMessage());
			}
			 return listaDeDiscos;
		 }
	 
	 public boolean atualizarListaConsolidada()
	 {
			if (ManipuladorArquivos.sobrescrever("repositorio/unidadeArmazenamento.ivt", "repositorioConsolidado/unidadeArmazenamento.ivt")) return true;
			return false;
	 }
}
