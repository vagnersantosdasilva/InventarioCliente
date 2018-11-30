package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import Entidades.CPU;
import Util.Log;
import Util.ManipuladorArquivos;

public class ProcessadoresDAO {
	
	private CPU processador;
	private List listaProcessadores = new ArrayList();
	private List  listaAuxiliar = new ArrayList();
	
	public void gerarRelatorioProcessadores()
	{
		Process inventarioProcessador;
		try {
			inventarioProcessador = Runtime.getRuntime().exec("Wscript scripts/processadores.vbs");
			inventarioProcessador.waitFor();
		} catch (Exception e) {
			System.out.println("Erro ao tentar executar script processadores.vbs");
			System.out.println(e.getMessage());
			Log.escrever("[ProcessadoresDAO:getRelatorioProcessadores] :"+e.getMessage());
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public List getListaProcessadores()
	{
		try {
			 
			 Reader r = new FileReader("repositorio/processadores.ivt");
			 BufferedReader br = new BufferedReader(r);
			 String linha;
			 while((linha=br.readLine())!=null)
			 {
				 if (linha.indexOf("Nome")>=0) listaAuxiliar.add(linha);
				 if (linha.indexOf("Arquitetura")>=0) listaAuxiliar.add(linha);
				 if (linha.indexOf("ClockMaximo")>=0) listaAuxiliar.add(linha);
				 if (linha.indexOf("Nucleos")>=0) listaAuxiliar.add(linha);
				 if (linha.indexOf("NucleosLogicos:")>=0) listaAuxiliar.add(linha);
				 if (linha.indexOf("Fabricante")>=0) listaAuxiliar.add(linha);
				 if (linha.indexOf("Status")>=0) listaAuxiliar.add(linha);
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
			 int parametro = contadorDeLinhas/7;
			 int elementos=0;
			 
			 for(int i=0 ;i<parametro;i++)
			 {
				//elemento0 =nome; elemento1=arquitetura;  elemento2=frequenciaMaxima ...
				 
				CPU processador = new CPU();
				
				processador.setNome(vetor.split(":")[elementos].trim());
				processador.setArquitetura(vetor.split(":")[elementos+1].trim());
				processador.setFrequenciaMaxima(vetor.split(":")[elementos+2].trim());
				processador.setNumeroNucleos(vetor.split(":")[elementos+3].trim());
				processador.setNumeroProcessadoresLogicos(vetor.split(":")[elementos+4].trim());
				processador.setFabricante(vetor.split(":")[elementos+5].trim());
				processador.setStatus(vetor.split(":")[elementos+6].trim());
				listaProcessadores.add(processador);
				elementos=elementos+7;
			 }
			 	
		} 
		catch (Exception e) {
			System.out.println("Erro EM :ProcessadoresDAO");
			System.out.println(e.getMessage());
			Log.escrever("[ProcessadoresDAO:getListaProcessadores] :"+e.getMessage());
		} 
		
		
		 return listaProcessadores;
	 }
	
	public boolean atualizarListaConsolidada()
	{
		if (ManipuladorArquivos.sobrescrever("repositorio/processadores.ivt", "repositorioConsolidado/processadores.ivt")) return true;
		return false;
	}
	

}
