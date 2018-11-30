package Util;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Properties;

public class Propriedades {

	public static String lePropriedades(String propriedade)
	{
		return lePropriedades("propriedades/propriedades_sistema.cfg",propriedade);
	}
	
	public static String lePropriedades(String caminho,String propriedade) 
	{
		try
		{
			Properties prop=new Properties();
			FileInputStream fis = new FileInputStream(caminho);
			prop.load(fis);
			String valorDeRetorno=prop.getProperty(propriedade);
			fis.close();
			return valorDeRetorno;
		}
		catch(Exception e)
		{
			System.out.println("Erro Util.Propriedades [lePropriedades] :"+e.getMessage());
			e.printStackTrace();
			Log.escrever("[Propriedades:lePropriedades] :"+e.getMessage());
		}
		return "";

	}

	public static void escrevePropriedades(List lista)throws IOException
	{
		Properties prop=new Properties();
		FileOutputStream fos = new FileOutputStream("propriedades/propriedades.cfg");
		for (Object propriedade :lista){
			
			
		}

	}
	
	public static void escreverStringEmArquivo(String frase,String arquivo) 
	{
		try
		{
			Writer w = new FileWriter(arquivo,true);
			BufferedWriter wr = new BufferedWriter(w);
				wr.write(frase);
				wr.newLine();
			wr.close();
			w.close();
			
		}catch(IOException e)
		{
			System.out.println("Erro[Propriedades:escreverStringEmArquivo]:"+e.getMessage());
			e.printStackTrace();
			
		}
		
	}

	public static void sobrescreverArquivo(String frase ,String arquivo) 
	{
		try
		{
			Writer w = new FileWriter(arquivo,false);
			BufferedWriter wr = new BufferedWriter(w);
				wr.write(frase);
				wr.newLine();
			wr.close();
			w.close();
			
		}catch(IOException e)
		{
			System.out.println("Erro[Propriedades:sobrescreverStringEmArquivo]:"+e.getMessage());
			e.printStackTrace();
		}
		
	}

	
	

}
