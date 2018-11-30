package Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PropriedadesDeCaminho 
{
	
	private static long ulltimoAcesso;
	
	
	public static String getDataModificacao(String caminho)
	{
		{
			long ultimaModificacao=0;
			String datas ="";
			try
			{
				if (caminho!=null)
				{
					File file= new File(caminho);
					ultimaModificacao = file.lastModified();
					datas=converterMiliSegundos(ultimaModificacao);
					return datas;
				}
			}
			catch(Exception e)
			{
				System.out.println("Erro em Monitor de Mudanças.");
				System.out.println(e.getMessage());
				Log.escrever("[PropriedadesDeCaminho:getDataModificacao] :"+e.getMessage());
			}
			return "Não definido";
		}
	}
	
	private static  String converterMiliSegundos(long datas)
	{
		if (datas==0) return "Não definido";
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(datas));
	}
	
	
}
