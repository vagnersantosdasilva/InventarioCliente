package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


public class CoparadorDeArquivos 
{
	public static boolean equals(String caminho1,String caminho2)
	{
		List listaDeLinhas1 = converterParaLista(caminho1);
		List listaDeLinhas2 = converterParaLista(caminho2);
		
		if (listaDeLinhas1.size()!=listaDeLinhas2.size()) return false;
		for(int cont=0;cont<=listaDeLinhas1.size()-1;cont++)
		{
				if (!(listaDeLinhas1.get(cont).equals(listaDeLinhas2.get(cont)))) return false; 
		}
		return true;		
		
	}
	
	
	public static List converterParaLista(String arquivo)
	{
		List listaDeLinhas = new ArrayList();
		try
		{
				Reader r = new FileReader(arquivo);
				BufferedReader br = new BufferedReader(r);
				String linha;
				
				
				while((linha=br.readLine())!=null)
				{
					listaDeLinhas.add(linha);
					
					
				}
				br.close();
				System.out.println("Numero de linhas de "+arquivo+" : "+listaDeLinhas.size());
				return listaDeLinhas;
		}
		catch(Exception e)
		{
				System.out.println("Erro no processamento do arquivo : "+arquivo );
				System.out.println(e.getMessage());
				Log.escrever("[ComparadorDeArquivos:converterParaLista] :"+e.getMessage() );
		}
			return listaDeLinhas; 
	}
	
}
