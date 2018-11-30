package Util;

public class Conversor {
	
	public static String byteParaGiga(String tamanho)
	{
		if (tamanho==null) return "Não Detectado";
		long giga = Long.parseLong(tamanho.replaceAll(" ", "")); 
		Double local = (double)(giga/(1024*1024*948));
		return local.toString()+" GB";
	}
	public static String byteParaTera(String tamanho)
	{
		if (tamanho==null) return "Não Detectado";
		long giga = Long.parseLong(tamanho.replaceAll(" ", "")); 
		Double local = (double)(giga/(1024*1024*1024*1024));
		return local.toString()+" TB";
	}
	
	public static String gigaParaTera(String tamanho)
	{
		if (tamanho==null) return "Não Detectado";
		long giga = Long.parseLong(tamanho.replaceAll(" ", "")); 
		Double local = (double)(giga/(1024));
		return local.toString()+" TB";
	}
	
	public static String converteHexaDecimal(String hexa)
	{
		return null;
	}
	
	

}
