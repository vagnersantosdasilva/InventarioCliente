package Seguranca;

import java.net.InetAddress;
import java.net.NetworkInterface;

import Util.Log;

public class Identidade 
{
	
	private String identidade;
	private String macAdress;
	private static Identidade instance;
	
	private Identidade()
	{
		setMacAdress();
		setIdentidade();
	}
	
	
	public static Identidade getInstance()
	{
		if (instance==null)
		{
			synchronized (Identidade.class)
			{
				if (instance==null)
				{
					instance= new Identidade();
				}
			}
		}
		return instance;
	}
	
	
	@SuppressWarnings("static-access")
	private void setMacAdress() 
	{
	    
		try
		{
			if (macAdress==null)
			{
				InetAddress address = InetAddress.getLocalHost();  
			    NetworkInterface ni = NetworkInterface.getByInetAddress(address);  
			    byte[] mac = ni.getHardwareAddress();  
			    StringBuilder sb =new StringBuilder();
			    for (int i = 0; i < mac.length; i++) 
			    {           
			    	sb.append(ni.getHardwareAddress().toString().format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			    }
			    macAdress=sb.toString();
			    
			}
		}
		catch(Exception e)
		{
			System.out.println("Identidade:[setMacAdress]"+e.getMessage());
			e.printStackTrace();
			macAdress=null;
			Log.escrever("[Identidade:setMacAdress] :"+e.getMessage());
		}
		
	   	
	}
	
	private void setIdentidade()
	{
		try 
		{
			if (macAdress==null) setMacAdress();
			if (identidade==null)
			{
				String mac = macAdress;
				StringBuilder fragmento=new StringBuilder();
				for(int i=0;i<=5;i++)
				{
						String parcial=mac.split("-")[i];
						int hexa = Integer.parseInt(parcial,16);
						fragmento.append(hexa);
				}
				identidade=fragmento.toString();
			}
			
		} 
		catch (Exception e) 
		{
			System.out.println("Identidade[setIdentidade]:Erro ao gerar código de Máquina"+e.getMessage());
			e.printStackTrace();
			identidade=null;
			Log.escrever("[Identidade:setIdentidade] :"+e.getMessage());
		}
		
	}
	
	public String getIdentidade()
	{
		return identidade;
	}
	
	public String getMacAdress()
	{
		return macAdress;
	}
}
