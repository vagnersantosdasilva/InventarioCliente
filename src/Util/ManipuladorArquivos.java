package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ManipuladorArquivos 
{	
	public static boolean sobrescrever(String origem,String destino)
	{
		List lista =carregarArquivoEmLista(origem);
		salvarListaEmArquivos(lista, destino);
		return false;
	}
	
	public static boolean salvarListaEmArquivos(List lista,String caminho)
	{
		try
		{
			Writer w = new FileWriter(caminho);
			BufferedWriter wr = new BufferedWriter(w);
			String linha;
			for (int i =0;i<=lista.size()-1;i++)
			{
				wr.write((String) lista.get(i));
				wr.newLine();
			}
			wr.close();
			w.close();
			return true;
		}catch(IOException e)
		{
			System.out.println("Erro[salvarListaEmArquivo(ManipuladorArquivos )]:"+e.getMessage());
			Log.escrever("[ManipuladorArquivos:salvarListaEmArquivos] :"+e.getMessage());
		}
		return false;
	}
	
	public static List carregarArquivoEmLista(String arquivo)
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
				return listaDeLinhas;
		}
		catch(Exception e)
		{
				System.out.println("Erro no processamento do arquivo : "+arquivo );
				System.out.println(e.getMessage());
				Log.escrever("[ManipuladorArquivos:carregarArquivoEmLista] :"+e.getMessage());
		}
			return listaDeLinhas; 
	}
}
