package Testes;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.junit.Test;
import Seguranca.Identidade;
import Util.Propriedades;

public class TesteIdentificacao {

	@Test
	public void test()  
	{
		Identidade id =Identidade.getInstance();
		assertEquals("10-78-D2-B6-44-CA",id.getMacAdress());
		assertEquals("1612021018268202",id.getIdentidade());
	}
	
//	@Test
//	public void testePropriedades() throws Exception
//	{
//		assertEquals("10.0.0.100",Propriedades.lePropriedades("servidor"));
//		assertEquals("1050",Propriedades.lePropriedades("porta"));
//		assertEquals("C:\\Windows",Propriedades.lePropriedades("diretorioWindows"));
//		assertEquals("1504613215804",Propriedades.lePropriedades("ultimaMudanca"));
//		assertEquals("60",Propriedades.lePropriedades("tempoRefresh"));
//		
//	}
	
	

}
