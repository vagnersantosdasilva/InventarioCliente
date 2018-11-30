package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import Entidades.HotFixWindows;
import Entidades.Memoria;
import Seguranca.Identidade;
import Util.Conversor;
import Util.Log;
import Util.ManipuladorArquivos;

public class MemoriasDAO 
{
	 public void gerarRelatorioMemorias()
	 {
		 try {
			Util.Executar.executarComando("wscript scripts/memorias.vbs");
		} catch (Exception e) {
			System.out.println("{MemoriasDAO[gerarRelatorioMemorias]}");
			System.out.println(e.getMessage());
			Log.escrever("[MeoriasDAO:gerarRelatorioMemorias] :"+e.getMessage());
		} 
	  }
	 public boolean existe(List lista,Memoria memoria)
	 {
		for (int i=0; i<=lista.size()-1;i++)
		{
			Memoria _memoria = new Memoria();
			_memoria = (Memoria)lista.get(i);
			if (_memoria.equals(memoria)) return true;
		}
		return false;
	 }
	 
	 //marcar objetos que serão incluidos e eliminados no servidor
	 public List gerarListaDeAcoes()
	 {
		  List listaConsolidada = getListaDeMemorias("repositorioConsolidado/memorias.ivt");	
		  List listaTemporaria  = getListaDeMemorias("repositorio/memorias.ivt");
		  List listaDefinitiva = new ArrayList();
			
		
			for (int i=0;i<=listaConsolidada.size()-1;i++) 
			{
				Memoria _memoria = new Memoria ();
				_memoria=(Memoria)listaConsolidada.get(i);
				if (!(existe(listaTemporaria, _memoria)))
				{
					_memoria.setComando("excluir");
					listaDefinitiva.add(_memoria);
				}
			}
			for(int i=0;i<=listaTemporaria.size()-1;i++)
			{
				Memoria _memoria = new Memoria ();
				_memoria = (Memoria)listaTemporaria.get(i);
				if(existe(listaConsolidada,_memoria))
				{
					_memoria.setComando("manter");
					//listaDefinitiva.add(_memoria); reduzir o peso da lista 
				}
				else
				{
					_memoria.setComando("incluir");
					listaDefinitiva.add(_memoria);
				}
			}
			return listaDefinitiva;
	}
	 
	 
	public List getListaDeMemorias()
	{
		return getListaDeMemorias("repositorio/memorias.ivt");
	}
	
	@SuppressWarnings("rawtypes")
	public List getListaDeMemorias(String caminho)
	 {
		Identidade id =Identidade.getInstance();
		List listaDeMemorias  = new ArrayList();
		List listaAuxiliar = new ArrayList();
		String codigoMaquina=id.getIdentidade(); 
		try {
			 
			 Reader r = new FileReader(caminho);
			 BufferedReader br = new BufferedReader(r);
			 String linha;
			 while((linha=br.readLine())!=null)
			 {
				 if (linha.indexOf("Fabricante")>=0) listaAuxiliar.add(linha);
				 if (linha.indexOf("Velocidade")>=0) listaAuxiliar.add(linha);
				 if (linha.indexOf("Tipo")>=0) listaAuxiliar.add(linha);
				 if (linha.indexOf("Slot")>=0) listaAuxiliar.add(linha);
				 if (linha.indexOf("Tamanho:")>=0) listaAuxiliar.add(linha);
				 if (linha.indexOf("Status")>=0) listaAuxiliar.add(linha);
			 }
			 
			 br.close();
			 r.close();
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
			 int parametro = contadorDeLinhas/6;
			 int elementos=0;
			 
			 for(int i=0 ;i<parametro;i++)
			 {
				//elemento0 =fabricante; elemento1=tamanhopalavra;  elemento2=
				Memoria mem = new Memoria();
				mem.setCodigoMaquina(codigoMaquina);
				mem.setFabricante(vetor.split(":")[elementos].trim());
				mem.setTipo(vetor.split(":")[elementos+1].trim());
				mem.setCodigoSlot(((vetor.split(":")[elementos+2].trim())));
				mem.setCapacidade(Conversor.byteParaGiga(vetor.split(":")[elementos+3].trim()));
				mem.setVelocidade(vetor.split(":")[elementos+4].trim());
				mem.setStatus(vetor.split(":")[elementos+5].trim());
				listaDeMemorias.add(mem);
				elementos=elementos+6;
			 }
			 return listaDeMemorias; 
		} 
		catch (Exception e) 
		{
			System.out.println("Erro EM :MemoriasDAO :"+" Arquivo lido :"+caminho);
			System.out.println(e.getMessage());
			e.printStackTrace();
			Log.escrever("[MemoriasDAO:getListaDeMemorias] :"+e.getMessage());
		 }
		 return listaDeMemorias;
	 }
	public boolean atualizarListaConsolidada()
	{
		if (ManipuladorArquivos.sobrescrever("repositorio/memorias.ivt", "repositorioConsolidado/memorias.ivt")) return true;
		return false;
	}
	
}
